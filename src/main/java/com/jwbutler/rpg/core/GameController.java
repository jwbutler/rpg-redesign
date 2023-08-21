package com.jwbutler.rpg.core;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameController
{
    /**
     * @throws IllegalArgumentException if the unit's coordinates are out of bounds
     */
    void addUnit(@NonNull Unit unit);
    /**
     * @throws IllegalArgumentException if the unit is not present in the game state
     */
    void removeUnit(@NonNull Unit unit);

    /**
     * @throws IllegalArgumentException if {@code coordinates} are out of bounds
     */
    void moveUnit(@NonNull Unit unit, @NonNull Level level, @NonNull Coordinates coordinates);

    void addLevel(@NonNull Level level);

    void addPlayer(@NonNull Player player);

    void dealDamage(@NonNull Unit source, @NonNull Unit target, int amount);

    @NonNull
    GameState getState();

    @NonNull
    static GameController create(@NonNull GameState state)
    {
        return new GameControllerImpl(state);
    }
}
