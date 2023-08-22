package com.jwbutler.rpg.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkArgument;
import static com.jwbutler.rpg.util.Preconditions.checkState;

final class GameImpl implements Game
{
    @NonNull
    private final Map<UUID, Level> levelsById;
    @NonNull
    private final Map<UUID, Player> playersById;
    @NonNull
    private final Map<UUID, Unit> unitsById;

    GameImpl()
    {
        playersById = new HashMap<>();
        levelsById = new HashMap<>();
        unitsById = new HashMap<>();
    }

    @Override
    public void addPlayer(@NonNull Player player)
    {
        playersById.put(player.getId(), player);
    }

    /**
     * @throws IllegalArgumentException if there is no player with the specified id
     */
    @NonNull
    @Override
    public Player getPlayer(@NonNull UUID id)
    {
        var player = playersById.get(id);
        checkArgument(player != null);
        return player;
    }
    
    @NonNull
    @Override
    public Set<Player> getPlayers()
    {
        return new HashSet<>(playersById.values());
    }

    @Override
    public void addLevel(@NonNull Level level)
    {
        checkArgument(levelsById.get(level.getId()) == null);
        levelsById.put(level.getId(), level);
    }

    @Override
    @NonNull
    public Level getLevel(@NonNull UUID id)
    {
        var level = levelsById.get(id);
        checkArgument(level != null);
        return level;
    }

    @Override
    public void addUnit(@NonNull Unit unit)
    {
        checkArgument(unitsById.get(unit.getId()) == null);
        unitsById.put(unit.getId(), unit);
    }

    @Override
    @NonNull
    public Unit getUnit(@NonNull UUID id)
    {
        var unit = unitsById.get(id);
        checkArgument(unit != null);
        return unit;
    }

    @Override
    @Nullable
    public Unit getUnitNullable(@NonNull UUID id)
    {
        return unitsById.get(id);
    }

    @Override
    public void removeUnit(@NonNull Unit unit)
    {
        var removed = unitsById.remove(unit.getId());
        checkState(removed == unit);
    }
}
