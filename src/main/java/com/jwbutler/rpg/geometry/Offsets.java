package com.jwbutler.rpg.geometry;

import org.jspecify.annotations.NonNull;

public record Offsets
(
    int dx,
    int dy
)
{
    private static final Offsets ZERO = new Offsets(0, 0);

    @NonNull
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", dx, dy);
    }

    @NonNull
    public static Offsets zero()
    {
        return ZERO;
    }
}
