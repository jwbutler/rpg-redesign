package com.jwbutler.rpg.players;

import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;

public final class HumanPlayer extends AbstractPlayer implements Player
{
    @Nonnull
    private Coordinates cameraCoordinates;

    public HumanPlayer(@Nonnull String name, @Nonnull Coordinates cameraCoordinates)
    {
        super(name, Faction.PLAYER);
        this.cameraCoordinates = cameraCoordinates;
    }

    @Nonnull
    public Coordinates getCameraCoordinates()
    {
        return cameraCoordinates;
    }

    public void setCameraCoordinates(@Nonnull Coordinates coordinates)
    {
        cameraCoordinates = coordinates;
    }
}
