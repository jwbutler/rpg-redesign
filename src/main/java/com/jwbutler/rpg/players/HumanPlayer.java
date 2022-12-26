package com.jwbutler.rpg.players;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;

public final class HumanPlayer extends AbstractPlayer implements Player
{
    public enum State
    {
        TITLE_SCREEN,
        GAME
    }

    @Nonnull
    private final Camera camera;
    @CheckForNull
    private Coordinates mouseCoordinates;
    @Nonnull
    private State state;

    public HumanPlayer(@Nonnull GameController controller, @Nonnull String name, @Nonnull Coordinates cameraCoordinates)
    {
        super(controller, name, Faction.PLAYER);
        this.camera = new Camera(controller, cameraCoordinates);
        this.mouseCoordinates = null;
        this.state = State.TITLE_SCREEN;
    }

    @Nonnull
    public Camera getCamera()
    {
        return camera;
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
