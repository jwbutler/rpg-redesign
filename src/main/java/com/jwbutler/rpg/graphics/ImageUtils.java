package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import static com.google.common.base.Preconditions.checkState;

public final class ImageUtils
{
    private ImageUtils() {}


    @Nonnull
    public static BufferedImage loadImage(@Nonnull String filename, @Nonnull Color transparentColor)
    {
        var fullFilename = "/png/" + filename + ".png";
        try
        {
            var url = ImageUtils.class.getResource(fullFilename);
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
                    if (rgb == transparentColor.getRGB())
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
}
