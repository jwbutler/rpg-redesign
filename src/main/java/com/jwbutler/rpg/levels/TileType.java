package com.jwbutler.rpg.levels;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.ImageCache;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.animations.Frame;
import com.jwbutler.rpg.ui.ClientType;

public enum TileType
{
    GRASS("grass_24x12_1"),
    DIRT("dirt_24x12_1"),
    WALL("floor_24x12_stone_1");

    @Nonnull
    private final Frame frame;

    TileType(@Nonnull String filename)
    {
        var image = new ImageBuilder()
            .filename("tiles/" + filename)
            .transparentColor(Colors.WHITE)
            .cache(ImageCache.INSTANCE)
            .clientType(ClientType.getDefault())
            .build();
        frame = new Frame(image, filename, Layer.TILE);
    }

    @Nonnull
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
