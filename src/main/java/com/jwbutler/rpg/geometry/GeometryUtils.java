package com.jwbutler.rpg.geometry;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

public final class GeometryUtils
{
    private GeometryUtils() {}

    public static double hypotenuse(@Nonnull Coordinates first, @Nonnull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double manhattanDistance(@Nonnull Coordinates first, @Nonnull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.abs(dx) + Math.abs(dy);
    }

    /**
     * https://en.wikipedia.org/wiki/Chebyshev_distance
     */
    public static double chebyshevDistance(@Nonnull Coordinates first, @Nonnull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public static boolean isDirectlyAdjacent(@Nonnull Coordinates first, @Nonnull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();

        return Stream.of(Direction.values())
            .anyMatch(d -> d.dx() == dx && d.dy() == dy);

    }
}
