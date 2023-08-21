package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import javax.imageio.ImageIO;

import static com.jwbutler.rpg.util.Preconditions.checkState;

public final class ImageUtils
{
    private ImageUtils() {}

    @NonNull
    public static BufferedImage loadImage(@NonNull String filename)
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

    public static boolean imageFileExists(@NonNull String filename)
    {
        var fullFilename = "/png/" + filename + ".png";
        var url = ImageUtils.class.getResource(fullFilename);
        return url != null;
    }

    public static void setTransparentColor(@NonNull BufferedImage image, @NonNull Color transparentColor)
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
    public static void applyPaletteSwaps(@NonNull BufferedImage image, @NonNull Map<Color, Color> paletteSwaps)
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
