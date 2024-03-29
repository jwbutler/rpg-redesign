package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.Command;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static java.util.Objects.requireNonNull;

final class GameEngineImpl implements GameEngine
{
    @NonNull
    private final Session session;
    @NonNull
    private final GameRenderer renderer;
    @NonNull
    private final GameWindow window;

    GameEngineImpl(
        @NonNull Session session,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        this.session = session;
        this.renderer = renderer;
        this.window = window;
    }

    @Override
    public void update(@NonNull Game game)
    {
        if (session.getActiveUnit() == null)
        {
            session.selectNextUnit();
        }
        var activeUnit = session.getActiveUnit();
        requireNonNull(activeUnit);

        if (activeUnit.getCommand() == null)
        {
            var command = getQueuedCommand(activeUnit);
            if (command != null)
            {
                activeUnit.setCommand(command);
            }
        }
        activeUnit.update();
        if (activeUnit.getCommand() != null && activeUnit.getCommand().isComplete(activeUnit))
        {
            activeUnit.setCommand(null);
            session.selectNextUnit();
        }
    }

    @Override
    public void render(@NonNull Game game)
    {
        renderer.render(game);
    }

    @Override
    public void moveCamera(@NonNull Direction direction)
    {
        var camera = session.getCamera();
        var coordinates = camera.getCoordinates().plus(direction);
        var level = session.getCurrentLevel();
        if (level.containsCoordinates(coordinates))
        {
            camera.setCoordinates(coordinates);
        }
    }

    @Override
    public void toggleScreenMaximized()
    {
        window.toggleMaximized();
    }

    @Override
    public void setMouseCoordinates(@NonNull Coordinates coordinates)
    {
        session.setMouseCoordinates(coordinates);
    }

    @Override
    public void loadLevel(@NonNull Level level)
    {
        session.setCurrentLevel(level);
        session.selectNextUnit();
    }

    @Override
    public void queueCommand(@NonNull Unit unit, @NonNull Command command)
    {
        session.queueCommand(unit, command);
    }

    @Override
    @Nullable
    public Command getQueuedCommand(@NonNull Unit unit)
    {
        var queuedCommand = session.getQueuedCommand(unit);
        session.clearQueuedCommand(unit);
        return queuedCommand;
    }

    @Override
    public void clearQueuedCommand(@NonNull Unit unit)
    {
        session.clearQueuedCommand(unit);
    }
}
