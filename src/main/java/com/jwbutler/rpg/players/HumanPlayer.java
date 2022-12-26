package com.jwbutler.rpg.players;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Coordinates;

public final class HumanPlayer extends AbstractPlayer implements Player
{
    public enum State
    {
        TITLE_SCREEN,
        GAME
    }

    @Nonnull
    private Coordinates cameraCoordinates;
    @CheckForNull
    private Coordinates mouseCoordinates;
    @Nonnull
    private State state;

    public HumanPlayer(@Nonnull String name, @Nonnull Coordinates cameraCoordinates)
    {
        super(name, Faction.PLAYER);
        this.cameraCoordinates = cameraCoordinates;
        this.mouseCoordinates = null;
        this.state = State.TITLE_SCREEN;
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

    @Nonnull
    public State getState()
    {
        return state;
    }

    public void setState(@Nonnull State state)
    {
        this.state = state;
    }
}
