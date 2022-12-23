package com.jwbutler.rpg.levels;

import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Unit;

public interface Level
{
    @Nonnull UUID getId();

    @Nonnull String getName();

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
     * @throws IllegalStateException if the unit is not in this level
     */
    void removeUnit(@Nonnull Unit unit);

    @Nonnull
    static Level create(@Nonnull String name)
    {
        return new LevelImpl(name);
    }
}
