package com.jwbutler.rpg.players;

import java.util.HashSet;
import java.util.Set;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

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

    @NonNull
    private final Camera camera;
    @Nullable
    private Coordinates mouseCoordinates;
    @NonNull
    private State state;
    @NonNull
    private final Set<Unit> selectedUnits;
    @Nullable
    private Pixel selectionStart;
    @Nullable
    private Pixel selectionEnd;

    public HumanPlayer(
        @NonNull Game game,
        @NonNull String name,
        @NonNull Coordinates cameraCoordinates
    )
    {
        super(game, name, Faction.PLAYER);
        this.camera = new Camera(game, cameraCoordinates);
        this.mouseCoordinates = null;
        this.state = State.TITLE_SCREEN;
        selectionStart = null;
        selectedUnits = new HashSet<>();
    }

    @NonNull
    public Camera getCamera()
    {
        return camera;
    }

    @Nullable
    public Coordinates getMouseCoordinates()
    {
        return mouseCoordinates;
    }

    public void setMouseCoordinates(@Nullable Coordinates mouseCoordinates)
    {
        this.mouseCoordinates = mouseCoordinates;
    }

    @NonNull
    public State getState()
    {
        return state;
    }

    public void setState(@NonNull State state)
    {
        this.state = state;
    }

    @Nullable
    public Pixel getSelectionStart()
    {
        return selectionStart;
    }

    public void setSelectionStart(@Nullable Pixel pixel)
    {
        selectionStart = pixel;
    }

    @Nullable
    public Pixel getSelectionEnd()
    {
        return selectionEnd;
    }

    public void setSelectionEnd(@Nullable Pixel pixel)
    {
        if (selectionStart == null)
        {
            selectionStart = pixel;
        }
        selectionEnd = pixel;
    }
    @NonNull
    public Set<Unit> getSelectedUnits()
    {
        return selectedUnits;
    }

    public void setSelectedUnits(@NonNull Set<Unit> units)
    {
        this.selectedUnits.clear();
        this.selectedUnits.addAll(units);
    }
}
