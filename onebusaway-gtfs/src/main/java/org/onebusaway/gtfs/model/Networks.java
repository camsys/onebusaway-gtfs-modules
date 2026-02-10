/**
 * Copyright (C) 2026 Cayla Savitzky <csavitzky@camsys.com>
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

@CsvFields(filename = "networks.txt", required = false)
public class Networks extends IdentityBean<AgencyAndId>{

    private static final long serialVersionUID = 1L;


    @CsvField(name = "network_id", mapping = DefaultAgencyIdFieldMappingFactory.class)
    private AgencyAndId id;

    @CsvField(optional = true)
    private String networkName;


    public Networks(){

    }

    public Networks(Networks networks){
        this.id = networks.id;
        this.networkName = networks.networkName;

    }

    public String getNetworkId() {
        return id.getId();
    }

    @Override
    public void setId(AgencyAndId id) {
        this.id = id;
    }

    @Override
    public AgencyAndId getId() {
        return id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }


}
