package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.Unit;

public record DieCommand() implements Command
{
    @Override
    @NonNull
    public ActivityPair getNextActivity(@NonNull Unit unit)
    {
        return new ActivityPair(Activity.FALLING, unit.getDirection());
    }
}
