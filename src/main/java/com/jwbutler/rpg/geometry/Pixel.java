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

  /**
   * The center coordinate is centered in the middle of the screen.  Its top-center position is the "true zero" of our
   * coordinate system,  We can then take relative pixel lengths relative to this point and divide by the tile
   * dimensions.
   *
   * This is the inverse of {@link Coordinates#toPixel}.
   */
  @Nonnull
  public Coordinates toCoordinates(@Nonnull Coordinates cameraCoordinates)
  {
      // relative to the top-center of the center coordinate
      var x = x() - GAME_WIDTH / 2;
      var y = y() - GAME_HEIGHT / 2 + TILE_HEIGHT / 2;

      return new Coordinates(
          (int) Math.floor(1.0 * x / TILE_WIDTH + 1.0 * y / TILE_HEIGHT) + cameraCoordinates.x(),
          (int) Math.floor(1.0 * y / TILE_HEIGHT - 1.0 * x / TILE_WIDTH) + cameraCoordinates.y()
      );
  }
}
