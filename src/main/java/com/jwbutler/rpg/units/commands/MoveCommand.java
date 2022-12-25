package com.jwbutler.rpg.units.commands;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
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
            // TODO pathfinding
            var direction = Direction.nearestBetween(unit.getCoordinates(), target);
            unit.startActivity(Activity.WALKING, direction);
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
