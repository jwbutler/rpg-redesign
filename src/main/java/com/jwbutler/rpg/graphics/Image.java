package com.jwbutler.rpg.graphics;

import java.awt.image.BufferedImage;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;

public interface Image
{
    @Nonnull
    BufferedImage asBufferedImage();

    int getWidth();
    int getHeight();

    @Nonnull
    Color getColor(@Nonnull Pixel pixel);
    void setColor(@Nonnull Pixel pixel, @Nonnull Color color);
}
