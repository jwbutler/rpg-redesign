package com.jwbutler.rpg.sprites.animations;

import java.awt.image.BufferedImage;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.graphics.Layer;

public record Frame
(
    @Nonnull BufferedImage image,
    @Nonnull String filename,
    @Nonnull Layer layer
)
{
}
