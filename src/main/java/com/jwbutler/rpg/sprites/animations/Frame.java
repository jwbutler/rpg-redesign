package com.jwbutler.rpg.sprites.animations;

import java.awt.image.BufferedImage;

import com.jwbutler.rpg.geometry.Offsets;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.graphics.Layer;

/**
 * @param offsets offsets relative to the top-left corner of the current tile
 */
public record Frame
(
    @NonNull BufferedImage image,
    @NonNull String filename,
    @NonNull Layer layer,
    @NonNull Offsets offsets
)
{
}
