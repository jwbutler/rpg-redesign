package com.jwbutler.rpg.util;

import org.jspecify.annotations.NonNull;

/**
 * Based on the Guava class of the same name
 */
public final class Preconditions
{
    private Preconditions() {}
    
    public static void checkArgument(boolean condition)
    {
        if (!condition)
        {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean condition, @NonNull String message)
    {
        if (!condition)
        {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkState(boolean condition)
    {
        if (!condition)
        {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean condition, @NonNull String message)
    {
        if (!condition)
        {
            throw new IllegalStateException(message);
        }
    }
}
