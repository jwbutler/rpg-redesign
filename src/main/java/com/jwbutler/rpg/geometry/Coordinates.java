package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

public record Coordinates
(
    int x,
    int y
)
{
    private static final Coordinates ZERO = new Coordinates(0, 0);

    @Nonnull
    public Coordinates plus(int dx, int dy)
    {
        return new Coordinates(x + dx, y + dy);
    }

    @Nonnull
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }

    @Nonnull
    public static Coordinates zero()
    {
        return ZERO;
    }
}
