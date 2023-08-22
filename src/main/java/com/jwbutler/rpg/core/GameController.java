package com.jwbutler.rpg.core;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameController
{
    /**
     * @throws IllegalArgumentException if {@code coordinates} are out of bounds
     */
    void moveUnit(@NonNull Unit unit, @NonNull Level level, @NonNull Coordinates coordinates);

    void dealDamage(@NonNull Unit source, @NonNull Unit target, int amount);

    @NonNull
    GameState getState();

    @NonNull
    static GameController create(@NonNull GameState state)
    {
        return new GameControllerImpl(state);
    }
}
