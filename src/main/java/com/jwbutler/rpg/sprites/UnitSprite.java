package com.jwbutler.rpg.sprites;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.UnitAnimations;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.graphics.ImageUtils.loadImage;

public final class UnitSprite implements AnimatedSprite<Unit>
{
    @Nonnull
    private final UnitAnimations animations;
    @Nonnull
    private final Map<Color, Color> paletteSwaps;

    public UnitSprite(
        @Nonnull UnitAnimations animations,
        @Nonnull Map<Color, Color> paletteSwaps
    )
    {
        this.animations = animations;
        this.paletteSwaps = paletteSwaps;
    }

    @Nonnull
    @Override
    public BufferedImage getImage(@Nonnull Unit target)
    {
        var animation = getAnimation(target);
        var filename = animation.filenames().get(target.getFrameNumber());
        return new ImageBuilder()
            .filename(filename)
            .transparentColor(Colors.WHITE)
            .build();
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
