package com.jwbutler.rpg.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;

public final class GameRenderer
{
    @Nonnull
    private final GameWindow window;

    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 24;

    public GameRenderer(@Nonnull GameWindow window)
    {
        this.window = window;
    }

    public void render(@Nonnull GameState state)
    {
        window.render(graphics ->
        {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
            var level = state.getCurrentLevel();
            var playerUnit = state.getPlayerUnit();
            graphics.setColor(Color.WHITE);
            graphics.drawString(playerUnit.getName(), 10, 10);
            graphics.drawString(playerUnit.getCoordinates().toString(), 10, 30);
            graphics.drawString(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), 10, 50);
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
                var topLeft = coordinatesToPixel(new Coordinates(x, y), state.getCameraCoordinates());
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
                        topLeft.y() + TILE_HEIGHT / 2,
                    },
                    4
                );
            }
        }
    }

    private void _drawUnits(@Nonnull GameState state, @Nonnull Graphics graphics)
    {
        var level = state.getCurrentLevel();
        for (int y = 0; y < level.getDimensions().height(); y++)
        {
            for (int x = 0; x < level.getDimensions().width(); x++)
            {
                var unit = level.getUnit(new Coordinates(x, y));
                if (unit != null)
                {
                    var faction = unit.getPlayer().getFaction();
                    var color = switch (faction)
                    {
                        case PLAYER -> Color.GREEN;
                        case ENEMY -> Color.RED;
                        case NEUTRAL -> Color.LIGHT_GRAY;
                    };
                    graphics.setColor(color);
                    var topLeft = coordinatesToPixel(new Coordinates(x, y), state.getCameraCoordinates());
                    graphics.fillOval(
                        topLeft.x() + TILE_WIDTH / 4,
                        topLeft.y() + TILE_HEIGHT / 4,
                        TILE_WIDTH / 2,
                        TILE_HEIGHT / 2
                    );
                }
            }
        }
    }

    /**
     * @return the top-left corner of the resulting grid tile
     */
    @Nonnull
    private Pixel coordinatesToPixel(@Nonnull Coordinates coordinates, @Nonnull Coordinates cameraCoordinates)
    {
        var x = (coordinates.x() - cameraCoordinates.x() - coordinates.y() + cameraCoordinates.y() - 1) * (TILE_WIDTH / 2) + GameWindow.WIDTH / 2;
        var y = (coordinates.x() - cameraCoordinates.x() + coordinates.y() - cameraCoordinates.y() - 1) * (TILE_HEIGHT / 2) + GameWindow.HEIGHT / 2;
        return new Pixel(x, y);
    }
}
