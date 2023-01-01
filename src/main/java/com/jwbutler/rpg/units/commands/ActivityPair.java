package com.jwbutler.rpg.units.commands;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;

public record ActivityPair
(
    @Nonnull Activity activity,
    @Nonnull Direction direction
) {}
