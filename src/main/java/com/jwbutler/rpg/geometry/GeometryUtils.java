package com.jwbutler.rpg.geometry;

import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;

public final class GeometryUtils
{
    private GeometryUtils() {}

    public static double hypotenuse(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double manhattanDistance(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.abs(dx) + Math.abs(dy);
    }

    /**
     * https://en.wikipedia.org/wiki/Chebyshev_distance
     */
    public static double chebyshevDistance(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public static boolean isDirectlyAdjacent(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();

        return Stream.of(Direction.values())
            .anyMatch(d -> d.dx() == dx && d.dy() == dy);

    }
}
