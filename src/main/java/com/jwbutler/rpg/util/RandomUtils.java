package com.jwbutler.rpg.util;

import java.util.Collection;
import java.util.Random;

import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.util.Preconditions.checkState;

public final class RandomUtils
{
    private static final Random RNG = new Random();

    private RandomUtils() {}

    @NonNull
    public static <T> T randomChoice(@NonNull Collection<T> items)
    {
        checkState(!items.isEmpty());
        int i = RNG.nextInt(items.size());
        // noinspection unchecked
        return ((T[]) items.toArray())[i];
    }
}
