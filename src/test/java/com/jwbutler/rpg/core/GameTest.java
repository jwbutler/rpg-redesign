package com.jwbutler.rpg.core;

import com.jwbutler.rpg.levels.LevelFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public final class GameTest
{
    @Test
    public void testAddLevel()
    {
        var game = Game.create();
        var level = LevelFactory.TEST_LEVEL.get();

        game.addLevel(level);
        assertEquals(game.getLevel(level.getId()), level);
    }
}
