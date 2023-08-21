package com.jwbutler.rpg.sprites;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.AnimationPack;
import com.jwbutler.rpg.sprites.animations.Frame;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkState;

public final class UnitSprite implements AnimatedSprite<Unit>
{
    @NonNull
    private final AnimationPack animations;

    public UnitSprite(@NonNull AnimationPack animations)
    {
        this.animations = animations;
    }

    @NonNull
    @Override
    public Frame getFrame(@NonNull Unit target)
    {
        var animation = getAnimation(target);
        checkState(target.getFrameNumber() < animation.frames().size());
        return animation.frames().get(target.getFrameNumber());
    }

    @NonNull
    @Override
    public Animation getAnimation(@NonNull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
