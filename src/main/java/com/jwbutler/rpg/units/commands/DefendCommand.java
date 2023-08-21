package com.jwbutler.rpg.units.commands;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.RandomUtils.randomChoice;

public record DefendCommand() implements Command
{
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        Set<Unit> adjacentEnemies = Arrays.stream(Direction.values())
            .map(unit.getCoordinates()::plus)
            .map(unit.getLevel()::getUnit)
            .filter(Objects::nonNull)
            .filter(u -> unit.getPlayer().getFaction().isHostile(u.getPlayer().getFaction()))
            .collect(Collectors.toSet());

        if (!adjacentEnemies.isEmpty())
        {
            var enemy = randomChoice(adjacentEnemies);
            var direction = Direction.between(unit.getCoordinates(), enemy.getCoordinates());
            return new ActivityPair(Activity.ATTACKING, direction);
        }
        return new ActivityPair(Activity.STANDING, unit.getDirection());
    }

    @Nullable
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @Nullable
    @Override
    public Coordinates getTargetCoordinates()
    {
        return null;
    }
}
