package com.jwbutler.rpg.sprites.animations;

import java.util.List;
import javax.annotation.Nonnull;

public record Animation
(
    @Nonnull List<Frame> frames
)
{
}
