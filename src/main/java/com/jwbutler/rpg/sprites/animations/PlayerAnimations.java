package com.jwbutler.rpg.sprites.animations;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;

public final class PlayerAnimations implements UnitAnimations
{
    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Activity activity, @Nonnull Direction direction)
    {
        List<String> filenames = switch (activity)
        {
            case STANDING -> List.of(_formatFilename(activity, direction, "1"));
            case WALKING  -> Stream.of("1", "2")
                .map(number -> _formatFilename(activity, direction, number))
                .flatMap(_stretch(8))
                .toList();
            case ATTACKING -> Stream.of("1", "2", "2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .flatMap(_stretch(8))
                .toList();
            case FALLING -> Stream.of("1", "2", "3", "4")
                .map(number -> _formatFilename(activity, direction, number))
                .flatMap(_stretch(8))
                .toList();
        };
        return new Animation(filenames);
    }

    @Nonnull
    private static String _formatFilename(@Nonnull Activity activity, @Nonnull Direction direction, @Nonnull String number)
    {
        return String.format("units/player/player_%s_%s_%s", activity, direction, number);
    }

    @Nonnull
    private static <T> Function<T, Stream<T>> _stretch(int count)
    {
        return item -> IntStream.range(0, count).mapToObj(i -> item);
    }
}
