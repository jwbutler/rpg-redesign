package com.jwbutler.rpg.graphics;

import javax.annotation.Nonnull;

public record Color(
    int red,
    int green,
    int blue,
    int alpha
)
{
    public Color(int red, int green, int blue)
    {
        this(red, green, blue, 255);
    }

    @Nonnull
    public java.awt.Color getSwingColor()
    {
        return new java.awt.Color(red(), green(), blue(), alpha());
    }
}
