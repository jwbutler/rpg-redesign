package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;

import static com.google.common.base.Preconditions.checkState;

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

    LevelBuilder addTile(@Nonnull Coordinates coordinates, @Nonnull TileType tileType)
    {
        coordinatesToTile.put(coordinates, tileType);
        return this;
    }

    LevelBuilder addTiles(@Nonnull Map<Coordinates, TileType> coordinatesToTile)
    {
        this.coordinatesToTile.putAll(coordinatesToTile);
        return this;
    }

    @Nonnull
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
