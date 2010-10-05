package org.onebusaway.gtfs.model.calendar;

import java.io.Serializable;
import java.util.TimeZone;

import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.services.calendar.CalendarService;

/**
 * Combines a serviceId (represented by {@link AgencyAndId}) and a
 * {@link TimeZone} object that can be used to ground {@link ServiceDate}
 * objects. See {@link ServiceDate#getAsDate(TimeZone)} for more info.
 * 
 * @author bdferris
 * 
 * @see ServiceDate
 * @see CalendarService
 */
public class LocalizedServiceId implements Serializable,
    Comparable<LocalizedServiceId> {

  private static final long serialVersionUID = 1L;

  private final AgencyAndId id;

  private final TimeZone timeZone;

  public LocalizedServiceId(AgencyAndId serviceId, TimeZone timeZone) {
    if (serviceId == null)
      throw new IllegalArgumentException("serviceId cannot be null");
    if (timeZone == null)
      throw new IllegalArgumentException("timeZone cannot be null");
    this.id = serviceId;
    this.timeZone = (TimeZone) timeZone.clone();
  }

  public AgencyAndId getId() {
    return id;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  @Override
  public String toString() {
    return "ServiceId(id=" + id + " timeZone=" + timeZone.getID() + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((timeZone == null) ? 0 : timeZone.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LocalizedServiceId other = (LocalizedServiceId) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (timeZone == null) {
      if (other.timeZone != null)
        return false;
    } else if (!timeZone.equals(other.timeZone))
      return false;
    return true;
  }

  @Override
  public int compareTo(LocalizedServiceId o) {
    int rc = this.id.compareTo(o.id);
    if (rc == 0)
      rc = this.timeZone.getID().compareTo(o.getTimeZone().getID());
    return rc;
  }
}
