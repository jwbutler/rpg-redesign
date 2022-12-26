package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.graphics.Colors;
import com.jwbutler.rpg.graphics.ImageBuilder;
import com.jwbutler.rpg.graphics.Overlay;
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
            _drawUnits(state, graphics);
        });
    }

    private void _drawGrid(@Nonnull GameState state, @Nonnull Graphics graphics)
    {
        graphics.setColor(Colors.WHITE);
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
                    var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
                    graphics.drawImage(grassImage, tileRect.left(), tileRect.top(), null);
                }
                if (coordinates.equals(state.getHumanPlayer().getMouseCoordinates()))
                {
                    _drawOverlay(Overlay.PLAYER_ACTIVE, state, graphics, coordinates);
                }
            }
        }
    }

    private void _drawOverlay(
        @Nonnull Overlay overlay,
        @Nonnull GameState state,
        @Nonnull Graphics graphics,
        @Nonnull Coordinates coordinates
    )
    {
        var humanPlayer = state.getHumanPlayer();
        var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
        var image = overlay.getImage();
        graphics.drawImage(image, tileRect.left(), tileRect.top(), null);
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
                    var overlay = switch (unit.getPlayer().getFaction())
                    {
                        case PLAYER -> Overlay.PLAYER_ACTIVE;
                        case ENEMY -> Overlay.ENEMY_ACTIVE;
                        case NEUTRAL -> Overlay.TILE_ACTIVE;
                    };
                    _drawOverlay(overlay, state, graphics, coordinates);
                    var tileRect = humanPlayer.getCamera().coordinatesToPixelRect(coordinates);
                    var topLeft = new Pixel(
                        tileRect.left() + (TILE_WIDTH - image.getWidth()) / 2,
                        tileRect.top() + (TILE_WIDTH / 2) - image.getHeight()
                    );
                    graphics.drawImage(image, topLeft.x(), topLeft.y(), null);
                }
            }
        }
    }
}
