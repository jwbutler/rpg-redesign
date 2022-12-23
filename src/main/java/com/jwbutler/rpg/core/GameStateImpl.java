package com.jwbutler.rpg.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

final class GameStateImpl implements GameState
{
    @Nonnull
    private final Map<UUID, Unit> unitsById;
    @Nonnull
    private final Map<UUID, Level> levelsById;

    GameStateImpl()
    {
        unitsById = new HashMap<>();
        levelsById = new HashMap<>();
    }

    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        checkArgument(unitsById.get(unit.getId()) == null);
        unitsById.put(unit.getId(), unit);
    }

    @Override
    @Nonnull
    public Unit getUnit(@Nonnull UUID id)
    {
        var unit = unitsById.get(id);
        checkArgument(unit != null);
        return unit;
    }

    @Override
    @CheckForNull
    public Unit getUnitNullable(@Nonnull UUID id)
    {
        return unitsById.get(id);
    }

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        var removed = unitsById.remove(unit.getId());
        checkState(removed == unit);
    }

    @Override
    public void addLevel(@Nonnull Level level)
    {
        checkArgument(levelsById.get(level.getId()) == null);
        levelsById.put(level.getId(), level);
    }

    @Override
    @Nonnull
    public Level getLevel(@Nonnull UUID id)
    {
        var level = levelsById.get(id);
        checkArgument(level != null);
        return level;
    }
}
