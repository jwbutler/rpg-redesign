package com.jwbutler.rpg.units.commands;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand
{
    @Nonnull
    ActivityPair getNextActivity(@Nonnull Unit unit);
    void endActivity(@Nonnull Unit unit);
    @CheckForNull Unit getTargetUnit();
}
