package com.jwbutler.rpg.sprites;

import java.awt.Color;
import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.AnimationPack;
import com.jwbutler.rpg.sprites.animations.Frame;
import com.jwbutler.rpg.units.Unit;

public final class UnitSprite implements AnimatedSprite<Unit>
{
    @Nonnull
    private final AnimationPack animations;
    @Nonnull
    private final Map<Color, Color> paletteSwaps;

    public UnitSprite(
        @Nonnull AnimationPack animations,
        @Nonnull Map<Color, Color> paletteSwaps
    )
    {
        this.animations = animations;
        this.paletteSwaps = paletteSwaps;
    }

    @Nonnull
    @Override
    public Frame getFrame(@Nonnull Unit target)
    {
        var animation = getAnimation(target);
        return animation.frames().get(target.getFrameNumber());
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
