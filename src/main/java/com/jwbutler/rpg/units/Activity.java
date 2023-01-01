package com.jwbutler.rpg.units;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.commands.DieCommand;

import static com.jwbutler.rpg.units.commands.Command.defaultCommand;

public enum Activity
{
    STANDING
    {
        @Override
        public void onComplete(@Nonnull Unit unit)
        {
        }
    },
    WALKING
    {
        @Override
        public void onComplete(@Nonnull Unit unit)
        {
            var coordinates = unit.getCoordinates().plus(unit.getDirection());
            var level = unit.getLevel();
            if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
            {
                unit.getController().moveUnit(unit, level, coordinates);
            }
        }
    },
    ATTACKING
    {
        @Override
        public void onComplete(@Nonnull Unit unit)
        {
            var damage = unit.getAttackDamage();
            var controller = unit.getController();
            var coordinates = unit.getCoordinates().plus(unit.getDirection());
            var target = unit.getLevel().getUnit(coordinates);
            if (target != null)
            {
                controller.dealDamage(unit, target, damage);
                if (target.getLife() <= 0)
                {
                    target.setCommand(new DieCommand());
                    unit.setCommand(defaultCommand());
                }
            }
        }
    },
    FALLING
    {
        @Override
        public void onComplete(@Nonnull Unit unit)
        {
            var controller = unit.getController();
            controller.removeUnit(unit);
        }
    };

    @Nonnull
    @Override
    public final String toString()
    {
        return name().toLowerCase();
    }

    public abstract void onComplete(@Nonnull Unit unit);
}
