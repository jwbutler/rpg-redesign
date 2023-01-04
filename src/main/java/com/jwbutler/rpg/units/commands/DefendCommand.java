package com.jwbutler.rpg.units.commands;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.RandomUtils.randomChoice;

public record DefendCommand() implements Command
{
    @Override
    @Nonnull
    public ActivityPair getNextActivity(@Nonnull Unit unit)
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

    @CheckForNull
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @CheckForNull
    @Override
    public Coordinates getTargetCoordinates()
    {
        return null;
    }
}
