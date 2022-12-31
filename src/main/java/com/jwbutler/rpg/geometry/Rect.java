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
    @Nonnull
    public static Rect between(@Nonnull Point start, @Nonnull Point end)
    {
        return new Rect(
            Math.min(start.x(), end.x()),
            Math.min(start.y(), end.y()),
            Math.abs(end.x() - start.x() + 1),
            Math.abs(end.y() - start.y() + 1)
        );
    }

    public boolean overlaps(@Nonnull Rect other)
    {
        return
            (left() <= other.right())
            && (right() >= other.left())
            && (top() <= other.bottom())
            && (bottom() >= other.top());
    }

    @Nonnull
    public Rect getIntersection(@Nonnull Rect other)
    {
        var left = Math.max(left(), other.left());
        var top = Math.max(top(), other.top());
        var right = Math.min(right(), other.right());
        var bottom = Math.min(bottom(), other.bottom());
        var width = Math.max(right - left, 0);
        var height = Math.max(bottom - top, 0);
        return new Rect(left, top, width, height);
    }

    public int right()
    {
        return left + width;
    }

    public int bottom()
    {
        return top + height;
    }

    public int area()
    {
        return width() * height();
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
