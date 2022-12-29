package com.jwbutler.rpg.units;

import java.util.Map;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.UnitSprite;
import com.jwbutler.rpg.sprites.animations.PlayerAnimations;

import static java.util.Collections.emptyMap;

public final class UnitFactory
{
    private UnitFactory() {}

    @Nonnull
    public static Unit createPlayerUnit(
        @Nonnull GameController controller,
        @Nonnull String name,
        int life,
        @Nonnull Player player,
        @Nonnull Level level,
        @Nonnull Coordinates coordinates
    )
    {
        return new UnitBuilder()
            .controller(controller)
            .name(name)
            .life(life)
            .sprite(new UnitSprite(new PlayerAnimations(), emptyMap()))
            .player(player)
            .level(level)
            .coordinates(coordinates)
            .build();
    }

    @Nonnull
    public static Unit createEvilPlayerUnit(
        @Nonnull GameController controller,
        @Nonnull String name,
        int life,
        @Nonnull Player player,
        @Nonnull Level level,
        @Nonnull Coordinates coordinates
    )
    {
        var paletteSwaps = Map.of(

        );
        return new UnitBuilder()
            .controller(controller)
            .name(name)
            .life(life)
            .sprite(new UnitSprite(new PlayerAnimations(), emptyMap()))
            .player(player)
            .level(level)
            .coordinates(coordinates)
            .build();
    }
}
