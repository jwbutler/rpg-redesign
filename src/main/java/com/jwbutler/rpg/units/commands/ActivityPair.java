package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;

public record ActivityPair
(
    @NonNull Activity activity,
    @NonNull Direction direction
) {}
