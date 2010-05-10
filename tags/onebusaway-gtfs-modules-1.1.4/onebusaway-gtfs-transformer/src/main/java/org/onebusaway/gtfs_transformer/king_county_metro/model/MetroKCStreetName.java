package org.onebusaway.gtfs_transformer.king_county_metro.model;

import org.onebusaway.gtfs.csv.schema.annotations.CsvField;
import org.onebusaway.gtfs.csv.schema.annotations.CsvFields;
import org.onebusaway.gtfs.model.IdentityBean;

@CsvFields(filename = "street_names.csv")
public class MetroKCStreetName extends IdentityBean<Integer> {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private String name;

  @CsvField(optional = true)
  private String type;

  @CsvField(optional = true)
  private String prefix;

  @CsvField(optional = true)
  private String suffix;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}
