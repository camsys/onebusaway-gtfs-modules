/**
 * Copyright (C) 2024 Cambridge Systematics, Inc.
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
package org.onebusaway.gtfs_merge.strategies;

import org.onebusaway.gtfs.model.Pathway;

import java.util.Objects;

/**
 * Entity merge strategy for {@link Pathway} entities (pathways.txt).
 */
public class PathwayMergeStrategy extends
    AbstractNonIdentifiableSingleEntityMergeStrategy<Pathway> {

  public PathwayMergeStrategy() {
    super(Pathway.class);
  }

  @Override
  protected boolean entitiesAreIdentical(Pathway a, Pathway b) {
    return Objects.equals(a.getFromStop(), b.getFromStop())
        && Objects.equals(a.getToStop(), b.getToStop())
        && Objects.equals(a.getPathwayMode(), b.getPathwayMode())
        && Objects.equals(a.getTraversalTime(), b.getTraversalTime())
        && Objects.equals(a.getIsBidirectional(), b.getIsBidirectional())
        && Objects.equals(a.getLength(), b.getLength())
        && Objects.equals(a.getStairCount(), b.getStairCount())
        && Objects.equals(a.getMaxSlope(), b.getMaxSlope())
        && Objects.equals(a.getMinWidth(), b.getMinWidth())
        && Objects.equals(a.getIsAccessible(), b.getIsAccessible())
        && Objects.equals(a.getSignpostedAs(), b.getSignpostedAs())
        && Objects.equals(a.getReversedSignpostedAs(), b.getReversedSignpostedAs())
        && Objects.equals(a.getWheelchairTraversalTime(), b.getWheelchairTraversalTime())
        && Objects.equals(a.getPathwayCode(), b.getPathwayCode())
        && Objects.equals(a.getPathwayType(), b.getPathwayType());
  }
}
