package com.jwbutler.rpg.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * This is so stupid but I've always wanted to do this.
 */
public final class Symbols
{
    private Symbols () {}
    
    @NonNull
    public static <T> Optional<T> $(@Nullable T value)
    {
        return Optional.ofNullable(value);
    }
    
    @Nullable
    public static <T> T $$(@Nullable T... values)
    {
        return Arrays.stream(values)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
