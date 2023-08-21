package com.jwbutler.rpg.geometry;

import java.util.Comparator;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;

public enum Direction
{
    N ( 0, -1),
    NE( 1, -1),
    E ( 1,  0),
    SE( 1,  1),
    S ( 0,  1),
    SW(-1,  1),
    W (-1,  0),
    NW(-1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public final int dx()
    {
        return dx;
    }

    public final int dy()
    {
        return dy;
    }

    /**
     * @throws IllegalArgumentException if {@code first} and {@code second} are not directly adjacent
     */
    @NonNull
    public static Direction between(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Stream.of(Direction.values())
            .filter(direction -> direction.dx() == dx && direction.dy() == dy)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("No direction found between points %s, %s", first, second)));
    }

    @NonNull
    public static Direction nearestBetween(@NonNull Coordinates first, @NonNull Coordinates second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        var theta = Math.atan2(dy, dx);
        return Stream.of(Direction.values())
            .min(Comparator.comparing(direction ->
            {
                var t = Math.atan2(direction.dy(), direction.dx());
                return Math.abs(theta - t);
            }))
            .orElseThrow();
    }
}
