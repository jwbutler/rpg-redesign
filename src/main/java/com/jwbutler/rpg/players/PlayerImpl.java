package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkArgument;

final class PlayerImpl implements Player
{
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;
    @NonNull
    private final Faction faction;
    @NonNull
    private final Set<Unit> units;

    PlayerImpl(@NonNull String name, @NonNull Faction faction)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.faction = faction;
        this.units = new HashSet<>();
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
    public Faction getFaction()
    {
        return faction;
    }

    @Override
    public void addUnit(@NonNull Unit unit)
    {
        checkArgument(!units.contains(unit));
        units.add(unit);
    }

    @NonNull
    @Override
    public Set<Unit> getUnits()
    {
        return units;
    }

    @Override
    public void removeUnit(@NonNull Unit unit)
    {
        checkArgument(units.contains(unit));
        units.remove(unit);
    }
}
