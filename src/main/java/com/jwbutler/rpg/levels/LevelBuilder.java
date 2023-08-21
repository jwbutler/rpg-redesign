package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;

import static com.jwbutler.rpg.util.Preconditions.checkState;

final class LevelBuilder
{
    private String name;
    private Dimensions dimensions;
    private final Map<Coordinates, TileType> coordinatesToTile;

    LevelBuilder()
    {
        this.coordinatesToTile = new HashMap<>();
    }

    LevelBuilder name(String name)
    {
        this.name = name;
        return this;
    }

    LevelBuilder dimensions(Dimensions dimensions)
    {
        this.dimensions = dimensions;
        return this;
    }

    LevelBuilder addTile(@NonNull Coordinates coordinates, @NonNull TileType tileType)
    {
        coordinatesToTile.put(coordinates, tileType);
        return this;
    }

    LevelBuilder addTiles(@NonNull Map<Coordinates, TileType> coordinatesToTile)
    {
        this.coordinatesToTile.putAll(coordinatesToTile);
        return this;
    }

    @NonNull
    Level build()
    {
        checkState(name != null);
        checkState(dimensions != null);

        return new LevelImpl(
            name,
            dimensions,
            coordinatesToTile
        );
    }
}
