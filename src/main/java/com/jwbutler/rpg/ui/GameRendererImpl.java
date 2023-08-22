package com.jwbutler.rpg.ui;

import java.awt.Graphics2D;

import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.graphics.TileOverlay;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;
import static com.jwbutler.rpg.geometry.GeometryConstants.TILE_WIDTH;

final class GameRendererImpl implements GameRenderer
{
    @NonNull
    private final GameWindow window;

    GameRendererImpl(@NonNull GameWindow window)
    {
        this.window = window;
    }

    @Override
    public void render(@NonNull GameState state)
    {
        window.render(graphics ->
        {
            graphics.setColor(Colors.BLACK);
            graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            graphics.setColor(Colors.WHITE);
            _drawGrid(state, graphics);
            _drawTileOverlays(state, graphics);
            _drawUnits(state, graphics);
            _drawUiOverlays(state, graphics);
        });
    }

    private static void _drawGrid(@NonNull GameState state, @NonNull Graphics2D graphics)
    {
        var level = state.getCurrentLevel();
        var humanPlayer = state.getHumanPlayer();

        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var tile = level.getTile(coordinates);
                var frame = tile.getFrame();
                var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
                graphics.drawImage(frame.image(), tileRect.left(), tileRect.top(), null);
            }
        }
    }

    private static void _drawTileOverlays(
        @NonNull GameState state,
        @NonNull Graphics2D graphics
    )
    {
        var humanPlayer = state.getHumanPlayer();

        if (humanPlayer.getMouseCoordinates() != null)
        {
            _drawTileOverlay(TileOverlay.TILE_MOUSEOVER, state, graphics, humanPlayer.getMouseCoordinates());
        }

        for (var playerUnit : humanPlayer.getUnits())
        {
            switch (playerUnit.getLatestCommand())
            {
                case MoveCommand mc -> _drawTileOverlay(TileOverlay.TILE_TARGETED, state, graphics, mc.target());
                case AttackCommand ac -> _drawTileOverlay(TileOverlay.TILE_TARGETED, state, graphics, ac.target().getCoordinates());
                default -> {}
            }
        }
    }

    private static void _drawUnits(@NonNull GameState state, @NonNull Graphics2D graphics)
    {
        var level = state.getCurrentLevel();

        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var unit = level.getUnit(coordinates);
                if (unit != null)
                {
                    _drawUnit(state, graphics, unit);
                }
            }
        }
    }

    private static void _drawUnit(
        @NonNull GameState state,
        @NonNull Graphics2D graphics,
        @NonNull Unit unit
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var coordinates = unit.getCoordinates();
        var frame = unit.getSprite().getFrame(unit);
        var image = frame.image();
        var overlay = switch (unit.getPlayer().getFaction())
        {
            case PLAYER ->
            {
                if (humanPlayer.getSelectedUnits().contains(unit))
                {
                    yield TileOverlay.PLAYER_ACTIVE;
                }
                else
                {
                    yield TileOverlay.PLAYER_INACTIVE;
                }
            }
            case ENEMY, NEUTRAL -> // NEUTRAL doesn't really make sense, oh well
            {
                if (humanPlayer.getUnits()
                    .stream()
                    .anyMatch(playerUnit -> playerUnit.getCommand() instanceof AttackCommand ac && ac.target() == unit))
                {
                    yield TileOverlay.ENEMY_TARGETED;
                }
                yield TileOverlay.ENEMY_INACTIVE;
            }
        };
        _drawTileOverlay(overlay, state, graphics, coordinates);
        var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
        var topLeft = new Pixel(
            tileRect.left() + (TILE_WIDTH - image.getWidth()) / 2,
            tileRect.top() + (TILE_WIDTH / 2) - image.getHeight()
        );

        for (var equipment : unit.getEquipment())
        {
            if (equipment.getSprite().getFrame(equipment).layer() == Layer.EQUIPMENT_UNDER)
            {
                _drawEquipment(state, graphics, equipment);
            }
        }
        graphics.drawImage(image, topLeft.x(), topLeft.y(), null);

        for (var equipment : unit.getEquipment())
        {
            if (equipment.getSprite().getFrame(equipment).layer() == Layer.EQUIPMENT_OVER)
            {
                _drawEquipment(state, graphics, equipment);
            }
        }
    }

    private static void _drawEquipment(
        @NonNull GameState state,
        @NonNull Graphics2D graphics,
        @NonNull Equipment equipment
    )
    {
        var unit = equipment.getUnit();
        var coordinates = unit.getCoordinates();
        var humanPlayer = state.getHumanPlayer();
        var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
        var frame = equipment.getSprite().getFrame(equipment);
        var image = frame.image();
        var topLeft = new Pixel(
            tileRect.left() + (TILE_WIDTH - image.getWidth()) / 2,
            tileRect.top() + (TILE_WIDTH / 2) - image.getHeight()
        );
        graphics.drawImage(image, topLeft.x(), topLeft.y(), null);
    }

    private static void _drawTileOverlay(
        @NonNull TileOverlay overlay,
        @NonNull GameState state,
        @NonNull Graphics2D graphics,
        @NonNull Coordinates coordinates
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
        var image = overlay.getImage();
        graphics.drawImage(image, tileRect.left(), tileRect.top(), null);
    }

    private static void _drawUiOverlays(@NonNull GameState state, @NonNull Graphics2D graphics)
    {
        var humanPlayer = state.getHumanPlayer();
        var start = humanPlayer.getSelectionStart();
        var end = humanPlayer.getSelectionEnd();
        if (start != null && end != null)
        {
            graphics.setColor(Colors.CYAN);
            var rect = Rect.between(start, end);
            graphics.drawRect(rect.left(), rect.top(), rect.width(), rect.height());
        }
    }
}
