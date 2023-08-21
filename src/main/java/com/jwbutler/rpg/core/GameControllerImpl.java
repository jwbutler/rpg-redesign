package com.jwbutler.rpg.core;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.DieCommand;

import static com.jwbutler.rpg.units.commands.Command.defaultCommand;

final class GameControllerImpl implements GameController
{
    @NonNull
    private final GameState state;

    GameControllerImpl(@NonNull GameState state)
    {
        this.state = state;
    }

    @Override
    public void addUnit(@NonNull Unit unit)
    {
        state.addUnit(unit);
        unit.getLevel().addUnit(unit);
        unit.getPlayer().addUnit(unit);
    }

    @Override
    public void removeUnit(@NonNull Unit unit)
    {
        state.removeUnit(unit);
        unit.getLevel().removeUnit(unit);
        unit.getPlayer().removeUnit(unit);

        for (Unit other : unit.getLevel().getUnits())
        {
            if (other.getCommand().getTargetUnit() == unit)
            {
                other.setCommand(defaultCommand());
            }
        }
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
    public void addLevel(@NonNull Level level)
    {
        state.addLevel(level);
    }

    @Override
    public void addPlayer(@NonNull Player player)
    {
        state.addPlayer(player);
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
