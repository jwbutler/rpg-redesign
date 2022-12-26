package com.jwbutler.rpg.geometry;

import javax.annotation.Nonnull;

public final class GeometryUtils
{
    private GeometryUtils() {}

    public static <T extends Point> double hypotenuse(@Nonnull T first, @Nonnull T second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static <T extends Point> double manhattanDistance(@Nonnull T first, @Nonnull T second)
    {
        var dx = second.x() - first.x();
        var dy = second.y() - first.y();
        return Math.abs(dx) + Math.abs(dy);
    }
}
