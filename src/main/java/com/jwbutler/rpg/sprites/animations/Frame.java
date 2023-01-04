package com.jwbutler.rpg.sprites.animations;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.graphics.Image;
import com.jwbutler.rpg.graphics.Layer;

public record Frame
(
    @Nonnull Image image,
    @Nonnull String filename,
    @Nonnull Layer layer
)
{
}
