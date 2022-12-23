package com.jwbutler.rpg.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

final class GameStateImpl implements GameState
{
    @Nonnull
    private final Map<UUID, Level> levelsById;
    @Nonnull
    private final Map<UUID, Player> playersById;
    @Nonnull
    private final Map<UUID, Unit> unitsById;

    @CheckForNull
    private Level currentLevel;

    GameStateImpl()
    {
        playersById = new HashMap<>();
        levelsById = new HashMap<>();
        unitsById = new HashMap<>();
        currentLevel = null;
    }

    @Override
    public void addPlayer(@Nonnull Player player)
    {
        playersById.put(player.getId(), player);
    }

    /**
     * @throws IllegalArgumentException if there is no player with the specified id
     */
    @Nonnull
    @Override
    public Player getPlayer(@Nonnull UUID id)
    {
        var player = playersById.get(id);
        checkArgument(player != null);
        return player;
    }

    @Nonnull
    @Override
    public HumanPlayer getHumanPlayer()
    {
        return playersById.values()
            .stream()
            .filter(player -> player.getFaction() == Faction.PLAYER)
            .filter(HumanPlayer.class::isInstance)
            .map(HumanPlayer.class::cast)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void addLevel(@Nonnull Level level)
    {
        checkArgument(levelsById.get(level.getId()) == null);
        levelsById.put(level.getId(), level);
    }

    @Override
    @Nonnull
    public Level getLevel(@Nonnull UUID id)
    {
        var level = levelsById.get(id);
        checkArgument(level != null);
        return level;
    }

    @Nonnull
    @Override
    public Level getCurrentLevel()
    {
        checkState(currentLevel != null);
        return currentLevel;
    }

    @Override
    public void setCurrentLevel(@Nonnull Level currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        checkArgument(unitsById.get(unit.getId()) == null);
        unitsById.put(unit.getId(), unit);
    }

    @Override
    @Nonnull
    public Unit getUnit(@Nonnull UUID id)
    {
        var unit = unitsById.get(id);
        checkArgument(unit != null);
        return unit;
    }

    @Nonnull
    @Override
    public Unit getPlayerUnit()
    {
        return unitsById.values()
            .stream()
            .filter(unit -> unit.getPlayer().getFaction() == Faction.PLAYER)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    @Override
    @CheckForNull
    public Unit getUnitNullable(@Nonnull UUID id)
    {
        return unitsById.get(id);
    }

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        var removed = unitsById.remove(unit.getId());
        checkState(removed == unit);
    }
}
