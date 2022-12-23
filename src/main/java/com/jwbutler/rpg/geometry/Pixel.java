package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

public record Pixel
(
    int x,
    int y
) implements Point
{
    private static final Pixel ZERO = new Pixel(0, 0);

    @Nonnull
    public Pixel plus(int dx, int dy)
    {
        return new Pixel(x + dx, y + dy);
    }

    @Nonnull
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }

    @Nonnull
    public static Pixel zero()
    {
        return ZERO;
    }
}
