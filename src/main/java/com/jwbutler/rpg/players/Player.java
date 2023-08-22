package com.jwbutler.rpg.players;

import java.util.Set;
import java.util.UUID;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.units.Unit;

public interface Player
{
    @NonNull
    UUID getId();

    @NonNull
    String getName();

    @NonNull
    Faction getFaction();

    void addUnit(@NonNull Unit unit);

    @NonNull
    Set<Unit> getUnits();

    void removeUnit(@NonNull Unit unit);
    
    @NonNull
    static Player create(@NonNull Game game, @NonNull String name, @NonNull Faction faction)
    {
        return new PlayerImpl(game, name, faction);
    }
}
