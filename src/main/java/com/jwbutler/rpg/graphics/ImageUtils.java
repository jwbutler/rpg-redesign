package com.jwbutler.rpg.graphics;

import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.graphics.swing.SwingImage;
import com.jwbutler.rpg.ui.ClientType;

import static com.google.common.base.Preconditions.checkState;

public final class ImageUtils
{
    private ImageUtils() {}

    @Nonnull
    public static Image loadImage(@Nonnull String filename, @Nonnull ClientType clientType)
    {
        var fullFilename = "/png/" + filename + ".png";
        var url = ImageUtils.class.getResource(fullFilename);
        checkState(url != null, "Could not find image " + fullFilename);
        return switch (clientType)
        {
            case SWING -> SwingImage.fromFile(url);
            case CANVAS -> throw new UnsupportedOperationException();
        };
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
