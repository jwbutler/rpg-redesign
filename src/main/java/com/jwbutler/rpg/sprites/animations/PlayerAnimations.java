package com.jwbutler.rpg.sprites.animations;

import java.awt.Color;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.units.Activity;

import static com.jwbutler.rpg.graphics.ImageUtils.loadImage;
import static com.jwbutler.rpg.graphics.ImageUtils.setTransparentColor;

public final class PlayerAnimations implements AnimationPack
{
    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Activity activity, @Nonnull Direction direction)
    {
        List<String> filenames = switch (activity)
        {
            case STANDING -> List.of(_formatFilename(activity, direction, "1"));
            case WALKING  -> Stream.of("2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .toList();
            case ATTACKING -> Stream.of("1", "2", "2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .toList();
            case FALLING -> Stream.of("1", "2", "3", "4")
                .map(number -> _formatFilename(activity, direction, number))
                .toList();
        };

        List<Frame> frames = filenames.stream()
            .map(filename ->
            {
                var image = loadImage(filename);
                setTransparentColor(image, Color.WHITE);
                return new Frame(image, filename, Layer.UNIT);
            })
            .toList();
        return new Animation(frames);
    }

    @Nonnull
    private static String _formatFilename(@Nonnull Activity activity, @Nonnull Direction direction, @Nonnull String number)
    {
        return String.format("units/player/player_%s_%s_%s", activity, direction, number);
    }
}
