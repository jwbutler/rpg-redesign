package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.units.Unit;

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
    @Nonnull
    private final Set<Unit> selectedUnits;
    @CheckForNull
    private Pixel selectionStart;
    @CheckForNull
    private Pixel selectionEnd;

    public HumanPlayer(
        @Nonnull GameController controller,
        @Nonnull String name,
        @Nonnull Coordinates cameraCoordinates
    )
    {
        super(controller, name, Faction.PLAYER);
        this.camera = new Camera(controller, cameraCoordinates);
        this.mouseCoordinates = null;
        this.state = State.TITLE_SCREEN;
        selectionStart = null;
        selectedUnits = new HashSet<>();
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

    public void setMouseCoordinates(@CheckForNull Coordinates mouseCoordinates)
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

    @CheckForNull
    public Pixel getSelectionStart()
    {
        return selectionStart;
    }

    public void setSelectionStart(@CheckForNull Pixel pixel)
    {
        selectionStart = pixel;
    }

    @CheckForNull
    public Pixel getSelectionEnd()
    {
        return selectionEnd;
    }

    public void setSelectionEnd(@CheckForNull Pixel pixel)
    {
        if (selectionStart == null)
        {
            selectionStart = pixel;
        }
        selectionEnd = pixel;
    }
    @Nonnull
    public Set<Unit> getSelectedUnits()
    {
        return selectedUnits;
    }

    public void setSelectedUnits(@Nonnull Set<Unit> units)
    {
        this.selectedUnits.clear();
        this.selectedUnits.addAll(units);
    }
}
