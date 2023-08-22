package com.jwbutler.rpg.players;

import org.jspecify.annotations.NonNull;

public enum Faction
{
    PLAYER,
    NEUTRAL,
    ENEMY;

    public boolean isHostileToward(@NonNull Faction other)
    {
        return switch (this)
        {
            case PLAYER -> other == ENEMY;
            case NEUTRAL -> false;
            case ENEMY -> other == PLAYER;
        };
    }
}
