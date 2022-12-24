package com.jwbutler.rpg.sprites.animations;

import java.util.List;
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
        return switch (activity)
        {
            case STANDING -> Animation.of(_formatFilename(activity, direction, "1"));
            case WALKING  -> Animation.of(
                Stream.of("1", "2").map(number -> _formatFilename(activity, direction, number)).toList()
            );
            case ATTACKING  -> Animation.of(
                Stream.of("1", "2", "2", "1").map(number -> _formatFilename(activity, direction, number)).toList()
            );
            case FALLING  -> Animation.of(
                Stream.of("1", "2", "3", "4").map(number -> _formatFilename(activity, direction, number)).toList()
            );
        };
    }

    @Nonnull
    private static String _formatFilename(@Nonnull Activity activity, @Nonnull Direction direction, @Nonnull String number)
    {
        return String.format("player_%s_%s_%s", activity, direction, number);
    }
}
