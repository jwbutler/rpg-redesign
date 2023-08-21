package com.jwbutler.rpg.players;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.core.GameController;

public final class EnemyPlayer extends AbstractPlayer implements Player
{
    public EnemyPlayer(@NonNull GameController controller, @NonNull String name)
    {
        super(controller, name, Faction.ENEMY);
    }
}
