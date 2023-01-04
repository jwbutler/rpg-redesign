package com.jwbutler.rpg.units.commands;

import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pathfinder;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.geometry.GeometryUtils.isDirectlyAdjacent;

public record AttackCommand
(
    @Nonnull Unit target
)
implements Command
{
    @Override
    @Nonnull
    public ActivityPair getNextActivity(@Nonnull Unit unit)
    {
        if (isDirectlyAdjacent(unit.getCoordinates(), target.getCoordinates()))
        {
            return new ActivityPair(Activity.ATTACKING, Direction.between(unit.getCoordinates(), target.getCoordinates()));
        }
        else
        {
            var controller = unit.getController();
            var level = controller.getState().getCurrentLevel();
            var candidates = level.getAllCoordinates()
                .stream()
                .filter(coordinates -> level.getUnit(coordinates) == null && !level.getTile(coordinates).isBlocking())
                .collect(Collectors.toSet());
            candidates.add(unit.getCoordinates());
            candidates.add(target.getCoordinates());
            @CheckForNull var path = Pathfinder.A_STAR.findPath(
                unit.getCoordinates(),
                target.getCoordinates(),
                candidates
            );
            if (path != null)
            {
                // first entry in path is equal to the current node, I think
                path.remove(0);
                Coordinates nextCoordinates = path.get(0);
                if (level.containsCoordinates(nextCoordinates) && level.getUnit(nextCoordinates) == null)
                {
                    var direction = Direction.between(unit.getCoordinates(), nextCoordinates);
                    return new ActivityPair(Activity.WALKING, direction);
                }
            }
            return new ActivityPair(Activity.STANDING, unit.getDirection());
        }
    }

    @Nonnull
    @Override
    public Unit getTargetUnit()
    {
        return target;
    }

    @CheckForNull
    @Override
    public Coordinates getTargetCoordinates()
    {
        return null;
    }
}
