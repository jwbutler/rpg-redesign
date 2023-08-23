package com.jwbutler.rpg.units.commands;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand, DefendCommand
{
    @NonNull
    ActivityPair getNextActivity(@NonNull Unit unit);
}
