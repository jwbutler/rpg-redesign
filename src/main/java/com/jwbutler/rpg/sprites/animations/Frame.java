package com.jwbutler.rpg.sprites.animations;

import java.awt.image.BufferedImage;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.graphics.Layer;

public record Frame
(
    @NonNull BufferedImage image,
    @NonNull String filename,
    @NonNull Layer layer
)
{
}
