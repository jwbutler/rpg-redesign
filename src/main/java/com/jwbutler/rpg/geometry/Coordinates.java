package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_WIDTH;

public record Coordinates
(
    int x,
    int y
)
implements Point
{
    private static final Coordinates ZERO = new Coordinates(0, 0);

    @Nonnull
    public Coordinates plus(int dx, int dy)
    {
        return new Coordinates(x + dx, y + dy);
    }

    @Nonnull
    public Coordinates plus(@Nonnull Direction direction)
    {
        return new Coordinates(x + direction.dx(), y + direction.dy());
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

    /**
     * @return the top-left corner of the resulting grid tile
     */
    @Nonnull
    public Pixel toPixel(@Nonnull Coordinates cameraCoordinates)
    {
        var x = (x() - cameraCoordinates.x() - y() + cameraCoordinates.y() - 1) * (TILE_WIDTH / 2) + GAME_WIDTH / 2;
        var y = (x() - cameraCoordinates.x() + y() - cameraCoordinates.y() - 1) * (TILE_HEIGHT / 2) + GAME_HEIGHT / 2;
        return new Pixel(x, y);
    }
}
