package com.jwbutler.rpg.core;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
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
        unit.getPlayer().addUnit(unit);
    }

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        state.removeUnit(unit);
        unit.getLevel().removeUnit(unit);
        unit.getPlayer().removeUnit(unit);
    }

    @Override
    public void moveUnit(@Nonnull Unit unit, @Nonnull Level level, @Nonnull Coordinates coordinates)
    {
        unit.getLevel().removeUnit(unit);
        unit.setLevel(level);
        unit.setCoordinates(coordinates);
        level.addUnit(unit);
    }

    @Override
    public void addLevel(@Nonnull Level level)
    {
        state.addLevel(level);
    }

    @Override
    public void addPlayer(@Nonnull Player player)
    {
        state.addPlayer(player);
    }

    @Nonnull
    @Override
    public GameState getState()
    {
        return state;
    }
}
