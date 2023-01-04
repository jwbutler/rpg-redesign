package com.jwbutler.rpg.graphics;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;

public interface Image
{
    int getWidth();
    int getHeight();

    @Nonnull
    Color getColor(@Nonnull Pixel pixel);
    void setColor(@Nonnull Pixel pixel, @Nonnull Color color);
}
