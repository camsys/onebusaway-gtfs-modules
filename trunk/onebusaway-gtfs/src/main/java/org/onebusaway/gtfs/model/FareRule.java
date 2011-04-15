/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
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

public final class FareRule extends IdentityBean<Integer> {

  private static final long serialVersionUID = 1L;

  private int id;

  private FareAttribute fare;

  private Route route;

  private String originId;

  private String destinationId;

  private String containsId;

  public FareRule() {

  }

  public FareRule(FareRule fr) {
    this.id = fr.id;
    this.fare = fr.fare;
    this.route = fr.route;
    this.originId = fr.originId;
    this.destinationId = fr.destinationId;
    this.containsId = fr.containsId;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public FareAttribute getFare() {
    return fare;
  }

  public void setFare(FareAttribute fare) {
    this.fare = fare;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  public String getOriginId() {
    return originId;
  }

  public void setOriginId(String originId) {
    this.originId = originId;
  }

  public String getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }

  public String getContainsId() {
    return containsId;
  }

  public void setContainsId(String containsId) {
    this.containsId = containsId;
  }

  public String toString() {
    return "<FareRule " + getId() + ">";
  }
}
