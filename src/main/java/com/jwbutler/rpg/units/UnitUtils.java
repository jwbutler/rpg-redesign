package com.jwbutler.rpg.units;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.commands.DieCommand;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.units.commands.Command.defaultCommand;

public final class UnitUtils
{
    private UnitUtils() {}

    /**
     * @throws IllegalArgumentException if the unit's coordinates are out of bounds
     */
    public static void addUnit(@NonNull Unit unit, @NonNull Game game)
    {
        game.addUnit(unit);
        unit.getLevel().addUnit(unit);
        unit.getPlayer().addUnit(unit);
    }

    /**
     * @throws IllegalArgumentException if the unit is not present in the game state
     */
    public static void removeUnit(@NonNull Unit unit, @NonNull Game game)
    {
        game.removeUnit(unit);
        unit.getLevel().removeUnit(unit);
        unit.getPlayer().removeUnit(unit);

        // TODO I do not feel good about putting this here
        for (Unit other : unit.getLevel().getUnits())
        {
            if (other.getCommand().getTargetUnit() == unit)
            {
                other.setCommand(defaultCommand());
            }
        }
    }

    public static void moveUnit(@NonNull Unit unit, @NonNull Level level, @NonNull Coordinates coordinates)
    {
        unit.getLevel().removeUnit(unit);
        unit.setLevel(level);
        unit.setCoordinates(coordinates);
        level.addUnit(unit);
    }

    public static void dealDamage(@NonNull Unit source, @NonNull Unit target, int amount)
    {
        target.takeDamage(amount);
        if (target.getLife() <= 0)
        {
            target.setCommand(new DieCommand());
        }
    }
    
    @NonNull
    public static Set<Unit> getAdjacentUnits(@NonNull Unit unit)
    {
        return Arrays.stream(Direction.values())
            .map(unit.getCoordinates()::plus)
            .map(unit.getLevel()::getUnit)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }
    
    public static boolean isHostileToward(@NonNull Unit source, @NonNull Unit target)
    {
        return source.getPlayer().getFaction().isHostileToward(target.getPlayer().getFaction());
    }
}
