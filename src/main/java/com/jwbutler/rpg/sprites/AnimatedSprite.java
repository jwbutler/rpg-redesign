package com.jwbutler.rpg.sprites;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.animations.Animation;

public interface AnimatedSprite<T> extends Sprite<T>
{
    @Nonnull
    Animation getAnimation(@Nonnull T target);
}
