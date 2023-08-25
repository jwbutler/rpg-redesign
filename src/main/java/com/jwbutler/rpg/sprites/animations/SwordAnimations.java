package com.jwbutler.rpg.sprites.animations;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.jwbutler.rpg.geometry.Offsets;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.ImageCache;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.SpriteUtils;
import com.jwbutler.rpg.units.Activity;

public record SwordAnimations
(
    @NonNull Map<Color, Color> paletteSwaps
)
implements AnimationPack
{
    private static final Offsets OFFSETS = PlayerAnimations.OFFSETS;

    @NonNull
    @Override
    public Animation getAnimation(@NonNull Activity activity, @NonNull Direction direction)
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
                .map(number -> _formatFilename(activity, _getFallingDirection(direction), number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
        };
        List<Frame> frames = filenames.stream()
            .map(filename -> _buildFrame(filename, paletteSwaps))
            .toList();
        return new Animation(frames);
    }

    @NonNull
    private static Direction _getFallingDirection(@NonNull Direction direction)
    {
        return switch (direction)
        {
            case W, NW, N -> Direction.NW;
            case NE, E -> Direction.NE;
            case SE, S, SW -> Direction.S;
        };
    }

    @NonNull
    private static Frame _buildFrame(@NonNull String filename, @NonNull Map<Color, Color> paletteSwaps)
    {
        var image = new ImageBuilder()
            .filename(filename)
            .transparentColor(Color.WHITE)
            .paletteSwaps(paletteSwaps)
            .cache(ImageCache.INSTANCE)
            .build();
        return new Frame(
            image,
            filename,
            filename.endsWith(SpriteUtils.BEHIND_SUFFIX) ? Layer.EQUIPMENT_UNDER : Layer.EQUIPMENT_OVER,
            OFFSETS
        );
    }

    @NonNull
    private static String _formatFilename(@NonNull Activity activity, @NonNull Direction direction, @NonNull String number)
    {
        return String.format("equipment/sword/sword_%s_%s_%s", activity, direction, number);
    }
}
