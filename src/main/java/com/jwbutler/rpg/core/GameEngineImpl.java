package com.jwbutler.rpg.core;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Unit;

final class GameEngineImpl implements GameEngine
{
    @Nonnull
    private final GameState state;

    GameEngineImpl(@Nonnull GameState state)
    {
        this.state = state;
    }

    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        state.addUnit(unit);
        unit.getLevel().addUnit(unit);
    }

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        state.removeUnit(unit);
        unit.getLevel().removeUnit(unit);
    }

    @Override
    public void moveUnit(@Nonnull Unit unit, @Nonnull Level level, @Nonnull Coordinates coordinates)
    {
        if (level != unit.getLevel())
        {
            unit.getLevel().removeUnit(unit);
        }
        unit.setLevel(level);
        level.addUnit(unit);
    }

    @Override
    public void addLevel(@Nonnull Level level)
    {
        state.addLevel(level);
    }
}
