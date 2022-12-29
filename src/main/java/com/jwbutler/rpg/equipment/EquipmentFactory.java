package com.jwbutler.rpg.equipment;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.sprites.EquipmentSprite;
import com.jwbutler.rpg.sprites.animations.SwordAnimations;
import com.jwbutler.rpg.units.Unit;

import static java.util.Collections.emptyMap;

public class EquipmentFactory
{
    @Nonnull
    public static Equipment createNoobSword(
        @Nonnull GameController controller,
        @Nonnull Unit unit
    )
    {
        return new EquipmentBuilder()
            .unit(unit)
            .name("Noob Sword")
            .slot(Slot.MAIN_HAND)
            .sprite(new EquipmentSprite(new SwordAnimations(), emptyMap()))
            .build();
    }
}
