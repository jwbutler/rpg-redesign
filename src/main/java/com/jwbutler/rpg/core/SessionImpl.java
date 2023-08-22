package com.jwbutler.rpg.core;

import java.util.HashSet;
import java.util.Set;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

final class SessionImpl implements Session
{
    @NonNull
    private final Player player;
    @NonNull
    private final Camera camera;
    @NonNull
    private final Set<Unit> selectedUnits;
    @NonNull
    private SessionState state;
    @Nullable
    private Pixel selectionStart;
    @Nullable
    private Pixel selectionEnd;
    @NonNull
    private Coordinates mouseCoordinates;
    
    SessionImpl(@NonNull Player player, @NonNull Camera camera)
    {
        this.player = player;
        this.camera = camera;
        this.state = SessionState.TITLE_SCREEN;
        this.selectedUnits = new HashSet<>();
        this.selectionStart = null;
        this.selectionEnd = null;
        this.mouseCoordinates = null;
    }

    @Override
    @NonNull
    public Player getPlayer()
    {
        return player;
    }

    @Override
    @NonNull
    public SessionState getState()
    {
        return state;
    }
    
    @Override
    public void setState(@NonNull SessionState state)
    {
        this.state = state;
    }

    @NonNull
    public Camera getCamera()
    {
        return camera;
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
        selectedUnits.clear();
        selectedUnits.addAll(units);
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
}
