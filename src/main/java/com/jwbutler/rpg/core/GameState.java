package com.jwbutler.rpg.core;

import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameState
{
    void addPlayer(@NonNull Player player);

    /**
     * @throws IllegalArgumentException if there is no player with the specified id
     */
    @NonNull
    Player getPlayer(@NonNull UUID id);
    
    @NonNull Set<Player> getPlayers();

    /**
     * @throws IllegalArgumentException if we already have a level with the specified id
     */
    void addLevel(@NonNull Level level);

    /**
     * @throws IllegalArgumentException if there is no level with the specified id
     */
    @NonNull
    Level getLevel(@NonNull UUID id);
    
    @NonNull
    Level getCurrentLevel();

    void setCurrentLevel(@NonNull Level currentLevel);

    /**
     * @throws IllegalArgumentException if we already have a unit with the specified id
     */
    void addUnit(@NonNull Unit unit);

    /**
     * @throws IllegalArgumentException if there is no unit with the specified id
     */
    @NonNull
    Unit getUnit(@NonNull UUID id);

    @Nullable
    Unit getUnitNullable(@NonNull UUID id);

    void removeUnit(@NonNull Unit unit);

    @NonNull
    static GameState create()
    {
        return new GameStateImpl();
    }
}
