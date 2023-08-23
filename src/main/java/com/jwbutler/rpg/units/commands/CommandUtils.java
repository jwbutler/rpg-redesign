package com.jwbutler.rpg.units.commands;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class CommandUtils
{
    private CommandUtils() {}

    /**
     * @return the unit this command is targeting, if it's targeting a unit
     *         (e.g. it's an attack command).  This is intended as a UI convenience
     */
    @Nullable
    public static Unit getTargetUnit(@NonNull Command command)
    {
        return switch (command)
        {
            case AttackCommand attackCommand -> attackCommand.target();
            default -> null;
        };
    }

    /**
     * @return the coordinates this command is targeting, if it's targeting a tile
     *         (e.g. it's a move command).  This is intended as a UI convenience
     */
    @Nullable
    public static Coordinates getTargetCoordinates(@NonNull Command command)
    {
        return switch (command)
        {
            case MoveCommand moveCommand -> moveCommand.target();
            default -> null;
        };
    }

    @NonNull
    public static Command getDefaultCommand()
    {
        return new DefendCommand();
    }
}
