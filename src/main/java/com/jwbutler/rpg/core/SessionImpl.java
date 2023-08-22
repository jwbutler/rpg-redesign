package com.jwbutler.rpg.core;

import com.jwbutler.rpg.players.HumanPlayer;
import org.jspecify.annotations.NonNull;

final class SessionImpl implements Session
{
    @NonNull
    private final HumanPlayer player;
    
    public SessionImpl(@NonNull HumanPlayer player)
    {
        this.player = player;
    }

    @Override
    @NonNull
    public HumanPlayer getPlayer()
    {
        return player;
    }
}
