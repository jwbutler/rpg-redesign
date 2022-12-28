package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.Layer;
import com.jwbutler.rpg.graphics.Overlay;
import com.jwbutler.rpg.levels.TileType;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;

public final class GameRenderer
{
    @Nonnull
    private final GameWindow window;
    @Nonnull
    private final BufferedImage grassImage;

    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 24;

    public GameRenderer(@Nonnull GameWindow window)
    {
        this.window = window;
        this.grassImage = new ImageBuilder()
            .filename("tiles/green_32x24")
            .transparentColor(Colors.WHITE)
            .build();
    }

    public void render(@Nonnull GameState state)
    {
        window.render(graphics ->
        {
            graphics.setColor(Colors.BLACK);
            graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            graphics.setColor(Colors.WHITE);
            _drawGrid(state, graphics);
            _drawTileOverlays(state, graphics);
            _drawUnits(state, graphics);
        });
    }

    private void _drawGrid(@Nonnull GameState state, @Nonnull Graphics2D graphics)
    {
        var level = state.getCurrentLevel();
        var humanPlayer = state.getHumanPlayer();

        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var tile = level.getTile(coordinates);
                if (tile == TileType.GRASS)
                {
                    var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
                    graphics.drawImage(grassImage, tileRect.left(), tileRect.top(), null);
                }
            }
        }
    }

    private void _drawTileOverlays(
        @Nonnull GameState state,
        @Nonnull Graphics2D graphics
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var playerUnit = state.getPlayerUnit();

        if (humanPlayer.getMouseCoordinates() != null)
        {
            _drawOverlay(Overlay.TILE_MOUSEOVER, state, graphics, humanPlayer.getMouseCoordinates());
        }

        switch (playerUnit.getLatestCommand())
        {
            case MoveCommand mc -> _drawOverlay(Overlay.TILE_TARGETED, state, graphics, mc.target());
            case AttackCommand ac -> _drawOverlay(Overlay.TILE_TARGETED, state, graphics, ac.target().getCoordinates());
            default -> {}
        }
    }

    private void _drawUnits(@Nonnull GameState state, @Nonnull Graphics2D graphics)
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

    private void _drawUnit(
        @Nonnull GameState state,
        @Nonnull Graphics2D graphics,
        @Nonnull Unit unit
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var playerUnit = state.getPlayerUnit();
        var coordinates = unit.getCoordinates();
        var frame = unit.getSprite().getFrame(unit);
        var image = frame.image();
        var overlay = switch (unit.getPlayer().getFaction())
        {
            case PLAYER -> Overlay.PLAYER_ACTIVE;
            case ENEMY, NEUTRAL -> // NEUTRAL doesn't really make sense, oh well
            {
                if (playerUnit.getCommand() instanceof AttackCommand ac && ac.target() == unit)
                {
                    yield Overlay.ENEMY_TARGETED;
                }
                yield Overlay.ENEMY_INACTIVE;
            }
        };
        _drawOverlay(overlay, state, graphics, coordinates);
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

    private void _drawEquipment(
        @Nonnull GameState state,
        @Nonnull Graphics2D graphics,
        @Nonnull Equipment equipment
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

    private void _drawOverlay(
        @Nonnull Overlay overlay,
        @Nonnull GameState state,
        @Nonnull Graphics2D graphics,
        @Nonnull Coordinates coordinates
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
        var image = overlay.getImage();
        graphics.drawImage(image, tileRect.left(), tileRect.top(), null);
    }
}
