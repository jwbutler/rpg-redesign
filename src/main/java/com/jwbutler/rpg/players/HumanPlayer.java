package com.jwbutler.rpg.players;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;

public final class HumanPlayer extends AbstractPlayer implements Player
{
    @Nonnull
    private Coordinates cameraCoordinates;
    @CheckForNull
    private Coordinates mouseCoordinates;

    public HumanPlayer(@Nonnull String name, @Nonnull Coordinates cameraCoordinates)
    {
        super(name, Faction.PLAYER);
        this.cameraCoordinates = cameraCoordinates;
        this.mouseCoordinates = null;
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

    @CheckForNull
    public Coordinates getMouseCoordinates()
    {
        return mouseCoordinates;
    }

    public void setMouseCoordinates(@Nonnull Coordinates mouseCoordinates)
    {
        this.mouseCoordinates = mouseCoordinates;
    }
}
