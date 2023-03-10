package com.jwbutler.rpg.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.annotation.Nonnull;

public enum TileOverlay
{
    PLAYER_ACTIVE(Colors.DARK_GREEN, Colors.CYAN),
    PLAYER_INACTIVE(Colors.DARK_GREEN, Colors.DARK_GREEN),
    ENEMY_TARGETED(Colors.DARK_RED, Colors.RED),
    ENEMY_MOUSEOVER(Colors.RED, Colors.YELLOW),
    ENEMY_INACTIVE(Colors.DARK_RED, Colors.DARK_RED),
    TILE_TARGETED(Colors.DARK_BLUE, Colors.BLUE),
    TILE_MOUSEOVER(Colors.DARK_BLUE, Colors.DARK_BLUE);

    @Nonnull
    private final Color outerColor;
    @Nonnull
    private final Color innerColor;

    TileOverlay(@Nonnull Color outerColor, @Nonnull Color innerColor)
    {
        this.outerColor = outerColor;
        this.innerColor = innerColor;
    }

    @Nonnull
    public BufferedImage getImage()
    {
        return new ImageBuilder()
            .filename("tiles/overlay_24x12")
            .transparentColor(Color.WHITE)
            .paletteSwaps(Map.of(
                Color.RED, outerColor,
                Color.BLUE, innerColor
            ))
            .cache(ImageCache.INSTANCE)
            .build();
    }
}
