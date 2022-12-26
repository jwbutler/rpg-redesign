package com.jwbutler.rpg.units.commands;

import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.AStarPathfinder;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record MoveCommand
(
    @Nonnull GameController controller,
    @Nonnull Coordinates target
)
implements Command
{
    @Override
    public void startNextActivity(@Nonnull Unit unit)
    {
        if (unit.getCoordinates().equals(target))
        {
            unit.startActivity(Activity.STANDING, unit.getDirection());
        }
        else
        {
            var level = controller.getState().getCurrentLevel();
            var unblockedCoordinates = level
                .getAllCoordinates()
                .stream()
                .filter(coordinates -> level.getUnit(coordinates) == null && !level.getTile(coordinates).isBlocking())
                .collect(Collectors.toSet());
            @CheckForNull var path = new AStarPathfinder().findPath(
                unit.getCoordinates(),
                target(),
                unblockedCoordinates
            );
            if (path != null)
            {
                // first entry in path is equal to the current node, I think
                path.remove(0);
                var direction = Direction.between(unit.getCoordinates(), path.get(0));
                unit.startActivity(Activity.WALKING, direction);
            }
        }
    }

    @Override
    public void endActivity(@Nonnull Unit unit)
    {
        switch (unit.getActivity())
        {
            case WALKING ->
            {
                var coordinates = unit.getCoordinates().plus(unit.getDirection());
                // TODO check if it's legal and unblocked
                controller.moveUnit(unit, unit.getLevel(), coordinates);
            }
        }
    }
}
