package com.jwbutler.rpg.equipment;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkState;

public final class EquipmentBuilder
{
    private String name;
    private Unit unit;
    private Slot slot;
    private AnimatedSprite<Equipment> sprite;

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

    @Nonnull
    public Equipment build()
    {
        checkState(name != null);
        checkState(unit != null);
        checkState(slot != null);
        checkState(sprite != null);

        return new EquipmentImpl(
            name,
            unit,
            slot,
            sprite
        );
    }
}
