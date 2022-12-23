package com.jwbutler.rpg.levels;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;
import com.jwbutler.rpg.units.Unit;

public interface Level
{
    @Nonnull UUID getId();

    @Nonnull String getName();

    @Nonnull Dimensions getDimensions();

    /**
     * @return whether the specified coordinates are in-bounds
     */
    boolean containsCoordinates(@Nonnull Coordinates coordinates);
    /**
     * @throws IllegalArgumentException if the specified coordinates are out of bounds
     */
    @Nonnull TileType getTile(@Nonnull Coordinates coordinates);

    /**
     * @throws IllegalStateException if the unit's level pointer doesn't point to this livel,
     *                               or if the unit is already in this level
     */
    void addUnit(@Nonnull Unit unit);

    /**
     * @throws IllegalArgumentException if the coordinates are not in this level's bounds
     */
    @CheckForNull Unit getUnit(@Nonnull Coordinates coordinates);

    @Nonnull Set<Unit> getUnits();
    /**
     * @throws IllegalArgumentException if the unit is not in this level
     */
    void removeUnit(@Nonnull Unit unit);

    @Nonnull
    static Level create(
        @Nonnull String name,
        @Nonnull Dimensions dimensions,
        @Nonnull Map<Coordinates, TileType> coordinatesToTile
    )
    {
        return new LevelImpl(name, dimensions, coordinatesToTile);
    }
}
