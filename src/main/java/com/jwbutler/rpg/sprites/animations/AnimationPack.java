package com.jwbutler.rpg.sprites.animations;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;

public interface AnimationPack
{
    @Nonnull
    Animation getAnimation(@Nonnull Activity activity, @Nonnull Direction direction);
}
