package com.jwbutler.rpg.equipment;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.Unit;

public interface Equipment
{
    @NonNull
    UUID getId();
    @NonNull
    String getName();
    @NonNull
    Unit getUnit();
    @NonNull
    Slot getSlot();
    @NonNull
    Sprite<Equipment> getSprite();
    int getDamage();
}
