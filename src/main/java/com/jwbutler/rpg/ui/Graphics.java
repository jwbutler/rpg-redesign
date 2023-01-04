package com.jwbutler.rpg.ui;

import java.awt.Graphics2D;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.graphics.Color;
import com.jwbutler.rpg.graphics.Image;

/**
 * Wrapper for Swing's {@link Graphics2D} and HTML Canvas API's Context2D
 */
public interface Graphics
{
    void drawImage(@Nonnull Image image, @Nonnull Pixel topLeft);
    void drawRect(@Nonnull Rect rect, @Nonnull Color color);
    void fillRect(@Nonnull Rect rect, @Nonnull Color color);
}
