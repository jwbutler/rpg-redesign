package com.jwbutler.rpg.core;

import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import org.jspecify.annotations.NonNull;

/**
 * Awkwardly separating out some functionality from {@link Game}
 * mostly because I don't want to get too attached to this API
 */
public final class GameUtils
{
    private GameUtils() {}

    @NonNull
    public static HumanPlayer getHumanPlayer(@NonNull Game game)
    {
        return game.getPlayers()
            .stream()
            .filter(player -> player.getFaction() == Faction.PLAYER)
            .filter(HumanPlayer.class::isInstance)
            .map(HumanPlayer.class::cast)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }
}
