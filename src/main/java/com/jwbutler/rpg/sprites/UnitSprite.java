package com.jwbutler.rpg.sprites;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.AnimationPack;
import com.jwbutler.rpg.sprites.animations.Frame;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkState;

public final class UnitSprite implements AnimatedSprite<Unit>
{
    @Nonnull
    private final AnimationPack animations;

    public UnitSprite(@Nonnull AnimationPack animations)
    {
        this.animations = animations;
    }

    @Nonnull
    @Override
    public Frame getFrame(@Nonnull Unit target)
    {
        var animation = getAnimation(target);
        checkState(target.getFrameNumber() < animation.frames().size());
        return animation.frames().get(target.getFrameNumber());
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
