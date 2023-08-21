package com.jwbutler.rpg.sprites;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.animations.Animation;

public interface AnimatedSprite<T> extends Sprite<T>
{
    @NonNull
    Animation getAnimation(@NonNull T target);
}
