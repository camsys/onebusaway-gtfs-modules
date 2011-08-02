/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
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
package org.onebusaway.gtfs.serialization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.onebusaway.csv_entities.schema.DefaultEntitySchemaFactory;
import org.onebusaway.csv_entities.schema.EntitySchemaFactoryHelper;
import org.onebusaway.csv_entities.schema.beans.CsvEntityMappingBean;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.FareAttribute;
import org.onebusaway.gtfs.model.FareRule;
import org.onebusaway.gtfs.model.Frequency;
import org.onebusaway.gtfs.model.IdentityBean;
import org.onebusaway.gtfs.model.Pathway;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Transfer;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.comparators.ServiceCalendarComparator;
import org.onebusaway.gtfs.serialization.comparators.ServiceCalendarDateComparator;
import org.onebusaway.gtfs.serialization.comparators.ShapePointComparator;
import org.onebusaway.gtfs.serialization.comparators.StopTimeComparator;
import org.onebusaway.gtfs.serialization.mappings.AgencyIdTranslationFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.ServiceDateFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.DefaultAgencyIdFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.EntityFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.RouteAgencyFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.RouteValidator;
import org.onebusaway.gtfs.serialization.mappings.StopTimeFieldMappingFactory;

public class GtfsEntitySchemaFactory {

  public static List<Class<?>> getEntityClasses() {
    List<Class<?>> entityClasses = new ArrayList<Class<?>>();
    entityClasses.add(Agency.class);
    entityClasses.add(ShapePoint.class);
    entityClasses.add(Route.class);
    entityClasses.add(Stop.class);
    entityClasses.add(Trip.class);
    entityClasses.add(StopTime.class);
    entityClasses.add(ServiceCalendar.class);
    entityClasses.add(ServiceCalendarDate.class);
    entityClasses.add(FareAttribute.class);
    entityClasses.add(FareRule.class);
    entityClasses.add(Frequency.class);
    entityClasses.add(Pathway.class);
    entityClasses.add(Transfer.class);
    return entityClasses;
  }

  public static Map<Class<?>, Comparator<?>> getEntityComparators() {
    Map<Class<?>, Comparator<?>> comparators = new HashMap<Class<?>, Comparator<?>>();
    comparators.put(Agency.class,
        getComparatorForIdentityBeanType(Agency.class));
    comparators.put(Route.class, getComparatorForIdentityBeanType(Route.class));
    comparators.put(Stop.class, getComparatorForIdentityBeanType(Stop.class));
    comparators.put(Trip.class, getComparatorForIdentityBeanType(Trip.class));
    comparators.put(StopTime.class, new StopTimeComparator());
    comparators.put(ShapePoint.class, new ShapePointComparator());
    comparators.put(ServiceCalendar.class, new ServiceCalendarComparator());
    comparators.put(ServiceCalendarDate.class,
        new ServiceCalendarDateComparator());
    return comparators;
  }

  public static DefaultEntitySchemaFactory createEntitySchemaFactory() {

    DefaultEntitySchemaFactory factory = new DefaultEntitySchemaFactory();
    EntitySchemaFactoryHelper helper = new EntitySchemaFactoryHelper(factory);

    CsvEntityMappingBean agencyId = helper.addEntity(AgencyAndId.class);
    helper.addIgnorableField(agencyId, "agencyId");

    CsvEntityMappingBean agency = helper.addEntity(Agency.class, "agency.txt",
        "agency_");
    helper.addOptionalField(agency, "id", "agency_id",
        new AgencyIdTranslationFieldMappingFactory());
    helper.addOptionalFields(agency, "lang", "phone");

    CsvEntityMappingBean route = helper.addEntity(Route.class, "routes.txt",
        "route_");
    helper.addOptionalField(route, "agency", "agency_id",
        new RouteAgencyFieldMappingFactory());
    // We set the order of the id field to come after the agency field, such
    // that the agency field will be set before we attempt to set the id field
    helper.addField(route, "id", new DefaultAgencyIdFieldMappingFactory(
        "agency.id"), 1);
    helper.addOptionalFields(route, "desc", "shortName", "longName", "url",
        "color", "textColor", "bikesAllowed");
    route.addValidator(new RouteValidator());

    CsvEntityMappingBean shapePoint = helper.addEntity(ShapePoint.class,
        "shapes.txt");
    shapePoint.setRequired(false);
    helper.addIgnorableField(shapePoint, "id");
    helper.addOptionalField(shapePoint, "shapeId",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addOptionalField(shapePoint, "distTraveled", "shape_dist_traveled");
    helper.addField(shapePoint, "lat", "shape_pt_lat");
    helper.addField(shapePoint, "lon", "shape_pt_lon");
    helper.addField(shapePoint, "sequence", "shape_pt_sequence");

    CsvEntityMappingBean stop = helper.addEntity(Stop.class, "stops.txt",
        "stop_");
    helper.addField(stop, "id", new DefaultAgencyIdFieldMappingFactory());
    helper.addOptionalFields(stop, "code", "desc", "direction", "url");
    helper.addOptionalField(stop, "zoneId", "zone_id");
    helper.addOptionalField(stop, "locationType", "location_type");
    helper.addOptionalField(stop, "parentStation", "parent_station");
    helper.addOptionalField(stop, "wheelchairBoarding", "wheelchair_boarding");

    CsvEntityMappingBean trip = helper.addEntity(Trip.class, "trips.txt");
    helper.addField(trip, "route", "route_id", new EntityFieldMappingFactory());
    helper.addField(trip, "id", "trip_id",
        new DefaultAgencyIdFieldMappingFactory("route.agency.id"), 1);
    helper.addOptionalFields(trip, "tripShortName", "tripHeadsign",
        "routeShortName", "directionId", "blockId");
    helper.addField(trip, "serviceId", new DefaultAgencyIdFieldMappingFactory());
    helper.addOptionalField(trip, "shapeId",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addOptionalField(trip, "wheelchairAccessible",
        "wheelchair_accessible");
    helper.addOptionalField(trip, "tripBikesAllowed");

    CsvEntityMappingBean stopTime = helper.addEntity(StopTime.class,
        "stop_times.txt");
    helper.addIgnorableField(stopTime, "id");
    helper.addField(stopTime, "trip", "trip_id",
        new EntityFieldMappingFactory());
    helper.addField(stopTime, "stop", "stop_id",
        new EntityFieldMappingFactory());
    helper.addOptionalField(stopTime, "arrivalTime",
        new StopTimeFieldMappingFactory());
    helper.addOptionalField(stopTime, "departureTime",
        new StopTimeFieldMappingFactory());
    helper.addOptionalFields(stopTime, "stopHeadsign", "routeShortName",
        "pickupType", "dropOffType", "shapeDistTraveled");

    CsvEntityMappingBean calendar = helper.addEntity(ServiceCalendar.class,
        "calendar.txt");
    helper.addIgnorableField(calendar, "id");
    helper.addField(calendar, "serviceId", "service_id",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addField(calendar, "startDate", new ServiceDateFieldMappingFactory());
    helper.addField(calendar, "endDate", new ServiceDateFieldMappingFactory());

    CsvEntityMappingBean calendarDate = helper.addEntity(
        ServiceCalendarDate.class, "calendar_dates.txt");
    calendarDate.setRequired(false);
    helper.addIgnorableField(calendarDate, "id");
    helper.addField(calendarDate, "serviceId", "service_id",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addField(calendarDate, "date", new ServiceDateFieldMappingFactory());

    CsvEntityMappingBean fareAttributes = helper.addEntity(FareAttribute.class,
        "fare_attributes.txt");
    fareAttributes.setRequired(false);
    helper.addField(fareAttributes, "id", "fare_id",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addFields(fareAttributes, "price", "currencyType", "paymentMethod");
    helper.addOptionalFields(fareAttributes, "transfers", "transferDuration");

    CsvEntityMappingBean fareRules = helper.addEntity(FareRule.class,
        "fare_rules.txt");
    fareRules.setRequired(false);
    helper.addIgnorableField(fareRules, "id");
    helper.addField(fareRules, "fare", "fare_id",
        new EntityFieldMappingFactory());
    helper.addOptionalField(fareRules, "route", "route_id",
        new EntityFieldMappingFactory());
    helper.addOptionalFields(fareRules, "originId", "destinationId",
        "containsId");

    CsvEntityMappingBean frequencies = helper.addEntity(Frequency.class,
        "frequencies.txt");
    frequencies.setRequired(false);
    helper.addIgnorableField(frequencies, "id");
    helper.addField(frequencies, "trip", "trip_id",
        new EntityFieldMappingFactory());
    helper.addOptionalField(frequencies, "startTime",
        new StopTimeFieldMappingFactory());
    helper.addOptionalField(frequencies, "endTime",
        new StopTimeFieldMappingFactory());
    helper.addFields(frequencies, "headwaySecs");
    helper.addOptionalField(frequencies, "exactTimes");

    CsvEntityMappingBean pathways = helper.addEntity(Pathway.class,
        "pathways.txt");
    pathways.setRequired(false);
    helper.addField(pathways, "id", "pathway_id",
        new DefaultAgencyIdFieldMappingFactory());
    helper.addField(pathways, "fromStop", "from_stop_id",
        new EntityFieldMappingFactory());
    helper.addField(pathways, "toStop", "to_stop_id",
        new EntityFieldMappingFactory());
    helper.addField(pathways, "traversalTime", "traversal_time");
    helper.addOptionalField(pathways, "wheelchairTraversalTime",
        "wheelchair_traversal_time");

    CsvEntityMappingBean transfers = helper.addEntity(Transfer.class,
        "transfers.txt");
    transfers.setRequired(false);
    helper.addIgnorableField(transfers, "id");
    helper.addField(transfers, "fromStop", "from_stop_id",
        new EntityFieldMappingFactory());
    helper.addField(transfers, "toStop", "to_stop_id",
        new EntityFieldMappingFactory());
    helper.addField(transfers, "transferType");
    helper.addOptionalField(transfers, "minTransferTime");

    return factory;
  }

  private static <T extends IdentityBean<?>> Comparator<T> getComparatorForIdentityBeanType(
      Class<T> entityType) {
    return new Comparator<T>() {
      @SuppressWarnings("unchecked")
      @Override
      public int compare(T o1, T o2) {
        Comparable<Object> a = (Comparable<Object>) o1.getId();
        Comparable<Object> b = (Comparable<Object>) o2.getId();
        return a.compareTo(b);
      }
    };
  }
}
