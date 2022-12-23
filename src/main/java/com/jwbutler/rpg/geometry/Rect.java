package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

public record Rect
(
    int left,
    int top,
    int width,
    int height
)
{
    public int right()
    {
        return left + width;
    }

    public int bottom()
    {
        return top + height;
    }

    @Nonnull
    public Dimensions getDimensions()
    {
        return new Dimensions(width, height);
    }

    public boolean contains(@Nonnull Point point)
    {
        return point.x() >= left()
            && point.x() < right()
            && point.y() >= top()
            && point.y() < bottom();
    }
}
