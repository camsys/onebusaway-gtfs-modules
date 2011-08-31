/*
 * Copyright 2008 Brian Ferris
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.onebusaway.gtfs_transformer.model;

import java.io.Serializable;

/**
 * This class is officially deprecated, but support is kept around for the
 * legacy code base
 * 
 * @author bdferris
 */
@Deprecated
public class TimepointKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String gtfsTripId;

  private String timepointId;

  private int timepointSequence;

  public TimepointKey() {

  }

  public TimepointKey(String gtfsTripId, String timepointId,
      int timepointSequence) {
    this.gtfsTripId = gtfsTripId;
    this.timepointId = timepointId;
    this.timepointSequence = timepointSequence;
  }

  public String getGtfsTripId() {
    return gtfsTripId;
  }

  public void setGtfsTripId(String tripId) {
    this.gtfsTripId = tripId;
  }

  public String getTimepointId() {
    return timepointId;
  }

  public void setTimepointId(String timepointId) {
    this.timepointId = timepointId;
  }

  public int getTimepointSequence() {
    return timepointSequence;
  }

  public void setTimepointSequence(int timepointSequence) {
    this.timepointSequence = timepointSequence;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof TimepointKey))
      return false;

    TimepointKey key = (TimepointKey) obj;
    return this.gtfsTripId.equals(key.gtfsTripId)
        && this.timepointId.equals(key.timepointId)
        && this.timepointSequence == key.timepointSequence;
  }

  @Override
  public int hashCode() {
    return 5 * this.gtfsTripId.hashCode() + 7 * this.timepointId.hashCode()
        + 11 * this.timepointSequence;
  }

  @Override
  public String toString() {
    return this.gtfsTripId + " " + this.timepointId + " "
        + this.timepointSequence;
  }
}
