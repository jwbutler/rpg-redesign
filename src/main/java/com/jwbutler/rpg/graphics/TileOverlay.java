package com.jwbutler.rpg.graphics;

import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.ui.ClientType;

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
    public Image getImage()
    {
        return new ImageBuilder()
            .filename("tiles/overlay_24x12")
            .transparentColor(Colors.WHITE)
            .paletteSwaps(Map.of(
                Colors.RED, outerColor,
                Colors.BLUE, innerColor
            ))
            .cache(ImageCache.INSTANCE)
            .clientType(ClientType.SWING)
            .build();
    }
}
