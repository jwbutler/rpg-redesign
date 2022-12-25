package com.jwbutler.rpg.sprites;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

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
        var animation = getAnimation(target);
        var filename = animation.getFilenames().get(target.getFrameNumber());
        try
        {
            var url = getClass().getResource("/png/" + filename + ".png");
            checkState(url != null);
            var image = ImageIO.read(url);
            var argb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            argb.getGraphics().drawImage(image, 0, 0, null);
            var TRANSPARENT = new Color(0, 0, 0, 0).getRGB();
            for (int y = 0; y < argb.getHeight(); y++)
            {
                for (int x = 0; x < argb.getWidth(); x++)
                {
                    var rgb = argb.getRGB(x, y);
                    if (rgb == Color.WHITE.getRGB())
                    {
                        argb.setRGB(x, y, TRANSPARENT);
                    }
                }
            }
            return argb;
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Unit target)
    {
        return animations.getAnimation(target.getActivity(), target.getDirection());
    }
}
