package com.jwbutler.rpg.core;

import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Unit;

public interface GameState
{
    /**
     * @throws IllegalArgumentException if we already have a unit with the specified id
     */
    void addUnit(@Nonnull Unit unit);

    /**
     * @throws IllegalArgumentException if there is no unit with the specified id
     */
    @Nonnull
    Unit getUnit(@Nonnull UUID id);

    @CheckForNull
    @VisibleForTesting
    Unit getUnitNullable(@Nonnull UUID id);

    void removeUnit(@Nonnull Unit unit);

    /**
     * @throws IllegalArgumentException if we already have a level with the specified id
     */
    void addLevel(@Nonnull Level level);

    /**
     * @throws IllegalArgumentException if there is no level with the specified id
     */
    @Nonnull
    Level getLevel(@Nonnull UUID id);

    @Nonnull
    static GameState create()
    {
        return new GameStateImpl();
    }
}
