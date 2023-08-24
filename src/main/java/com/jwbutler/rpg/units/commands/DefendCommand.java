package com.jwbutler.rpg.units.commands;

import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.units.UnitUtils.getAdjacentUnits;
import static com.jwbutler.rpg.units.UnitUtils.isHostileToward;
import static com.jwbutler.rpg.util.RandomUtils.randomChoice;

public record DefendCommand() implements Command
{
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        var adjacentEnemies = getAdjacentUnits(unit).stream()
            .filter(adjacentUnit -> isHostileToward(unit, adjacentUnit))
            .collect(Collectors.toSet());

        if (!adjacentEnemies.isEmpty())
        {
            var enemy = randomChoice(adjacentEnemies);
            var direction = Direction.between(unit.getCoordinates(), enemy.getCoordinates());
            return new ActivityPair(Activity.ATTACKING, direction);
        }
        return new ActivityPair(Activity.STANDING, unit.getDirection());
    }

    @Override
    public boolean isComplete(@NonNull Unit unit)
    {
        return true;
    }
}
