package com.jwbutler.rpg.units;

import javax.annotation.Nonnull;

public enum Activity
{
    STANDING,
    WALKING,
    ATTACKING,
    FALLING;

    @Nonnull
    @Override
    public final String toString()
    {
        return name().toLowerCase();
    }
}
