package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record StayCommand() implements Command
{
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        return new ActivityPair(Activity.STANDING, unit.getDirection());
    }

    @Nullable
    @Override
    public Unit getTargetUnit()
    {
        return null;
    }

    @Nullable
    @Override
    public Coordinates getTargetCoordinates()
    {
        return null;
    }
}
