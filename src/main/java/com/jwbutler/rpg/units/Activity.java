package com.jwbutler.rpg.units;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.units.commands.DieCommand;

import static com.jwbutler.rpg.units.UnitUtils.dealDamage;
import static com.jwbutler.rpg.units.UnitUtils.moveUnit;
import static com.jwbutler.rpg.units.UnitUtils.removeUnit;

public enum Activity
{
    STANDING
    {
        @Override
        public void onComplete(@NonNull Unit unit)
        {
        }
    },
    WALKING
    {
        @Override
        public void onComplete(@NonNull Unit unit)
        {
            var coordinates = unit.getCoordinates().plus(unit.getDirection());
            var level = unit.getLevel();
            if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
            {
                moveUnit(unit, level, coordinates);
            }
        }
    },
    ATTACKING
    {
        @Override
        public void onComplete(@NonNull Unit unit)
        {
            var damage = unit.getAttackDamage();
            var coordinates = unit.getCoordinates().plus(unit.getDirection());
            var target = unit.getLevel().getUnit(coordinates);
            if (target != null)
            {
                dealDamage(unit, target, damage);
                if (target.getLife() <= 0)
                {
                    target.setCommand(new DieCommand());
                    unit.setCommand(null);
                }
            }
        }
    },
    FALLING
    {
        @Override
        public void onComplete(@NonNull Unit unit)
        {
            removeUnit(unit, unit.getGame());
        }
    };

    @NonNull
    @Override
    public final String toString()
    {
        return name().toLowerCase();
    }

    public abstract void onComplete(@NonNull Unit unit);
}
