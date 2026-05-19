/**
 * Copyright (C) 2024 Cambridge Systematics, Inc.
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
package org.onebusaway.gtfs_merge.strategies;

import org.onebusaway.gtfs.model.Level;

import java.util.Objects;

/**
 * Entity merge strategy for {@link Level} entities (levels.txt).
 */
public class LevelMergeStrategy extends
    AbstractNonIdentifiableSingleEntityMergeStrategy<Level> {

  public LevelMergeStrategy() {
    super(Level.class);
  }

  @Override
  protected boolean entitiesAreIdentical(Level a, Level b) {
    return Objects.equals(a.getIndex(), b.getIndex())
        && Objects.equals(a.getName(), b.getName());
  }
}
