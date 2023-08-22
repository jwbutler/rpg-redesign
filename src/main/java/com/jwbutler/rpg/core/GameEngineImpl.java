package com.jwbutler.rpg.core;

import java.util.Set;
import java.util.stream.Collectors;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.core.GameStateUtils.getHumanPlayer;

final class GameEngineImpl implements GameEngine
{
    @NonNull
    private final GameController controller;
    @NonNull
    private final GameRenderer renderer;
    @NonNull
    private final GameWindow window;
    
    GameEngineImpl(
        @NonNull GameController controller,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        this.controller = controller;
        this.renderer = renderer;
        this.window = window;
    }

    @Override
    public void update(@NonNull GameState state)
    {
        for (var unit : state.getCurrentLevel().getUnits())
        {
            unit.update();
        }
    }

    @Override
    public void render(@NonNull GameState state)
    {
        renderer.render(state);
    }

    @Override
    public void moveCamera(@NonNull Direction direction)
    {
        var player = getHumanPlayer(controller.getState());
        var camera = player.getCamera();
        camera.move(direction);
    }

    @Override
    public void toggleScreenMaximized()
    {
        this.window.toggleMaximized();
    }

    @Override
    public void moveOrAttack(@NonNull Coordinates coordinates)
    {
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        var level = state.getCurrentLevel();

        if (level.containsCoordinates(coordinates))
        {
            var unit = level.getUnit(coordinates);
            var selectedUnits = humanPlayer.getSelectedUnits();
            if (unit != null && unit.getPlayer().getFaction() == Faction.ENEMY)
            {
                for (var playerUnit : selectedUnits)
                {
                    playerUnit.setNextCommand(new AttackCommand(unit));
                }
            }
            else if (unit == null)
            {
                for (var playerUnit : selectedUnits)
                {
                    playerUnit.setNextCommand(new MoveCommand(coordinates));
                }
            }
        }
    }

    @Override
    public void setMouseCoordinates(@NonNull Coordinates coordinates)
    {
        var humanPlayer = getHumanPlayer(controller.getState());
        humanPlayer.setMouseCoordinates(coordinates);
    }

    @Override
    public void startSelectionRect(@NonNull Pixel pixel)
    {
        var humanPlayer = getHumanPlayer(controller.getState());
        humanPlayer.setSelectionStart(pixel);
    }
    
    @Override
    public void updateSelectionRect(@NonNull Pixel pixel)
    {
        var humanPlayer = getHumanPlayer(controller.getState());
        humanPlayer.setSelectionEnd(pixel);
    }

    @Override
    public void finishSelectionRect(@NonNull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        var selectionStart = humanPlayer.getSelectionStart();
        if (selectionStart == null)
        {
            // don't think this is possible
            return;
        }
        var rect = Rect.between(selectionStart, pixel);
        Set<Unit> selectedUnits = _getUnitsInSelectionRect(rect);
        humanPlayer.setSelectionStart(null);
        humanPlayer.setSelectionEnd(null);
        humanPlayer.setSelectedUnits(selectedUnits);
    }
    
    @NonNull
    private Set<Unit> _getUnitsInSelectionRect(@NonNull Rect rect)
    {
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        var camera = humanPlayer.getCamera();
        return state.getCurrentLevel()
            .getUnits()
            .stream()
            .filter(u -> u.getPlayer() == humanPlayer)
            .filter(u -> rect.getIntersection(camera.coordinatesToPixelRect(u.getCoordinates())).area() >= 100) // arbitrary threshold
            .collect(Collectors.toSet());
    }
}
