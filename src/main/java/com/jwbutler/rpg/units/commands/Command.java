package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand, DefendCommand
{
    @NonNull
    ActivityPair getNextActivity(@NonNull Unit unit);

    /**
     * @return the unit this command is targeting, if it's targeting a unit
     *         (e.g. it's an attack command).  This is intended as a UI convenience
     */
    @Nullable
    Unit getTargetUnit();
    /**
     * @return the coordinates this command is targeting, if it's targeting a tile
     *         (e.g. it's a move command).  This is intended as a UI convenience
     */
    @Nullable
    Coordinates getTargetCoordinates();

    @NonNull
    static Command defaultCommand()
    {
        return new DefendCommand();
    }
}
