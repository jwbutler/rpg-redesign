package com.jwbutler.rpg.core;

import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

public interface GameState
{
    void addPlayer(@Nonnull Player player);

    /**
     * @throws IllegalArgumentException if there is no player with the specified id
     */
    @Nonnull
    Player getPlayer(@Nonnull UUID id);

    @Nonnull
    @Deprecated
    Player getHumanPlayer();

    /**
     * @throws IllegalArgumentException if we already have a level with the specified id
     */
    void addLevel(@Nonnull Level level);

    /**
     * @throws IllegalArgumentException if there is no level with the specified id
     */
    @Nonnull
    Level getLevel(@Nonnull UUID id);

    /**
     * @deprecated - this should be per-player, not global
     */
    @Deprecated
    void setCurrentLevel(@Nonnull Level currentLevel);

    /**
     * @deprecated - this should be per-player, not global
     */
    @Nonnull
    @Deprecated
    Level getCurrentLevel();

    /**
     * @throws IllegalArgumentException if we already have a unit with the specified id
     */
    void addUnit(@Nonnull Unit unit);

    /**
     * @throws IllegalArgumentException if there is no unit with the specified id
     */
    @Nonnull
    Unit getUnit(@Nonnull UUID id);

    /**
     * @deprecated - this should be per-player, not global
     */
    @Nonnull
    @Deprecated
    Unit getPlayerUnit();

    @CheckForNull
    @VisibleForTesting
    Unit getUnitNullable(@Nonnull UUID id);

    void removeUnit(@Nonnull Unit unit);

    @Nonnull
    @Deprecated
    Coordinates getCameraCoordinates();

    @Deprecated
    void setCameraCoordinates(@Nonnull Coordinates coordinates);

    @Nonnull
    static GameState create(@Nonnull Coordinates cameraCoordinates)
    {
        return new GameStateImpl(cameraCoordinates);
    }
}
