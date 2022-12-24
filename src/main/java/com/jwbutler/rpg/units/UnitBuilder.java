package com.jwbutler.rpg.units;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.sprites.Sprite;

import static com.google.common.base.Preconditions.checkState;

final class UnitBuilder
{
    private String name;
    private Integer life;
    private AnimatedSprite<Unit> sprite;
    private Player player;
    private Level level;
    private Coordinates coordinates;

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

    @Nonnull
    Unit build()
    {
        checkState(name != null);
        checkState(life != null);
        checkState(sprite != null);
        checkState(player != null);
        checkState(level != null);
        checkState(coordinates != null);

        return new UnitImpl(
            name,
            life,
            sprite,
            player,
            level,
            coordinates
        );
    }
}
