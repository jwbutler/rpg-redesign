package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkState;

final class LevelImpl implements Level
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Map<Coordinates, Unit> coordinatesToUnit;

    LevelImpl(@Nonnull String name)
    {
        id = UUID.randomUUID();
        this.name = name;
        this.coordinatesToUnit = new HashMap<>();
    }

    @Nonnull
    @Override
    public UUID getId()
    {
        return id;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * @throws IllegalStateException if the unit's level pointer doesn't point to this livel,
     *                               or if the unit is already in this level
     */
    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        coordinatesToUnit.put(unit.getCoordinates(), unit);
    }

    /**
     * @throws IllegalArgumentException if the coordinates are not in this level's bounds
     */
    @CheckForNull
    @Override
    public Unit getUnit(@Nonnull Coordinates coordinates)
    {
        return coordinatesToUnit.get(coordinates);
    }

    @Nonnull
    @Override
    public Set<Unit> getUnits()
    {
        return new HashSet<>(coordinatesToUnit.values());
    }

    /**
     * @throws IllegalStateException if the unit is not in this level
     */
    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        var removed = coordinatesToUnit.remove(unit.getCoordinates());
        checkState(removed == unit);
    }
}
