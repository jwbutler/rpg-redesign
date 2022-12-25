package com.jwbutler.rpg.core;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameController
{
    /**
     * @throws IllegalArgumentException if the unit's coordinates are out of bounds
     */
    void addUnit(@Nonnull Unit unit);
    /**
     * @throws IllegalArgumentException if the unit is not present in the game state
     */
    void removeUnit(@Nonnull Unit unit);

    /**
     * @throws IllegalArgumentException if {@code coordinates} are out of bounds
     */
    void moveUnit(@Nonnull Unit unit, @Nonnull Level level, @Nonnull Coordinates coordinates);

    void addLevel(@Nonnull Level level);

    void addPlayer(@Nonnull Player player);

    @Nonnull
    GameState getState();

    @Nonnull
    static GameController create(@Nonnull GameState state)
    {
        return new GameControllerImpl(state);
    }
}
