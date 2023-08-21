package com.jwbutler.rpg.levels;

import java.awt.Color;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.ImageCache;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.animations.Frame;

public enum TileType
{
    GRASS("grass_24x12_1"),
    DIRT("dirt_24x12_1"),
    WALL("floor_24x12_stone_1");

    @NonNull
    private final Frame frame;

    TileType(@NonNull String filename)
    {
        var image = new ImageBuilder()
            .filename("tiles/" + filename)
            .transparentColor(Color.WHITE)
            .cache(ImageCache.INSTANCE)
            .build();
        frame = new Frame(image, filename, Layer.TILE);
    }

    @NonNull
    public final Frame getFrame()
    {
        return frame;
    }

    public final boolean isBlocking()
    {
        return switch (this)
        {
            case WALL -> true;
            default   -> false;
        };
    }
}
