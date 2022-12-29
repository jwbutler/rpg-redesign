package com.jwbutler.rpg.sprites;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.animations.Frame;

public interface Sprite<T>
{
    @Nonnull
    Frame getFrame(@Nonnull T target);
}
