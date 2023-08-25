package com.jwbutler.rpg.levels;

import java.awt.Color;

import com.jwbutler.rpg.geometry.Offsets;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.ImageCache;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.animations.Frame;

public enum TileType
{
    GRASS("grass_48x24"),
    DIRT("grass_48x24"),
    WALL("grass_48x24");

    @NonNull
    private final Frame frame;

    TileType(@NonNull String filename)
    {
        var image = new ImageBuilder()
            .filename("tiles/" + filename)
            .transparentColor(Color.WHITE)
            .cache(ImageCache.INSTANCE)
            .build();
        frame = new Frame(image, filename, Layer.TILE, Offsets.zero());
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
