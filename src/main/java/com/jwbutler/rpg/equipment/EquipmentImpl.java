package com.jwbutler.rpg.equipment;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.Unit;

final class EquipmentImpl implements Equipment
{
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;
    @NonNull
    private final Unit unit;
    @NonNull
    private final Slot slot;
    @NonNull
    private final AnimatedSprite<Equipment> sprite;
    private final int damage;

    EquipmentImpl(
        @NonNull String name,
        @NonNull Unit unit,
        @NonNull Slot slot,
        @NonNull AnimatedSprite<Equipment> sprite,
        int damage
    )
    {
        id = UUID.randomUUID();
        this.name = name;
        this.unit = unit;
        this.slot = slot;
        this.sprite = sprite;
        this.damage = damage;
    }

    @NonNull
    @Override
    public UUID getId()
    {
        return id;
    }

    @NonNull
    @Override
    public String getName()
    {
        return name;
    }

    @NonNull
    @Override
    public Unit getUnit()
    {
        return unit;
    }

    @NonNull
    @Override
    public Slot getSlot()
    {
        return slot;
    }

    @NonNull
    @Override
    public Sprite<Equipment> getSprite()
    {
        return sprite;
    }

    @Override
    public int getDamage()
    {
        return damage;
    }
}
