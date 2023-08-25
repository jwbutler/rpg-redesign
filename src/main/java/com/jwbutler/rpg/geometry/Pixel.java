package com.jwbutler.rpg.geometry;

import org.jspecify.annotations.NonNull;

public record Pixel
(
    int x,
    int y
)
implements Point
{
    private static final Pixel ZERO = new Pixel(0, 0);

    @NonNull
    public Pixel plus(@NonNull Offsets offsets)
    {
        return new Pixel(x + offsets.dx(), y + offsets.dy());
    }

    @NonNull
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }

    @NonNull
    public static Pixel zero()
    {
        return ZERO;
    }
}
