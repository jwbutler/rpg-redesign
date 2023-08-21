package com.jwbutler.rpg.sprites.animations;

import java.util.List;
import org.jspecify.annotations.NonNull;

public record Animation
(
    @NonNull List<Frame> frames
)
{
}
