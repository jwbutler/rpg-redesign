package com.jwbutler.rpg.sprites.animations;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.units.Activity;

import static com.jwbutler.rpg.graphics.ImageUtils.imageFileExists;
import static com.jwbutler.rpg.graphics.ImageUtils.loadImage;
import static com.jwbutler.rpg.graphics.ImageUtils.setTransparentColor;

public final class SwordAnimations implements AnimationPack
{
    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Activity activity, @Nonnull Direction direction)
    {
        List<String> filenames = switch (activity)
        {
            case STANDING -> Stream.of("1")
                .map(number -> _formatFilename(activity, direction, "1"))
                .map(SwordAnimations::_substituteBehindFilename)
                .toList();
            case WALKING  -> Stream.of("1", "2")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SwordAnimations::_substituteBehindFilename)
                .toList();
            case ATTACKING -> Stream.of("1", "2", "2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SwordAnimations::_substituteBehindFilename)
                .toList();
            case FALLING -> Stream.of("1", "2", "3", "4")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SwordAnimations::_substituteBehindFilename)
                .toList();
        };
        List<Frame> frames = filenames.stream()
            .map(filename ->
            {
                var image = loadImage(filename);
                setTransparentColor(image, Color.WHITE);
                return new Frame(
                    image,
                    filename,
                    filename.endsWith("_B") ? Layer.EQUIPMENT_UNDER : Layer.EQUIPMENT_OVER
                );
            })
            .toList();
        return new Animation(frames);
    }

    @Nonnull
    private static String _formatFilename(@Nonnull Activity activity, @Nonnull Direction direction, @Nonnull String number)
    {
        return String.format("equipment/sword/sword_%s_%s_%s", activity, direction, number);
    }

    @Nonnull
    private static String _substituteBehindFilename(@Nonnull String filename)
    {
        String behindFilename = filename + "_B";
        if (imageFileExists(behindFilename))
        {
            return behindFilename;
        }
        return filename;
    }
}
