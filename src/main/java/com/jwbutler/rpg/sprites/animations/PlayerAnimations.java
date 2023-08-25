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
import com.jwbutler.rpg.units.Activity;

public record PlayerAnimations
(
    @NonNull Map<Color, Color> paletteSwaps
)
implements AnimationPack
{
    public static final Offsets OFFSETS = new Offsets(4, -24);

    @NonNull
    @Override
    public Animation getAnimation(@NonNull Activity activity, @NonNull Direction direction)
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
                .map(number ->
                {
                    var frameDirection = switch (direction) {
                        case NW, N, NE, E -> Direction.NE;
                        case SE, S, SW, W -> Direction.S;
                    };
                    return _formatFilename(activity, frameDirection, number);
                })
                .toList();
        };

        List<Frame> frames = filenames.stream()
            .map(filename -> _buildFrame(filename, paletteSwaps))
            .toList();
        return new Animation(frames);
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
        return new Frame(image, filename, Layer.UNIT, OFFSETS);
    }

    @NonNull
    private static String _formatFilename(@NonNull Activity activity, @NonNull Direction direction, @NonNull String number)
    {
        return String.format("units/player/player_%s_%s_%s", activity, direction, number);
    }
}
