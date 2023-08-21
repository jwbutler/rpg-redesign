package com.jwbutler.rpg.equipment;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkState;

public final class EquipmentBuilder
{
    private String name;
    private Unit unit;
    private Slot slot;
    private AnimatedSprite<Equipment> sprite;
    private Integer damage;

    public EquipmentBuilder name(String name)
    {
        this.name = name;
        return this;
    }

    public EquipmentBuilder unit(Unit unit)
    {
        this.unit = unit;
        return this;
    }

    public EquipmentBuilder slot(Slot slot)
    {
        this.slot = slot;
        return this;
    }

    public EquipmentBuilder sprite(AnimatedSprite<Equipment> sprite)
    {
        this.sprite = sprite;
        return this;
    }

    public EquipmentBuilder damage(Integer damage)
    {
        this.damage = damage;
        return this;
    }

    @NonNull
    public Equipment build()
    {
        checkState(name != null);
        checkState(unit != null);
        checkState(slot != null);
        checkState(sprite != null);
        checkState(damage != null);

        return new EquipmentImpl(
            name,
            unit,
            slot,
            sprite,
            damage
        );
    }
}
