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

import static com.jwbutler.rpg.geometry.GeometryUtils.isDirectlyAdjacent;

public final class AttackCommand
implements Command
{
    @NonNull
    private final Unit target;

    private boolean startedAttacking;
    private boolean finishedAttacking;

    public AttackCommand(@NonNull Unit target)
    {
        this.target = target;
        this.startedAttacking = false;
        this.finishedAttacking = false;
    }
    
    @NonNull
    public Unit target()
    {
        return target;
    }
    
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        if (!startedAttacking && isDirectlyAdjacent(unit.getCoordinates(), target.getCoordinates()))
        {
            startedAttacking = true;
            return new ActivityPair(Activity.ATTACKING, Direction.between(unit.getCoordinates(), target.getCoordinates()));
        }
        else if (startedAttacking)
        {
            finishedAttacking = true;
            return new ActivityPair(Activity.STANDING, unit.getDirection());
        }
        else
        {
            var level = unit.getLevel();
            var candidates = level.getAllCoordinates()
                .stream()
                .filter(coordinates -> level.getUnit(coordinates) == null && !level.getTile(coordinates).isBlocking())
                .collect(Collectors.toSet());
            candidates.add(unit.getCoordinates());
            candidates.add(target.getCoordinates());
            @Nullable List<Coordinates> path = Pathfinder.A_STAR.findPath(
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

    @Override
    public boolean isComplete(@NonNull Unit unit)
    {
        return finishedAttacking;
    }
}
