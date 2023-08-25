package com.jwbutler.rpg.core;

import java.util.Comparator;
import java.util.List;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.Command;
import com.jwbutler.rpg.util.Pair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.jwbutler.rpg.util.Preconditions.checkState;
import static com.jwbutler.rpg.util.Symbols.$;

final class SessionImpl implements Session
{
    @NonNull
    private final Player player;
    @Nullable
    private Level currentLevel;
    @NonNull
    private final Camera camera;
    @NonNull
    private SessionState state;
    @Nullable
    private Coordinates mouseCoordinates;
    @Nullable
    private Unit activeUnit;

    @Nullable
    private Pair<Command, Unit> queuedCommand;

    SessionImpl(
        @NonNull Player player,
        @NonNull Camera camera
    )
    {
        this.player = player;
        this.camera = camera;
        this.currentLevel = null;
        this.state = SessionState.TITLE_SCREEN;
        this.mouseCoordinates = null;
        this.activeUnit = null;
        this.queuedCommand = null;
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
    public Coordinates getMouseCoordinates()
    {
        return mouseCoordinates;
    }

    @Override
    public void setMouseCoordinates(@Nullable Coordinates mouseCoordinates)
    {
        this.mouseCoordinates = mouseCoordinates;
    }

    @Override
    @Nullable
    public Unit getActiveUnit()
    {
        return activeUnit;
    }
    
    @Override
    public void selectNextUnit()
    {
        checkState(currentLevel != null);
        var units = _getSortedUnits(currentLevel);
        var index = units.indexOf(activeUnit); // I think this is null-safe
        var nextIndex = (index + 1) % units.size();
        activeUnit = units.get(nextIndex);
        
        // Should this go in some other function?
        camera.setCoordinates(activeUnit.getCoordinates());
    }

    @Override
    public void queueCommand(@NonNull Unit unit, @NonNull Command command)
    {
        queuedCommand = Pair.of(command, unit);
    }

    @Override
    @Nullable
    public Command getQueuedCommand(@NonNull Unit unit)
    {
        if (queuedCommand != null && queuedCommand.second() != unit)
        {
            queuedCommand = null;
        }
        return $(queuedCommand).map(Pair::first).orElse(null);
    }

    @Override
    public void clearQueuedCommand(@NonNull Unit unit)
    {
        queuedCommand = null;
    }

    @NonNull
    private List<Unit> _getSortedUnits(@NonNull Level level)
    {
        return level.getUnits()
            .stream()
            .sorted(Comparator.comparing(unit -> unit.getId().toString()))
            .toList();
    }
}
