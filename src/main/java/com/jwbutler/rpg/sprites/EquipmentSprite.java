package com.jwbutler.rpg.sprites;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.AnimationPack;
import com.jwbutler.rpg.sprites.animations.Frame;

public final class EquipmentSprite implements AnimatedSprite<Equipment>
{
    @NonNull
    private final AnimationPack animations;

    public EquipmentSprite(
        @NonNull AnimationPack animations
    )
    {
        this.animations = animations;
    }

    @NonNull
    @Override
    public Frame getFrame(@NonNull Equipment target)
    {
        var animation = getAnimation(target);
        var unit = target.getUnit();
        return animation.frames().get(unit.getFrameNumber());
    }

    @NonNull
    @Override
    public Animation getAnimation(@NonNull Equipment target)
    {
        var unit = target.getUnit();
        return animations.getAnimation(unit.getActivity(), unit.getDirection());
    }
}
