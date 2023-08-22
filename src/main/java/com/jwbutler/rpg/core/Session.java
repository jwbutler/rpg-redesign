package com.jwbutler.rpg.core;

import com.jwbutler.rpg.players.HumanPlayer;
import org.jspecify.annotations.NonNull;

/**
 * With an eye toward multiplayer support - this is a container for player-specific data
 */
public interface Session
{
    @NonNull
    HumanPlayer getPlayer();
    
    @NonNull
    static Session create(@NonNull HumanPlayer player)
    {
        return new SessionImpl(player);
    }
}
