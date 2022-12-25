package com.jwbutler.rpg.units.commands;

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
    public void startNextActivity(@Nonnull Unit unit)
    {
        unit.startActivity(Activity.FALLING, unit.getDirection());
    }

    @Override
    public void endActivity(@Nonnull Unit unit)
    {
        switch (unit.getActivity())
        {
            case FALLING -> { System.out.println("Done falling!"); }
        }
    }
}
