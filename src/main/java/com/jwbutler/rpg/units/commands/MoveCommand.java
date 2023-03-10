package com.jwbutler.rpg.units.commands;

import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pathfinder;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record MoveCommand
(
    @Nonnull Coordinates target
)
implements Command
{
    @Override
    @Nonnull
    public ActivityPair getNextActivity(@Nonnull Unit unit)
    {
        if (unit.getCoordinates().equals(target))
        {
            // TODO hack hack hack
            return new DefendCommand().getNextActivity(unit);
            // return new ActivityPair(Activity.STANDING, unit.getDirection());
        }
        else
        {
            var level = unit.getController().getState().getCurrentLevel();
            var candidates = level
                .getAllCoordinates()
                .stream()
                .filter(coordinates -> level.getUnit(coordinates) == null && !level.getTile(coordinates).isBlocking())
                .collect(Collectors.toSet());

            candidates.add(unit.getCoordinates());
            candidates.add(target);
            @CheckForNull var path = Pathfinder.A_STAR.findPath(
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

    @CheckForNull
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @Nonnull
    @Override
    public Coordinates getTargetCoordinates()
    {
        return target;
    }
}
