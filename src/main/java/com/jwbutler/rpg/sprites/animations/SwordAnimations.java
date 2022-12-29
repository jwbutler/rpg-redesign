package com.jwbutler.rpg.sprites.animations;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.SpriteUtils;
import com.jwbutler.rpg.units.Activity;

import static com.jwbutler.rpg.graphics.ImageUtils.applyPaletteSwaps;
import static com.jwbutler.rpg.graphics.ImageUtils.loadImage;
import static com.jwbutler.rpg.graphics.ImageUtils.setTransparentColor;

public record SwordAnimations
(
    @Nonnull Map<Color, Color> paletteSwaps
)
implements AnimationPack
{
    @Nonnull
    @Override
    public Animation getAnimation(@Nonnull Activity activity, @Nonnull Direction direction)
    {
        List<String> filenames = switch (activity)
        {
            case STANDING -> Stream.of("1")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
            case WALKING  -> Stream.of("2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
            case ATTACKING -> Stream.of("1", "2", "2", "1")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
            case FALLING -> Stream.of("1", "2", "3", "4")
                .map(number -> _formatFilename(activity, direction, number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
        };
        List<Frame> frames = filenames.stream()
            .map(filename ->
            {
                var image = loadImage(filename);
                setTransparentColor(image, Color.WHITE);
                applyPaletteSwaps(image, paletteSwaps);
                return new Frame(
                    image,
                    filename,
                    filename.endsWith(SpriteUtils.BEHIND_SUFFIX) ? Layer.EQUIPMENT_UNDER : Layer.EQUIPMENT_OVER
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
}
