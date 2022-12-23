package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkArgument;

final class LevelImpl implements Level
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Dimensions dimensions;
    @Nonnull
    private final Map<Coordinates, TileType> coordinatesToTile;
    @Nonnull
    private final Map<Coordinates, Unit> coordinatesToUnit;

    LevelImpl(
        @Nonnull String name,
        @Nonnull Dimensions dimensions,
        @Nonnull Map<Coordinates, TileType> coordinatesToTile
    )
    {
        checkArgument(!name.isBlank());
        checkArgument(!coordinatesToTile.isEmpty());

        id = UUID.randomUUID();
        this.name = name;
        this.dimensions = dimensions;
        this.coordinatesToTile = coordinatesToTile;
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

    @Nonnull
    @Override
    public Dimensions getDimensions()
    {
        return dimensions;
    }

    @Override
    public boolean containsCoordinates(@Nonnull Coordinates coordinates)
    {
        return new Rect(0, 0, getDimensions().width(), getDimensions().height()).contains(coordinates);
    }

    @Nonnull
    @Override
    public TileType getTile(@Nonnull Coordinates coordinates)
    {
        checkArgument(
            coordinates.x() >= 0 && coordinates.x() < getDimensions().width(),
            "Out of bounds"
        );
        checkArgument(
            coordinates.y() >= 0 && coordinates.y() < getDimensions().height(),
            "Out of bounds"
        );
        return coordinatesToTile.get(coordinates);
    }

    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        checkArgument(containsCoordinates(unit.getCoordinates()));
        checkArgument(coordinatesToUnit.get(unit.getCoordinates()) == null);
        coordinatesToUnit.put(unit.getCoordinates(), unit);
    }

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

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        var unitAtCoordinates = coordinatesToUnit.get(unit.getCoordinates());
        checkArgument(unitAtCoordinates == unit);
        coordinatesToUnit.remove(unit.getCoordinates());
    }
}
