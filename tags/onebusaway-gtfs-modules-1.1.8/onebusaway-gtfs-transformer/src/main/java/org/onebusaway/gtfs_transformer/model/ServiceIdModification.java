package org.onebusaway.gtfs_transformer.model;

public interface ServiceIdModification {

  public String getServiceId(String serviceId);

  public String getGtfsTripId(String tripId);
  
  public String getGtfsBlockId(String blockId);

  public int applyPassingTimeTransformation(int passingTime);
}
