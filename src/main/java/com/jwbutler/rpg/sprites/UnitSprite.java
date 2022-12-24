package com.jwbutler.rpg.sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.animations.Animation;
import com.jwbutler.rpg.sprites.animations.UnitAnimations;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkState;

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
        var image = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        var graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 6));
        var animation = getAnimation(target);
        var filename = animation.getFilenames().get(target.getFrameNumber());
        graphics.drawString(filename, 0, 10);
        System.out.println(filename);
        return image;
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
