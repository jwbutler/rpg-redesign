package com.jwbutler.rpg.geometry;

import org.jspecify.annotations.NonNull;

public record Coordinates
(
    int x,
    int y
)
implements Point
{
    private static final Coordinates ZERO = new Coordinates(0, 0);

    @NonNull
    public Coordinates plus(int dx, int dy)
    {
        return new Coordinates(x + dx, y + dy);
    }

    @NonNull
    public Coordinates plus(@NonNull Direction direction)
    {
        return new Coordinates(x + direction.dx(), y + direction.dy());
    }

    @NonNull
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }

    @NonNull
    public static Coordinates zero()
    {
        return ZERO;
    }
}
