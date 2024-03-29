package com.jwbutler.rpg.units;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.AnimatedSprite;

import static com.jwbutler.rpg.util.Preconditions.checkState;

final class UnitBuilder
{
    private Game game;
    private String name;
    private Integer life;
    private AnimatedSprite<Unit> sprite;
    private Player player;
    private Level level;
    private Coordinates coordinates;

    UnitBuilder game(Game game)
    {
        this.game = game;
        return this;
    }

    UnitBuilder name(String name)
    {
        this.name = name;
        return this;
    }

    UnitBuilder life(int life)
    {
        this.life = life;
        return this;
    }

    UnitBuilder player(Player player)
    {
        this.player = player;
        return this;
    }

    UnitBuilder level(Level level)
    {
        this.level = level;
        return this;
    }

    UnitBuilder coordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
        return this;
    }

    UnitBuilder sprite(AnimatedSprite<Unit> sprite)
    {
        this.sprite = sprite;
        return this;
    }

    @NonNull
    Unit build()
    {
        checkState(game != null);
        checkState(name != null);
        checkState(life != null);
        checkState(sprite != null);
        checkState(player != null);
        checkState(level != null);
        checkState(coordinates != null);

        return new UnitImpl(
            game,
            name,
            life,
            sprite,
            player,
            level,
            coordinates
        );
    }
}
