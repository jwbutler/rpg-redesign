package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkArgument;

final class LevelImpl implements Level
{
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;
    @NonNull
    private final Dimensions dimensions;
    @NonNull
    private final Map<Coordinates, TileType> coordinatesToTile;
    @NonNull
    private final Map<Coordinates, Unit> coordinatesToUnit;

    LevelImpl(
        @NonNull String name,
        @NonNull Dimensions dimensions,
        @NonNull Map<Coordinates, TileType> coordinatesToTile
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

    @NonNull
    @Override
    public UUID getId()
    {
        return id;
    }

    @NonNull
    @Override
    public String getName()
    {
        return name;
    }

    @NonNull
    @Override
    public Dimensions getDimensions()
    {
        return dimensions;
    }

    @Override
    public boolean containsCoordinates(@NonNull Coordinates coordinates)
    {
        return new Rect(0, 0, getDimensions().width(), getDimensions().height()).contains(coordinates);
    }

    @NonNull
    @Override
    public Set<Coordinates> getAllCoordinates()
    {
        return coordinatesToTile.keySet();
    }

    @NonNull
    @Override
    public TileType getTile(@NonNull Coordinates coordinates)
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
    public void addUnit(@NonNull Unit unit)
    {
        checkArgument(containsCoordinates(unit.getCoordinates()));
        checkArgument(coordinatesToUnit.get(unit.getCoordinates()) == null);
        coordinatesToUnit.put(unit.getCoordinates(), unit);
    }

    @Nullable
    @Override
    public Unit getUnit(@NonNull Coordinates coordinates)
    {
        return coordinatesToUnit.get(coordinates);
    }

    @NonNull
    @Override
    public Set<Unit> getAllUnits()
    {
        return new HashSet<>(coordinatesToUnit.values());
    }

    @Override
    public void removeUnit(@NonNull Unit unit)
    {
        var unitAtCoordinates = coordinatesToUnit.get(unit.getCoordinates());
        checkArgument(unitAtCoordinates == unit);
        coordinatesToUnit.remove(unit.getCoordinates());
    }
}
