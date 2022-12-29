package com.jwbutler.rpg.equipment;

import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.Unit;

final class EquipmentImpl implements Equipment
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Unit unit;
    @Nonnull
    private final Slot slot;
    @Nonnull
    private final AnimatedSprite<Equipment> sprite;

    EquipmentImpl(
        @Nonnull String name,
        @Nonnull Unit unit,
        @Nonnull Slot slot,
        @Nonnull AnimatedSprite<Equipment> sprite
    )
    {
        id = UUID.randomUUID();
        this.name = name;
        this.unit = unit;
        this.slot = slot;
        this.sprite = sprite;
    }

    @Nonnull
    @Override
    public UUID getId()
    {
        return id;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    @Nonnull
    @Override
    public Unit getUnit()
    {
        return unit;
    }

    @Nonnull
    @Override
    public Slot getSlot()
    {
        return slot;
    }

    @Nonnull
    @Override
    public Sprite<Equipment> getSprite()
    {
        return sprite;
    }
}
