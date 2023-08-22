package com.jwbutler.rpg.core;

import java.util.Set;
import java.util.stream.Collectors;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;
import org.jspecify.annotations.NonNull;

final class GameEngineImpl implements GameEngine
{
    @NonNull
    private final Game game;
    @NonNull
    private final Session session;
    @NonNull
    private final GameRenderer renderer;
    @NonNull
    private final GameWindow window;
    
    GameEngineImpl(
        @NonNull Game game,
        @NonNull Session session,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        this.game = game;
        this.session = session;
        this.renderer = renderer;
        this.window = window;
    }

    @Override
    public void update(@NonNull Game game)
    {
        for (var unit : game.getCurrentLevel().getUnits())
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
        var player = session.getPlayer();
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
        var humanPlayer = session.getPlayer();
        var level = game.getCurrentLevel();

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
        var humanPlayer = session.getPlayer();
        humanPlayer.setMouseCoordinates(coordinates);
    }

    @Override
    public void startSelectionRect(@NonNull Pixel pixel)
    {
        var humanPlayer = session.getPlayer();
        humanPlayer.setSelectionStart(pixel);
    }
    
    @Override
    public void updateSelectionRect(@NonNull Pixel pixel)
    {
        var humanPlayer = session.getPlayer();
        humanPlayer.setSelectionEnd(pixel);
    }

    @Override
    public void finishSelectionRect(@NonNull Pixel pixel)
    {
        var humanPlayer = session.getPlayer();
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
        var humanPlayer = session.getPlayer();
        var camera = humanPlayer.getCamera();
        return game.getCurrentLevel()
            .getUnits()
            .stream()
            .filter(u -> u.getPlayer() == humanPlayer)
            .filter(u -> rect.getIntersection(camera.coordinatesToPixelRect(u.getCoordinates())).area() >= 100) // arbitrary threshold
            .collect(Collectors.toSet());
    }
}
