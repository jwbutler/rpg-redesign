package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import org.jspecify.annotations.NonNull;

final class GameEngineImpl_Shining implements GameEngine_Shining
{
    @NonNull
    private final Session_Shining session;
    @NonNull
    private final GameRenderer renderer;
    @NonNull
    private final GameWindow window;
    
    GameEngineImpl_Shining(
        @NonNull Session_Shining session,
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
        for (var unit : session.getCurrentLevel().getUnits())
        {
            unit.update();
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
}
