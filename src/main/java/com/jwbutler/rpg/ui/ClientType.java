package com.jwbutler.rpg.ui;

import javax.annotation.Nonnull;

public enum ClientType
{
    SWING,
    CANVAS;

    @Nonnull
    public static ClientType getDefault()
    {
        return SWING;
    }
}
