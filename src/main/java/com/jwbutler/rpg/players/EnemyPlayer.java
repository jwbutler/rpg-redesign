package com.jwbutler.rpg.players;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.NonNull;

public final class EnemyPlayer extends AbstractPlayer implements Player
{
    public EnemyPlayer(@NonNull Game game, @NonNull String name)
    {
        super(game, name, Faction.ENEMY);
    }
}
