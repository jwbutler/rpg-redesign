package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkArgument;

final class PlayerImpl implements Player
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Faction faction;
    @Nonnull
    private final Set<Unit> units;

    PlayerImpl(@Nonnull String name, @Nonnull Faction faction)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.faction = faction;
        this.units = new HashSet<>();
    }

    @Nonnull
    @Override
    public UUID getId()
    {
        return id;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return name;
    }

    @Nonnull
    @Override
    public Faction getFaction()
    {
        return faction;
    }

    @Override
    public void addUnit(@Nonnull Unit unit)
    {
        checkArgument(!units.contains(unit));
        units.add(unit);
    }

    @Nonnull
    @Override
    public Set<Unit> getUnits()
    {
        return units;
    }

    @Override
    public void removeUnit(@Nonnull Unit unit)
    {
        checkArgument(units.contains(unit));
        units.remove(unit);
    }
}
