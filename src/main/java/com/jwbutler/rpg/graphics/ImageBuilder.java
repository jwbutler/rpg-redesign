package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.util.Preconditions.checkState;
import static com.jwbutler.rpg.graphics.ImageUtils.applyPaletteSwaps;
import static com.jwbutler.rpg.graphics.ImageUtils.setTransparentColor;

public final class ImageBuilder
{
    private String filename;
    private Color transparentColor;
    private Map<Color, Color> paletteSwaps;
    private ImageCache cache;

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

    public ImageBuilder cache(ImageCache cache)
    {
        this.cache = cache;
        return this;
    }

    @NonNull
    public BufferedImage build()
    {
        checkState(filename != null);

        Supplier<BufferedImage> supplier = () ->
        {
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
        };

        if (cache != null)
        {
            var cacheKey = new ImageCache.CacheKey(filename, transparentColor, paletteSwaps);
            return cache.computeIfAbsent(cacheKey, supplier);
        }
        return supplier.get();
    }
}
