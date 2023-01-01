package com.jwbutler.rpg.util;

import java.util.Collection;
import java.util.Random;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;

public final class RandomUtils
{
    private static final Random RNG = new Random();

    private RandomUtils() {}

    @Nonnull
    public static <T> T randomChoice(@Nonnull Collection<T> items)
    {
        checkState(!items.isEmpty());
        int i = RNG.nextInt(items.size());
        // noinspection unchecked
        return ((T[]) items.toArray())[i];
    }
}
