package com.jwbutler.rpg.units.commands;

import java.util.List;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pathfinder;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record MoveCommand
(
    @NonNull Coordinates target
)
implements Command
{
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        if (unit.getCoordinates().equals(target))
        {
            // TODO hack hack hack
            return new DefendCommand().getNextActivity(unit);
            // return new ActivityPair(Activity.STANDING, unit.getDirection());
        }
        else
        {
            var level = unit.getLevel();
            var candidates = level
                .getAllCoordinates()
                .stream()
                .filter(coordinates -> level.getUnit(coordinates) == null && !level.getTile(coordinates).isBlocking())
                .collect(Collectors.toSet());

            candidates.add(unit.getCoordinates());
            candidates.add(target);
            @Nullable List<Coordinates> path = Pathfinder.A_STAR.findPath(
                unit.getCoordinates(),
                target,
                candidates
            );
            if (path != null)
            {
                // first entry in path is equal to the current node, I think
                path.remove(0);
                var nextCoordinates = path.get(0);
                if (level.containsCoordinates(nextCoordinates) && level.getUnit(nextCoordinates) == null)
                {
                    var direction = Direction.between(unit.getCoordinates(), nextCoordinates);
                    return new ActivityPair(Activity.WALKING, direction);
                }
            }
        }

        // TODO hack hack hack
        return new DefendCommand().getNextActivity(unit);
    }

    @Nullable
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @NonNull
    @Override
    public Coordinates getTargetCoordinates()
    {
        return target;
    }
}
