package com.jwbutler.rpg.sprites;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.animations.Frame;

public interface Sprite<T>
{
    @NonNull
    Frame getFrame(@NonNull T target);
}
