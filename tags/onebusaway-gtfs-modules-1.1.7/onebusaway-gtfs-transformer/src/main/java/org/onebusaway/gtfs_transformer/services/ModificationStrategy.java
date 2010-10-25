package org.onebusaway.gtfs_transformer.services;

import org.onebusaway.gtfs.csv.schema.BeanWrapper;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;

public interface ModificationStrategy {
  public void applyModification(TransformContext context, BeanWrapper wrapped, GtfsMutableRelationalDao dao);
}
