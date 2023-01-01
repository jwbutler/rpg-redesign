package com.jwbutler.rpg.units.commands;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record DieCommand
(
    @Nonnull GameController controller
)
implements Command
{
    @Override
    @Nonnull
    public ActivityPair getNextActivity(@Nonnull Unit unit)
    {
        return new ActivityPair(Activity.FALLING, unit.getDirection());
    }

    @Override
    public void endActivity(@Nonnull Unit unit)
    {
        switch (unit.getActivity())
        {
            case FALLING -> controller.removeUnit(unit);
        }
    }

    @CheckForNull
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }
}
