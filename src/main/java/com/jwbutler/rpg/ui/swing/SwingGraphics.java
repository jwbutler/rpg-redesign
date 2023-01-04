package com.jwbutler.rpg.ui.swing;

import java.awt.Graphics2D;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.graphics.Color;
import com.jwbutler.rpg.graphics.Image;
import com.jwbutler.rpg.ui.Graphics;

public final class SwingGraphics implements Graphics
{
    @Nonnull
    private final Graphics2D delegate;

    public SwingGraphics(@Nonnull Graphics2D delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public void drawImage(@Nonnull Image image, @Nonnull Pixel topLeft)
    {
        var bufferedImage = image.asBufferedImage();
        delegate.drawImage(bufferedImage, topLeft.x(), topLeft.y(), null);
    }

    @Override
    public void drawRect(@Nonnull Rect rect, @Nonnull Color color)
    {
        delegate.setColor(color.getSwingColor());
        delegate.drawRect(rect.left(), rect.top(), rect.width(), rect.height());
    }

    @Override
    public void fillRect(@Nonnull Rect rect, @Nonnull Color color)
    {
        delegate.setColor(color.getSwingColor());
        delegate.fillRect(rect.left(), rect.top(), rect.width(), rect.height());
    }
}
