package com.jwbutler.rpg.units;

import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;

public interface Unit
{
    @Nonnull UUID getId();
    @Nonnull String getName();
    int getLife();
    int getMaxLife();
    @Nonnull Activity getActivity();
    void setActivity(@Nonnull Activity activity);
    @Nonnull Command getCommand();
    void setCommand(@Nonnull Command command);
    @Nonnull Player getPlayer();
    @Nonnull Level getLevel();
    void setLevel(@Nonnull Level level);
    @Nonnull Coordinates getCoordinates();
    void setCoordinates(@Nonnull Coordinates coordinates);

    @Nonnull
    static Unit create(
        @Nonnull String name,
        int life,
        @Nonnull Player player,
        @Nonnull Level level,
        @Nonnull Coordinates coordinates
    )
    {
        return new UnitImpl(name, life, player, level, coordinates);
    }
}
