package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public final class ImageCache
{
    public record CacheKey
    (
        @Nonnull String filename,
        @CheckForNull Color transparentColor,
        @Nonnull Map<Color, Color> paletteSwaps
    ) {}

    @Nonnull
    private final Map<CacheKey, BufferedImage> images;

    public ImageCache()
    {
        images = new HashMap<>();
    }

    /**
     * TODO uggghhh
     */
    public static final ImageCache INSTANCE = new ImageCache();

    @Nonnull
    public BufferedImage computeIfAbsent(@Nonnull CacheKey key, @Nonnull Supplier<BufferedImage> supplier)
    {
        if (images.get(key) == null)
        {
            System.out.println("Cache miss: " + key);
        }
        return images.computeIfAbsent(key, k -> supplier.get());
    }
}
