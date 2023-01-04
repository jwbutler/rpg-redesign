package com.jwbutler.rpg.units.commands;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record DieCommand() implements Command
{
    @Override
    @Nonnull
    public ActivityPair getNextActivity(@Nonnull Unit unit)
    {
        return new ActivityPair(Activity.FALLING, unit.getDirection());
    }

    @CheckForNull
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @CheckForNull
    @Override
    public Coordinates getTargetCoordinates()
    {
        return null;
    }
}
