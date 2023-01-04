package com.jwbutler.rpg.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.graphics.swing.SwingImage;

import static com.google.common.base.Preconditions.checkState;

public final class ImageUtils
{
    private ImageUtils() {}

    @Nonnull
    public static Image loadImage(@Nonnull String filename)
    {
        var fullFilename = "/png/" + filename + ".png";
        try
        {
            var url = ImageUtils.class.getResource(fullFilename);
            checkState(url != null, "Could not find image " + fullFilename);
            var image = ImageIO.read(url);
            var argb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            argb.getGraphics().drawImage(image, 0, 0, null);
            return new SwingImage(argb);
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

    public static void setTransparentColor(@Nonnull Image image, @Nonnull Color transparentColor)
    {
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                var pixel = new Pixel(x, y);
                if (image.getColor(pixel).equals(transparentColor))
                {
                    image.setColor(pixel, Colors.TRANSPARENT_BLACK);
                }
            }
        }
    }

    /**
     * TODO optimize me
     */
    public static void applyPaletteSwaps(@Nonnull Image image, @Nonnull Map<Color, Color> paletteSwaps)
    {
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                var pixel = new Pixel(x, y);
                var color = image.getColor(pixel);
                var swapped = paletteSwaps.get(color);
                if (swapped != null)
                {
                    image.setColor(pixel, swapped);
                }
            }
        }
    }
}
