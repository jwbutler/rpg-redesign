package com.jwbutler.rpg.units;

import java.util.Map;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.UnitSprite;
import com.jwbutler.rpg.sprites.animations.PlayerAnimations;

import static java.util.Collections.emptyMap;

public final class UnitFactory
{
    private UnitFactory() {}

    @NonNull
    public static Unit createPlayerUnit(
        @NonNull GameController controller,
        @NonNull String name,
        int life,
        @NonNull Player player,
        @NonNull Level level,
        @NonNull Coordinates coordinates
    )
    {
        return new UnitBuilder()
            .controller(controller)
            .name(name)
            .life(life)
            .sprite(new UnitSprite(new PlayerAnimations(emptyMap())))
            .player(player)
            .level(level)
            .coordinates(coordinates)
            .build();
    }

    @NonNull
    public static Unit createEvilPlayerUnit(
        @NonNull GameController controller,
        @NonNull String name,
        int life,
        @NonNull Player player,
        @NonNull Level level,
        @NonNull Coordinates coordinates
    )
    {
        var paletteSwaps = Map.of(
            Colors.DARK_BLUE, Colors.DARK_RED,
            Colors.DARK_PURPLE, Colors.DARK_RED,
            Colors.PURPLE, Colors.DARK_RED
        );
        return new UnitBuilder()
            .controller(controller)
            .name(name)
            .life(life)
            .sprite(new UnitSprite(new PlayerAnimations(paletteSwaps)))
            .player(player)
            .level(level)
            .coordinates(coordinates)
            .build();
    }
}
