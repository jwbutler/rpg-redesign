package com.jwbutler.rpg.units.commands;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand, DefendCommand
{
    @Nonnull
    ActivityPair getNextActivity(@Nonnull Unit unit);

    /**
     * @return the unit this command is targeting, if it's targeting a unit
     *         (e.g. it's an attack command).  This is intended as a UI convenience
     */
    @CheckForNull
    Unit getTargetUnit();
    /**
     * @return the coordinates this command is targeting, if it's targeting a tile
     *         (e.g. it's a move command).  This is intended as a UI convenience
     */
    @CheckForNull
    Coordinates getTargetCoordinates();

    @Nonnull
    static Command defaultCommand()
    {
        return new DefendCommand();
    }
}
