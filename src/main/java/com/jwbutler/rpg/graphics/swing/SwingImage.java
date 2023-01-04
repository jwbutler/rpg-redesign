package com.jwbutler.rpg.graphics.swing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.graphics.Color;
import com.jwbutler.rpg.graphics.Image;

public final class SwingImage implements Image
{
    @Nonnull
    private final BufferedImage delegate;

    public SwingImage(@Nonnull BufferedImage delegate)
    {
        this.delegate = delegate;
    }

    public static SwingImage fromFile(@Nonnull URL url)
    {
        try
        {
            var image = ImageIO.read(url);
            var argb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            argb.getGraphics().drawImage(image, 0, 0, null);
            return new SwingImage(argb);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    @Nonnull
    public BufferedImage getBufferedImage()
    {
        return delegate;
    }

    @Override
    public int getWidth()
    {
        return delegate.getWidth();
    }

    @Override
    public int getHeight()
    {
        return delegate.getHeight();
    }

    @Nonnull
    @Override
    public Color getColor(@Nonnull Pixel pixel)
    {
        var rgba = delegate.getRGB(pixel.x(), pixel.y());
        var color = new java.awt.Color(rgba);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void setColor(@Nonnull Pixel pixel, @Nonnull Color color)
    {
        delegate.setRGB(pixel.x(), pixel.y(), color.getSwingColor().getRGB());
    }
}
