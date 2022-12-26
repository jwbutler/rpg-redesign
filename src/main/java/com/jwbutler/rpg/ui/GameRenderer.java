package com.jwbutler.rpg.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.graphics.ImageUtils;
import com.jwbutler.rpg.levels.TileType;

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
        this.grassImage = ImageUtils.loadImage("tiles/green_32x24", Color.WHITE);
    }

    public void render(@Nonnull GameState state)
    {
        window.render(graphics ->
        {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            graphics.setColor(Color.WHITE);
            _drawGrid(state, graphics);
            _drawUnits(state, graphics);
        });
    }

    private void _drawGrid(@Nonnull GameState state, @Nonnull Graphics graphics)
    {
        graphics.setColor(Color.WHITE);
        var level = state.getCurrentLevel();
        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var coordinates = new Coordinates(x, y);
                var tile = level.getTile(coordinates);
                if (tile == TileType.GRASS)
                {
                    var humanPlayer = state.getHumanPlayer();
                    var topLeft = coordinates.toPixel(humanPlayer.getCameraCoordinates());
                    graphics.drawImage(grassImage, topLeft.x(), topLeft.y(), null);
                }
                else
                {
                    graphics.setColor(Color.WHITE);
                    _drawGridTile(state, graphics, coordinates);
                }
                if (coordinates.equals(state.getHumanPlayer().getMouseCoordinates()))
                {
                    graphics.setColor(Color.BLUE);
                    _drawGridTile(state, graphics, coordinates);
                }
            }
        }
    }

    private void _drawGridTile(@Nonnull GameState state, @Nonnull Graphics graphics, @Nonnull Coordinates coordinates)
    {
        var humanPlayer = state.getHumanPlayer();
        var topLeft = coordinates.toPixel(humanPlayer.getCameraCoordinates());
        graphics.drawPolygon(
            new int[] {
                topLeft.x() + TILE_WIDTH / 2,
                topLeft.x() + TILE_WIDTH,
                topLeft.x() + TILE_WIDTH / 2,
                topLeft.x()
            },
            new int[] {
                topLeft.y(),
                topLeft.y() + TILE_HEIGHT / 2,
                topLeft.y() + TILE_HEIGHT,
                topLeft.y() + TILE_HEIGHT / 2
            },
            4
        );
    }

    private void _drawUnits(@Nonnull GameState state, @Nonnull Graphics graphics)
    {
        var humanPlayer = state.getHumanPlayer();
        var level = state.getCurrentLevel();
        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var unit = level.getUnit(new Coordinates(x, y));
                if (unit != null)
                {
                    var image = unit.getSprite().getImage(unit);
                    var coordinates = new Coordinates(x, y);
                    var color = switch (unit.getPlayer().getFaction())
                    {
                        case PLAYER -> Color.GREEN;
                        case ENEMY -> Color.RED;
                        case NEUTRAL -> Color.GRAY;
                    };
                    graphics.setColor(color);
                    _drawGridTile(state, graphics, coordinates);
                    var topLeft = coordinates.toPixel(humanPlayer.getCameraCoordinates());
                    // TODO generalize these
                    topLeft = topLeft.plus((TILE_WIDTH - image.getWidth()) / 2, (TILE_WIDTH / 2) - image.getHeight());
                    graphics.drawImage(image, topLeft.x(), topLeft.y(), null);
                }
            }
        }
    }
}
