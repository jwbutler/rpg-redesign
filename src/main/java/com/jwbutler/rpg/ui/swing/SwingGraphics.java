package com.jwbutler.rpg.ui.swing;

import java.awt.Graphics2D;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.graphics.Color;
import com.jwbutler.rpg.graphics.Image;
import com.jwbutler.rpg.graphics.swing.SwingImage;
import com.jwbutler.rpg.ui.Graphics;

import static com.google.common.base.Preconditions.checkState;

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
        checkState(image instanceof SwingImage);
        var bufferedImage = ((SwingImage) image).getBufferedImage();
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
