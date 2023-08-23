package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.NonNull;

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
}
