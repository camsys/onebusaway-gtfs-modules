/**
 * 
 */
package org.onebusaway.gtfs_transformer.king_county_metro.model;

import org.onebusaway.gtfs.csv.schema.FlattenFieldMappingFactory;
import org.onebusaway.gtfs.csv.schema.annotations.CsvField;
import org.onebusaway.gtfs.csv.schema.annotations.CsvFields;
import org.onebusaway.gtfs_transformer.model.VersionedId;

@CsvFields(filename = "pattern_timepoints.csv")
public class MetroKCPatternTimepoint implements Comparable<MetroKCPatternTimepoint> {

  @CsvField(mapping=FlattenFieldMappingFactory.class)
  private VersionedId id;

  private int sequence;

  @CsvField(optional = true)
  private int tpiId;

  private int timepointId;

  public VersionedId getId() {
    return id;
  }

  public void setId(VersionedId id) {
    this.id = id;
  }

  public int getSequence() {
    return sequence;
  }

  public void setSequence(int position) {
    this.sequence = position;
  }

  public int getTpiId() {
    return tpiId;
  }

  public void setTpiId(int tpiId) {
    this.tpiId = tpiId;
  }

  public int getTimepointId() {
    return timepointId;
  }

  public void setTimepointId(int timepointId) {
    this.timepointId = timepointId;
  }

  public int compareTo(MetroKCPatternTimepoint o) {
    return this.sequence == o.sequence ? 0 : (this.sequence < o.sequence ? -1
        : 1);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof MetroKCPatternTimepoint))
      return false;
    MetroKCPatternTimepoint pt = (MetroKCPatternTimepoint) obj;
    return this.id.equals(pt.id) && this.sequence == pt.sequence;
  }

  @Override
  public int hashCode() {
    return id.hashCode() + this.sequence;
  }

}