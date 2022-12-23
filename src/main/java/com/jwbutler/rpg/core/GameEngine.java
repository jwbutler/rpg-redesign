package com.jwbutler.rpg.core;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameEngine
{
    void addUnit(@Nonnull Unit unit);
    void removeUnit(@Nonnull Unit unit);
    void moveUnit(@Nonnull Unit unit, @Nonnull Level level, @Nonnull Coordinates coordinates);

    void addLevel(@Nonnull Level level);

    void addPlayer(@Nonnull Player player);

    @Nonnull
    GameState getState();

    @Nonnull
    static GameEngine create(@Nonnull GameState state)
    {
        return new GameEngineImpl(state);
    }
}
