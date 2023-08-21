package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.units.Unit;

import static com.jwbutler.rpg.util.Preconditions.checkArgument;

abstract class AbstractPlayer implements Player
{
    @NonNull
    private final GameController controller;
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;
    @NonNull
    private final Faction faction;
    @NonNull
    private final Set<Unit> units;

    protected AbstractPlayer(@NonNull GameController controller, @NonNull String name, @NonNull Faction faction)
    {
        this.controller = controller;
        this.id = UUID.randomUUID();
        this.name = name;
        this.faction = faction;
        this.units = new HashSet<>();
    }

    @NonNull
    @Override
    public final UUID getId()
    {
        return id;
    }

    @NonNull
    @Override
    public final String getName()
    {
        return name;
    }

    @NonNull
    @Override
    public final Faction getFaction()
    {
        return faction;
    }

    @Override
    public final void addUnit(@NonNull Unit unit)
    {
        checkArgument(!units.contains(unit));
        units.add(unit);
    }

    @NonNull
    @Override
    public final Set<Unit> getUnits()
    {
        return units;
    }

    @Override
    public final void removeUnit(@NonNull Unit unit)
    {
        checkArgument(units.contains(unit));
        units.remove(unit);
    }
}
