package com.jwbutler.rpg.core;

import java.util.HashSet;
import java.util.Set;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.jwbutler.rpg.util.Preconditions.checkState;

final class SessionImpl implements Session
{
    @NonNull
    private final Player player;
    @Nullable
    private Level currentLevel;
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
    @Nullable
    private Coordinates mouseCoordinates;
    
    SessionImpl(@NonNull Player player, @NonNull Camera camera)
    {
        this.player = player;
        this.camera = camera;
        this.currentLevel = null;
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
    public Level getCurrentLevel()
    {
        checkState(currentLevel != null);
        return currentLevel;
    }

    @Override
    public void setCurrentLevel(@NonNull Level currentLevel)
    {
        this.currentLevel = currentLevel;
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
    @Override
    public Camera getCamera()
    {
        return camera;
    }
    
    @Nullable
    @Override
    public Pixel getSelectionStart()
    {
        return selectionStart;
    }

    @Override
    public void setSelectionStart(@Nullable Pixel pixel)
    {
        selectionStart = pixel;
    }

    @Nullable
    @Override
    public Pixel getSelectionEnd()
    {
        return selectionEnd;
    }

    @Override
    public void setSelectionEnd(@Nullable Pixel pixel)
    {
        if (selectionStart == null)
        {
            selectionStart = pixel;
        }
        selectionEnd = pixel;
    }

    @NonNull
    @Override
    public Set<Unit> getSelectedUnits()
    {
        return selectedUnits;
    }

    @Override
    public void setSelectedUnits(@NonNull Set<Unit> units)
    {
        selectedUnits.clear();
        selectedUnits.addAll(units);
    }

    @Nullable
    @Override
    public Coordinates getMouseCoordinates()
    {
        return mouseCoordinates;
    }

    @Override
    public void setMouseCoordinates(@Nullable Coordinates mouseCoordinates)
    {
        this.mouseCoordinates = mouseCoordinates;
    }
}
