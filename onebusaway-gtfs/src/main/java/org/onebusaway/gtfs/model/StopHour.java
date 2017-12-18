/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
 * Copyright (C) 2013 Google, Inc.
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
package org.onebusaway.gtfs.model;

import org.onebusaway.csv_entities.schema.annotations.CsvField;
import org.onebusaway.csv_entities.schema.annotations.CsvFields;
import org.onebusaway.gtfs.serialization.mappings.DefaultAgencyIdFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.EntityFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.StopTimeFieldMappingFactory;

@CsvFields(filename = "stop_hours.txt", required = false)
public final class StopHour extends IdentityBean<Integer>  {

    private static final long serialVersionUID =2L;

    public static final int MISSING_VALUE = -999;

    @CsvField(ignore = true)
    private int id;

    @CsvField(name = "stop_id", mapping = EntityFieldMappingFactory.class)
    private Stop stop;

    @CsvField(mapping = DefaultAgencyIdFieldMappingFactory.class)
    private AgencyAndId serviceId;

    @CsvField(mapping = StopTimeFieldMappingFactory.class)
    private int startTime = MISSING_VALUE;

    @CsvField(mapping = StopTimeFieldMappingFactory.class)
    private int endTime = MISSING_VALUE;

    public StopHour() {

    }

    public StopHour(StopHour s) {
        this.id = s.id;
        this.stop = s.stop;
        this.serviceId = s.serviceId;
        this.startTime = s.startTime;
        this.endTime = s.endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public AgencyAndId getServiceId() {
        return serviceId;
    }

    public void setServiceId(AgencyAndId serviceId) {
        this.serviceId = serviceId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "StopHour(stop =" + stop.getId() + ")";
    }
}
