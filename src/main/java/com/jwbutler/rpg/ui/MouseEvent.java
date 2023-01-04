package com.jwbutler.rpg.ui;

import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;

public record MouseEvent
(
    @CheckForNull Button button,
    @Nonnull Set<Button> heldButtons,
    @Nonnull Pixel pixel
    )
{
    public enum Button
    {
        LEFT,
        RIGHT,
        MIDDLE
    }
}
