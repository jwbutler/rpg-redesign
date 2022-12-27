package com.jwbutler.rpg.players;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;

public final class EnemyPlayer extends AbstractPlayer implements Player
{
    public EnemyPlayer(@Nonnull GameController controller, @Nonnull String name)
    {
        super(controller, name, Faction.ENEMY);
    }
}
