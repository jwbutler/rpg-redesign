package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;
import static com.jwbutler.rpg.graphics.ImageUtils.applyPaletteSwaps;
import static com.jwbutler.rpg.graphics.ImageUtils.setTransparentColor;

public final class ImageBuilder
{
    private String filename;
    private Color transparentColor;
    private Map<Color, Color> paletteSwaps;

    public ImageBuilder filename(String filename)
    {
        this.filename = filename;
        return this;
    }

    public ImageBuilder transparentColor(Color transparentColor)
    {
        this.transparentColor = transparentColor;
        return this;
    }

    public ImageBuilder paletteSwaps(Map<Color, Color> paletteSwaps)
    {
        this.paletteSwaps = paletteSwaps;
        return this;
    }

    @Nonnull
    public BufferedImage build()
    {
        checkState(filename != null);
        var image = ImageUtils.loadImage(filename);

        if (transparentColor != null)
        {
            setTransparentColor(image, transparentColor);
        }

        if (paletteSwaps != null)
        {
            applyPaletteSwaps(image, paletteSwaps);
        }
        return image;
    }
}
