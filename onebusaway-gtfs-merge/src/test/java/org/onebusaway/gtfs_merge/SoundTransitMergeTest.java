/**
 * Copyright (C) 2026 Cambridge Systematics, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.gtfs_merge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.impl.FileSupport;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.Translation;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsRelationalDao;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SoundTransitMergeTest {

  private static final String RESOURCE_BASE = "/org/onebusaway/gtfs_merge/sound-transit/";

  private GtfsMerger _merger;
  private FileSupport _support = new FileSupport();

  @BeforeEach
  public void before() {
    _merger = new GtfsMerger();
  }

  @AfterEach
  public void after() {
    _support.cleanup();
  }

  @Test
  public void testMergePierceTransitAndSoundTransit() throws Exception {
    File pierceFeed = resourceAsFile("3_3_gtfs_20260401");
    File soundFeed = resourceAsFile("40_40_gtfs_20260401");

    List<File> inputs = new ArrayList<>();
    inputs.add(pierceFeed);
    inputs.add(soundFeed);

    File outputZip = createTempOutputZip();
    _merger.run(inputs, outputZip);
    assertTrue(outputZip.exists(), "Merger did not produce output zip");

    GtfsRelationalDao dao = readGtfs(outputZip);

    // Both agencies must be present in the merged output.
    // 3_3 feed has agency_id=3 (Pierce Transit) and agency_id=40 (Sound Transit).
    // 40_40 feed has agency_id=40 (Sound Transit) — deduplicated to one by the merger.
    Collection<Agency> agencies = dao.getAllAgencies();
    List<String> agencyIds = agencies.stream().map(Agency::getId).collect(Collectors.toList());
    assertEquals(2, agencies.size(), "Expected exactly 2 agencies after deduplication, got: " + agencyIds);
    assertTrue(agencyIds.contains("3"), "Pierce Transit (agency_id=3) missing from merged output");
    assertTrue(agencyIds.contains("40"), "Sound Transit (agency_id=40) missing from merged output");

    // Routes from both agencies must be present.
    // 3_3 has 40 routes (agency_id=3), 40_40 has 8 routes (agency_id=40); no route_id overlap.
    Collection<Route> routes = dao.getAllRoutes();
    List<String> routeAgencyIds = routes.stream()
        .map(r -> r.getAgency().getId())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assertTrue(routeAgencyIds.contains("3"), "No Pierce Transit routes in merged output");
    assertTrue(routeAgencyIds.contains("40"), "No Sound Transit routes in merged output");
    assertEquals(48, routes.size(),
        "Expected 40 Pierce Transit + 8 Sound Transit routes = 48 total, got: " + routes.size());

    Collection<ServiceCalendar> calendars = dao.getAllCalendars();
    assertTrue(calendars.size() >= 15,
        "Expected at least 15 service calendars (3 Pierce + 12 Sound Transit), got: " + calendars.size());

    // Translations from both feeds are merged and deduplicated by natural key
    // (table_name, field_name, language, record_id, record_sub_id, field_value).
    //
    // 3_3 contributes:
    //   es / record_id=1       "6 Avenida / Avenida Pacific"  (unique)
    //   es / record_id=202     "Calle 72 Sur"                 (unique)
    //   es / record_id=1-SHUTTLE  "Link - Autobus"            (COLLIDES with 40_40)
    //
    // 40_40 contributes:
    //   es       / record_id=1-SHUTTLE  "Link - Bus de Reemplazo"  (wins — 40_40 processed first)
    //   zh-Hans  / record_id=1-SHUTTLE  "Link 替代接驳巴士"         (unique)
    //   ar       / record_id=1-SHUTTLE  "Link - حافلة بديلة"       (unique)
    //
    // Result: 5 translations (colliding es/1-SHUTTLE deduplicated to one).
    Collection<Translation> translations = dao.getAllEntitiesForType(Translation.class);
    assertEquals(5, translations.size(),
        "Expected 5 translations (3+3 minus 1 collision), got: " + translations.size());

    // Only one translation should survive for the colliding key
    List<Translation> shuttleEsTranslations = translations.stream()
        .filter(t -> "es".equals(t.getLanguage()) && "1-SHUTTLE".equals(t.getRecordId()))
        .collect(Collectors.toList());
    assertEquals(1, shuttleEsTranslations.size(),
        "Expected exactly 1 es/1-SHUTTLE translation after deduplication");

    // 40_40 is the last (newest) feed in the input list and is processed first,
    // so its translation text wins over 3_3's conflicting entry.
    assertEquals("Link - Bus de Reemplazo", shuttleEsTranslations.get(0).getTranslation());
  }

  private GtfsRelationalDao readGtfs(File path) throws IOException {
    GtfsReader reader = new GtfsReader();
    GtfsRelationalDaoImpl dao = new GtfsRelationalDaoImpl();
    reader.setEntityStore(dao);
    reader.setInputLocation(path);
    reader.run();
    return dao;
  }

  private File resourceAsFile(String filename) throws Exception {
    URI uri = getClass().getResource(RESOURCE_BASE + filename).toURI();
    return new File(uri);
  }

  private File createTempOutputZip() throws IOException {
    File tmpDir = File.createTempFile("SoundTransitMergeTest-", "-tmp");
    if (tmpDir.exists())
      _support.deleteFileRecursively(tmpDir);
    tmpDir.mkdirs();
    _support.markForDeletion(tmpDir);
    return new File(tmpDir, "merged.zip");
  }
}
