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
import org.onebusaway.gtfs.serialization.mappings.EntityFieldMappingFactory;


@CsvFields(filename = "route_networks.txt", required = false)
public class RouteNetworks extends IdentityBean<Integer> {

    private static final long serialVersionUID = 1L;

    private static final int MISSING_VALUE = -999;

    @CsvField(ignore = true)
    private int id;

    @CsvField(name = "network_id", mapping = EntityFieldMappingFactory.class)
    Networks networkId;

    @CsvField(name = "route_id", mapping = EntityFieldMappingFactory.class)
    Route routeId;

    public RouteNetworks() {

    }

    public RouteNetworks(RouteNetworks rn) {
        this.id = rn.id;
        this.networkId = rn.networkId;
        this.routeId = rn.routeId;
    }

    public Networks getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Networks networkId) {
        this.networkId = networkId;
    }

    public Route getRouteId() {
        return routeId;
    }

    public void setRouteId(Route routeId) {
        this.routeId = routeId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}