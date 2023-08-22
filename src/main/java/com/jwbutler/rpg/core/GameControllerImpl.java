package com.jwbutler.rpg.core;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.DieCommand;

final class GameControllerImpl implements GameController
{
    @NonNull
    private final GameState state;

    GameControllerImpl(@NonNull GameState state)
    {
        this.state = state;
    }

    @Override
    public void moveUnit(@NonNull Unit unit, @NonNull Level level, @NonNull Coordinates coordinates)
    {
        unit.getLevel().removeUnit(unit);
        unit.setLevel(level);
        unit.setCoordinates(coordinates);
        level.addUnit(unit);
    }

    @Override
    public void dealDamage(@NonNull Unit source, @NonNull Unit target, int amount)
    {
        target.takeDamage(amount);
        if (target.getLife() <= 0)
        {
            target.setCommand(new DieCommand());
        }
    }

    @NonNull
    @Override
    public GameState getState()
    {
        return state;
    }
}
