package com.jwbutler.rpg.units.commands;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

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
        // TODO bad placeholder logic
        if (unit.getCoordinates().equals(target.getCoordinates()))
        {
            unit.startActivity(Activity.STANDING, unit.getDirection());
        }
        else
        {
            // TODO pathfinding
            unit.startActivity(Activity.WALKING, unit.getDirection());
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
