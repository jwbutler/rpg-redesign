package com.jwbutler.rpg.ui;

import java.awt.Graphics2D;

import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.core.Session;
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
    @NonNull
    private final Session session;
    
    GameRendererImpl(
        @NonNull GameWindow window,
        @NonNull Session session
    )
    {
        this.window = window;
        this.session = session;
    }

    @Override
    public void render(@NonNull Game game)
    {
        window.render(graphics ->
        {
            graphics.setColor(Colors.BLACK);
            graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            graphics.setColor(Colors.WHITE);
            _drawGrid(game, graphics);
            _drawTileOverlays(game, graphics);
            _drawUnits(game, graphics);
            _drawUiOverlays(graphics);
        });
    }

    private void _drawGrid(@NonNull Game game, @NonNull Graphics2D graphics)
    {
        var level = game.getCurrentLevel();

        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var tile = level.getTile(coordinates);
                var frame = tile.getFrame();
                var tileRect = session.getCamera().coordinatesToPixelRect(coordinates);
                graphics.drawImage(frame.image(), tileRect.left(), tileRect.top(), null);
            }
        }
    }

    private void _drawTileOverlays(
        @NonNull Game game,
        @NonNull Graphics2D graphics
    )
    {
        for (var coordinates : game.getCurrentLevel().getAllCoordinates())
        {
            _drawTileOverlay(TileOverlay.TILE_GRID, graphics, coordinates);
        }
        
        if (session.getMouseCoordinates() != null)
        {
            _drawTileOverlay(TileOverlay.TILE_MOUSEOVER, graphics, session.getMouseCoordinates());
        }

        var humanPlayer = session.getPlayer();
        for (var playerUnit : humanPlayer.getUnits())
        {
            switch (playerUnit.getLatestCommand())
            {
                case MoveCommand mc -> _drawTileOverlay(TileOverlay.TILE_TARGETED, graphics, mc.target());
                case AttackCommand ac -> _drawTileOverlay(TileOverlay.TILE_TARGETED, graphics, ac.target().getCoordinates());
                default -> {}
            }
        }
    }

    private void _drawUnits(@NonNull Game game, @NonNull Graphics2D graphics)
    {
        var level = game.getCurrentLevel();

        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var unit = level.getUnit(coordinates);
                if (unit != null)
                {
                    _drawUnit(graphics, unit);
                }
            }
        }
    }

    private void _drawUnit(
        @NonNull Graphics2D graphics,
        @NonNull Unit unit
    )
    {
        var humanPlayer = session.getPlayer();
        var coordinates = unit.getCoordinates();
        var frame = unit.getSprite().getFrame(unit);
        var image = frame.image();
        var overlay = switch (unit.getPlayer().getFaction())
        {
            case PLAYER ->
            {
                if (session.getSelectedUnits().contains(unit))
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
        _drawTileOverlay(overlay, graphics, coordinates);
        var tileRect = session.getCamera().coordinatesToPixelRect(coordinates);
        var topLeft = new Pixel(
            tileRect.left() + (TILE_WIDTH - image.getWidth()) / 2,
            tileRect.top() + (TILE_WIDTH / 2) - image.getHeight()
        );

        for (var equipment : unit.getEquipment())
        {
            if (equipment.getSprite().getFrame(equipment).layer() == Layer.EQUIPMENT_UNDER)
            {
                _drawEquipment(graphics, equipment);
            }
        }
        graphics.drawImage(image, topLeft.x(), topLeft.y(), null);

        for (var equipment : unit.getEquipment())
        {
            if (equipment.getSprite().getFrame(equipment).layer() == Layer.EQUIPMENT_OVER)
            {
                _drawEquipment(graphics, equipment);
            }
        }
    }

    private void _drawEquipment(
        @NonNull Graphics2D graphics,
        @NonNull Equipment equipment
    )
    {
        var unit = equipment.getUnit();
        var coordinates = unit.getCoordinates();
        var tileRect = session.getCamera().coordinatesToPixelRect(coordinates);
        var frame = equipment.getSprite().getFrame(equipment);
        var image = frame.image();
        var topLeft = new Pixel(
            tileRect.left() + (TILE_WIDTH - image.getWidth()) / 2,
            tileRect.top() + (TILE_WIDTH / 2) - image.getHeight()
        );
        graphics.drawImage(image, topLeft.x(), topLeft.y(), null);
    }

    private void _drawTileOverlay(
        @NonNull TileOverlay overlay,
        @NonNull Graphics2D graphics,
        @NonNull Coordinates coordinates
    )
    {
        var tileRect = session.getCamera().coordinatesToPixelRect(coordinates);
        var image = overlay.getImage();
        graphics.drawImage(image, tileRect.left(), tileRect.top(), null);
    }

    private void _drawUiOverlays(@NonNull Graphics2D graphics)
    {
        var start = session.getSelectionStart();
        var end = session.getSelectionEnd();
        if (start != null && end != null)
        {
            graphics.setColor(Colors.CYAN);
            var rect = Rect.between(start, end);
            graphics.drawRect(rect.left(), rect.top(), rect.width(), rect.height());
        }
    }
}
