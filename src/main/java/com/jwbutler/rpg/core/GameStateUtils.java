package com.jwbutler.rpg.core;

import java.util.Set;
import java.util.stream.Collectors;

import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.units.commands.Command.defaultCommand;

/**
 * Awkwardly separating out some functionality from {@link GameState}
 * mostly because I don't want to get too attached to this API
 */
public final class GameStateUtils
{
    private GameStateUtils() {}

    @NonNull
    public static HumanPlayer getHumanPlayer(@NonNull GameState state)
    {
        return state.getPlayers()
            .stream()
            .filter(player -> player.getFaction() == Faction.PLAYER)
            .filter(HumanPlayer.class::isInstance)
            .map(HumanPlayer.class::cast)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }
    /**
     * @throws IllegalArgumentException if the unit's coordinates are out of bounds
     */
    public static void addUnit(@NonNull Unit unit, @NonNull GameState state)
    {
        state.addUnit(unit);
        unit.getLevel().addUnit(unit);
        unit.getPlayer().addUnit(unit);
    }

    /**
     * @throws IllegalArgumentException if the unit is not present in the game state
     */
    public static void removeUnit(@NonNull Unit unit, @NonNull GameState state)
    {
        state.removeUnit(unit);
        unit.getLevel().removeUnit(unit);
        unit.getPlayer().removeUnit(unit);

        // TODO I do not feel good about putting this here
        for (Unit other : unit.getLevel().getUnits())
        {
            if (other.getCommand().getTargetUnit() == unit)
            {
                other.setCommand(defaultCommand());
            }
        }
    }
}
