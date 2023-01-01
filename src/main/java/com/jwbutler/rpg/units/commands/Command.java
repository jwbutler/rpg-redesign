package com.jwbutler.rpg.units.commands;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand, DefendCommand
{
    @Nonnull
    ActivityPair getNextActivity(@Nonnull Unit unit);
    @CheckForNull Unit getTargetUnit();

    @Nonnull
    static Command defaultCommand()
    {
        return new DefendCommand();
    }
}
