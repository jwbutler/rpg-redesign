package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import static com.google.common.base.Preconditions.checkState;

public final class ImageUtils
{
    private ImageUtils() {}

    @Nonnull
    public static BufferedImage loadImage(@Nonnull String filename)
    {
        var fullFilename = "/png/" + filename + ".png";
        try
        {
            var url = ImageUtils.class.getResource(fullFilename);
            checkState(url != null, "Could not find image " + fullFilename);
            var image = ImageIO.read(url);
            var argb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            argb.getGraphics().drawImage(image, 0, 0, null);
            return argb;
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public static boolean imageFileExists(@Nonnull String filename)
    {
        var fullFilename = "/png/" + filename + ".png";
        var url = ImageUtils.class.getResource(fullFilename);
        return url != null;
    }

    public static void setTransparentColor(@Nonnull BufferedImage image, @Nonnull Color transparentColor)
    {
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                var rgb = image.getRGB(x, y);
                if (rgb == transparentColor.getRGB())
                {
                    image.setRGB(x, y, Colors.TRANSPARENT_BLACK.getRGB());
                }
            }
        }
    }

    /**
     * TODO optimize me
     */
    public static void applyPaletteSwaps(@Nonnull BufferedImage image, @Nonnull Map<Color, Color> paletteSwaps)
    {
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                var rgb = image.getRGB(x, y);
                var color = new Color(rgb);
                var swapped = paletteSwaps.get(color);
                if (swapped != null)
                {
                    image.setRGB(x, y, swapped.getRGB());
                }
            }
        }
    }
}
