package com.jwbutler.rpg.ui;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameState;

public final class GameRenderer
{
    @Nonnull
    private final GameWindow window;

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
        });
    }
}
