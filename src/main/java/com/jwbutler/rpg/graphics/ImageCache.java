package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public final class ImageCache
{
    public record CacheKey
    (
        @NonNull String filename,
        @Nullable Color transparentColor,
        @NonNull Map<Color, Color> paletteSwaps
    ) {}

    @NonNull
    private final Map<CacheKey, BufferedImage> images;

    public ImageCache()
    {
        images = new HashMap<>();
    }

    /**
     * TODO uggghhh
     */
    public static final ImageCache INSTANCE = new ImageCache();

    @NonNull
    public BufferedImage computeIfAbsent(@NonNull CacheKey key, @NonNull Supplier<BufferedImage> supplier)
    {
        return images.computeIfAbsent(key, k -> supplier.get());
    }
}
