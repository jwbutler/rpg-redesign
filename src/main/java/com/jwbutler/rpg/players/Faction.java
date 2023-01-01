package com.jwbutler.rpg.players;

import javax.annotation.Nonnull;

public enum Faction
{
    PLAYER,
    NEUTRAL,
    ENEMY;

    public boolean isHostile(@Nonnull Faction other)
    {
        return switch (this)
        {
            case PLAYER -> other == ENEMY;
            case NEUTRAL -> false;
            case ENEMY -> other == PLAYER;
        };
    }
}
