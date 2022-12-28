package com.jwbutler.rpg.equipment;

import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.Unit;

public interface Equipment
{
    @Nonnull
    UUID getId();
    @Nonnull
    String getName();
    @Nonnull
    Unit getUnit();
    @Nonnull
    Slot getSlot();
    @Nonnull
    Sprite<Equipment> getSprite();
}
