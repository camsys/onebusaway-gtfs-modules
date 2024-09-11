package org.onebusaway.gtfs.model;

import org.onebusaway.csv_entities.schema.annotations.CsvField;
import org.onebusaway.csv_entities.schema.annotations.CsvFields;


@CsvFields(filename = "network.txt", required = false)
public class Network extends IdentityBean<String> {
    private static final long serialVersionUID = 1L;

    @CsvField(name = "network_id", optional = true)
    private String id;

    @CsvField(name = "network_name", optional = true)
    private String name;



    @Override
    public String toString() {
        return "<Network " + this.id + ">";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}