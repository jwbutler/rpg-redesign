package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.units.Unit;

import static com.google.common.base.Preconditions.checkArgument;

abstract class AbstractPlayer implements Player
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Faction faction;
    @Nonnull
    private final Set<Unit> units;

    protected AbstractPlayer(@Nonnull String name, @Nonnull Faction faction)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.faction = faction;
        this.units = new HashSet<>();
    }

    @Nonnull
    @Override
    public final UUID getId()
    {
        return id;
    }

    @Nonnull
    @Override
    public final String getName()
    {
        return name;
    }

    @Nonnull
    @Override
    public final Faction getFaction()
    {
        return faction;
    }

    @Override
    public final void addUnit(@Nonnull Unit unit)
    {
        checkArgument(!units.contains(unit));
        units.add(unit);
    }

    @Nonnull
    @Override
    public final Set<Unit> getUnits()
    {
        return units;
    }

    @Override
    public final void removeUnit(@Nonnull Unit unit)
    {
        checkArgument(units.contains(unit));
        units.remove(unit);
    }
}
