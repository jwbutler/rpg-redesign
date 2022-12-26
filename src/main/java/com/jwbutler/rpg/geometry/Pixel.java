package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_WIDTH;

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
