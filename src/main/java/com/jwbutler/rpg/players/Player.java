package com.jwbutler.rpg.players;

import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.Unit;

public interface Player
{
    @Nonnull
    UUID getId();

    @Nonnull
    String getName();

    @Nonnull
    Faction getFaction();

    void addUnit(@Nonnull Unit unit);

    @Nonnull
    Set<Unit> getUnits();

    void removeUnit(@Nonnull Unit unit);

    @Nonnull
    static Player create(@Nonnull String name, @Nonnull Faction faction)
    {
        return new PlayerImpl(name, faction);
    }
}
