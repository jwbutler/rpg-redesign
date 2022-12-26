package com.jwbutler.rpg.sprites;

import java.awt.image.BufferedImage;
import javax.annotation.Nonnull;

public interface Sprite<T>
{
    @Nonnull
    BufferedImage getImage(@Nonnull T target);
}
