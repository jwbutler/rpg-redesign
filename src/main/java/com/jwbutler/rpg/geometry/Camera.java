package com.jwbutler.rpg.geometry;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_WIDTH;

public final class Camera
{
    @NonNull
    private final Game game;
    @NonNull
    private Coordinates coordinates;

    public Camera(@NonNull Game game, @NonNull Coordinates coordinates)
    {
        this.game = game;
        this.coordinates = coordinates;
    }

    public void setCoordinates(@NonNull Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public void move(@NonNull Direction direction)
    {
        var coordinates = this.coordinates.plus(direction);
        var level = game.getCurrentLevel();
        if (level.containsCoordinates(coordinates))
        {
            this.coordinates = coordinates;
        }
    }

    /**
     * The center coordinate is centered in the middle of the screen.  Its top-center position is the "true zero" of our
     * coordinate system,  We can then take relative pixel lengths relative to this point and divide by the tile
     * dimensions.
     */
    @NonNull
    public Coordinates pixelToCoordinates(@NonNull Pixel pixel)
    {
        // relative to the top-center of the center coordinate
        var x = pixel.x() - GAME_WIDTH / 2;
        var y = pixel.y() - GAME_HEIGHT / 2 + TILE_HEIGHT / 2;
        var cameraCoordinates = this.coordinates;

        return new Coordinates(
            (int) Math.floor(1.0 * x / TILE_WIDTH + 1.0 * y / TILE_HEIGHT) + cameraCoordinates.x(),
            (int) Math.floor(1.0 * y / TILE_HEIGHT - 1.0 * x / TILE_WIDTH) + cameraCoordinates.y()
        );
    }

    @NonNull
    public Rect coordinatesToPixelRect(@NonNull Coordinates coordinates)
    {
        var cameraCoordinates = this.coordinates;
        var left = (coordinates.x() - cameraCoordinates.x() - coordinates.y() + cameraCoordinates.y() - 1) * (TILE_WIDTH / 2) + GAME_WIDTH / 2;
        var top = (coordinates.x() - cameraCoordinates.x() + coordinates.y() - cameraCoordinates.y() - 1) * (TILE_HEIGHT / 2) + GAME_HEIGHT / 2;
        return new Rect(left, top, TILE_WIDTH, TILE_HEIGHT);
    }
}
