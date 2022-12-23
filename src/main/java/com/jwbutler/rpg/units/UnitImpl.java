package com.jwbutler.rpg.units;

import java.util.UUID;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;

final class UnitImpl implements Unit
{
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    private int life;
    private int maxLife;
    @Nonnull
    private Activity activity;
    @Nonnull
    private Command command;
    @Nonnull
    private Player player;
    @Nonnull
    private Level level;
    @Nonnull
    private Coordinates coordinates;

    UnitImpl(
        @Nonnull String name,
        int life,
        @Nonnull Player player,
        @Nonnull Level level,
        @Nonnull Coordinates coordinates
    )
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.activity = Activity.STANDING;
        this.command = Command.STAY;
        this.player = player;
        this.level = level;
        this.coordinates = coordinates;
    }

    @Override
    @Nonnull
    public UUID getId()
    {
        return id;
    }

    @Override
    @Nonnull
    public String getName()
    {
        return name;
    }

    @Override
    public int getLife()
    {
        return life;
    }

    @Override
    public int getMaxLife()
    {
        return maxLife;
    }

    @Override
    @Nonnull
    public Activity getActivity()
    {
        return activity;
    }

    @Override
    public void setActivity(@Nonnull Activity activity)
    {
        this.activity = activity;
    }

    @Override
    @Nonnull
    public Command getCommand()
    {
        return command;
    }

    @Override
    public void setCommand(@Nonnull Command command)
    {
        this.command = command;
    }

    @Nonnull
    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    @Nonnull
    public Level getLevel()
    {
        return level;
    }

    @Override
    public void setLevel(@Nonnull Level level)
    {
        this.level = level;
    }

    @Nonnull
    @Override
    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    @Override
    public void setCoordinates(@Nonnull Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }
}
