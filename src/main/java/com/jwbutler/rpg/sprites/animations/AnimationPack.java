package com.jwbutler.rpg.sprites.animations;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;

public interface AnimationPack
{
    @NonNull
    Animation getAnimation(@NonNull Activity activity, @NonNull Direction direction);
}
