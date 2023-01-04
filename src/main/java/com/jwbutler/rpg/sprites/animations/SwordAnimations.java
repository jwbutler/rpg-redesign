package com.jwbutler.rpg.sprites.animations;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.graphics.Color;
import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.ImageCache;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.sprites.SpriteUtils;
import com.jwbutler.rpg.ui.ClientType;
import com.jwbutler.rpg.units.Activity;

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
                .map(number -> _formatFilename(activity, _getFallingDirection(direction), number))
                .map(SpriteUtils::substituteBehindFilename)
                .toList();
        };
        List<Frame> frames = filenames.stream()
            .map(filename -> _buildFrame(filename, paletteSwaps))
            .toList();
        return new Animation(frames);
    }

    @Nonnull
    private static Direction _getFallingDirection(@Nonnull Direction direction)
    {
        return switch (direction)
        {
            case W, NW, N -> Direction.NW;
            case NE, E -> Direction.NE;
            case SE, S, SW -> Direction.S;
        };
    }

    @Nonnull
    private static Frame _buildFrame(@Nonnull String filename, @Nonnull Map<Color, Color> paletteSwaps)
    {
        var image = new ImageBuilder()
            .filename(filename)
            .transparentColor(Colors.WHITE)
            .paletteSwaps(paletteSwaps)
            .cache(ImageCache.INSTANCE)
            .clientType(ClientType.getDefault())
            .build();
        return new Frame(
            image,
            filename,
            filename.endsWith(SpriteUtils.BEHIND_SUFFIX) ? Layer.EQUIPMENT_UNDER : Layer.EQUIPMENT_OVER
        );
    }

    @Nonnull
    private static String _formatFilename(@Nonnull Activity activity, @Nonnull Direction direction, @Nonnull String number)
    {
        return String.format("equipment/sword/sword_%s_%s_%s", activity, direction, number);
    }
}
