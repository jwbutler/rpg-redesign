package com.jwbutler.rpg.geometry;

import java.util.List;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface Pathfinder
{
    @Nullable
    List<Coordinates> findPath(
        @NonNull Coordinates start,
        @NonNull Coordinates end,
        @NonNull Set<Coordinates> candidates
    );

    Pathfinder A_STAR = new AStarPathfinder();
}
