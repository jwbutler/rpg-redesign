package com.jwbutler.rpg.sprites;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.AnimationPack;
import com.jwbutler.rpg.sprites.animations.Frame;

public final class EquipmentSprite implements AnimatedSprite<Equipment>
{
    @Nonnull
    private final AnimationPack animations;

    public EquipmentSprite(
        @Nonnull AnimationPack animations
    )
    {
        this.animations = animations;
    }

    @Nonnull
    @Override
    public Frame getFrame(@Nonnull Equipment target)
    {
        var animation = getAnimation(target);
        var unit = target.getUnit();
        return animation.frames().get(unit.getFrameNumber());
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Equipment target)
    {
        var unit = target.getUnit();
        return animations.getAnimation(unit.getActivity(), unit.getDirection());
    }
}
