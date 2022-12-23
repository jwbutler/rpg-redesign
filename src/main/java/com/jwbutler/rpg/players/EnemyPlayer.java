package com.jwbutler.rpg.players;

import javax.annotation.Nonnull;

public final class EnemyPlayer extends AbstractPlayer implements Player
{
    public EnemyPlayer(@Nonnull String name)
    {
        super(name, Faction.ENEMY);
    }
}
