package com.jwbutler.rpg.units.commands;

import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pathfinder;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.geometry.GeometryUtils.isDirectlyAdjacent;

public record AttackCommand
(
    @Nonnull GameController controller,
    @Nonnull Unit target
)
implements Command
{
    @Override
    public void startNextActivity(@Nonnull Unit unit)
    {
        if (isDirectlyAdjacent(unit.getCoordinates(), target.getCoordinates()))
        {
            unit.startActivity(Activity.ATTACKING, Direction.between(unit.getCoordinates(), target.getCoordinates()));
        }
        else
        {
            var level = controller.getState().getCurrentLevel();
            var candidates = level
                .getAllCoordinates()
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
                    unit.startActivity(Activity.WALKING, direction);
                }
                else
                {
                    unit.startActivity(Activity.STANDING, unit.getDirection());
                }
            }
            else
            {
                // TODO - very easy to miss this "else" here.  Can we design commands a bit better?
                unit.startActivity(Activity.STANDING, unit.getDirection());
            }
        }
    }

    @Override
    public void endActivity(@Nonnull Unit unit)
    {
        switch (unit.getActivity())
        {
            case ATTACKING ->
            {
                var damage = unit.getAttackDamage();
                controller.dealDamage(unit, target, damage);
                if (target.getLife() <= 0)
                {
                    target.setCommand(new DieCommand(controller));
                    unit.setCommand(new StayCommand(controller));
                }
            }
            case WALKING ->
            {
                var coordinates = unit.getCoordinates().plus(unit.getDirection());
                var level = unit.getLevel();
                if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
                {
                    controller.moveUnit(unit, level, coordinates);
                }
            }
        }
    }

    @Nonnull
    @Override
    public Unit getTargetUnit()
    {
        return target;
    }
}
