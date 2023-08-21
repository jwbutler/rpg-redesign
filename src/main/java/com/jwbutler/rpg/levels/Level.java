package com.jwbutler.rpg.levels;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;
import com.jwbutler.rpg.units.Unit;

public interface Level
{
    @NonNull
    UUID getId();

    @NonNull
    String getName();

    @NonNull
    Dimensions getDimensions();

    /**
     * @return whether the specified coordinates are in-bounds
     */
    boolean containsCoordinates(@NonNull Coordinates coordinates);

    @NonNull
    Set<Coordinates> getAllCoordinates();

    /**
     * @throws IllegalArgumentException if the specified coordinates are out of bounds
     */
    @NonNull
    TileType getTile(@NonNull Coordinates coordinates);

    /**
     * @throws IllegalStateException if the unit's level pointer doesn't point to this livel,
     *                               or if the unit is already in this level
     */
    void addUnit(@NonNull Unit unit);

    /**
     * @throws IllegalArgumentException if the coordinates are not in this level's bounds
     */
    @Nullable Unit getUnit(@NonNull Coordinates coordinates);

    @NonNull Set<Unit> getUnits();
    /**
     * @throws IllegalArgumentException if the unit is not in this level
     */
    void removeUnit(@NonNull Unit unit);

    @NonNull
    static Level create(
        @NonNull String name,
        @NonNull Dimensions dimensions,
        @NonNull Map<Coordinates, TileType> coordinatesToTile
    )
    {
        return new LevelImpl(name, dimensions, coordinatesToTile);
    }
}
