package com.jwbutler.rpg.geometry;

import java.util.List;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface Pathfinder
{
    @CheckForNull
    List<Coordinates> findPath(
        @Nonnull Coordinates start,
        @Nonnull Coordinates end,
        @Nonnull Set<Coordinates> candidates
    );

    Pathfinder A_STAR = new AStarPathfinder();
}
