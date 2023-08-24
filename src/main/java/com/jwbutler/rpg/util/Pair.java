package com.jwbutler.rpg.util;

import org.jspecify.annotations.NonNull;

public record Pair<A, B>
(
    @NonNull A first,
    @NonNull B second
)
{
    @NonNull
    public static <A, B> Pair<A, B> of(@NonNull A first, @NonNull B second)
    {
        return new Pair<>(first, second);
    }
}
