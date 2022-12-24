package com.jwbutler.rpg.units.commands;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.Unit;

public sealed interface Command
permits StayCommand, MoveCommand, AttackCommand, DieCommand
{
    void startNextActivity(@Nonnull Unit unit);
    void endActivity(@Nonnull Unit unit);
}
