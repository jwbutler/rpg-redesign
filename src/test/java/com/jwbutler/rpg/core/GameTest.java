package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.EnemyPlayer;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.UnitFactory;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.DefendCommand;
import org.testng.annotations.Test;

import static com.jwbutler.rpg.units.UnitUtils.moveUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

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

    @Test
    public void testAddUnit()
    {
        var game = Game.create();
        var player = new HumanPlayer(game, "test_player", Coordinates.zero());
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);
        game.setCurrentLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        game.addUnit(unit);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));
    }

    @Test
    public void testRemoveUnit()
    {
        var game = Game.create();
        var player = new HumanPlayer(game, "test_player", Coordinates.zero());
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);
        game.setCurrentLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        game.addUnit(unit);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var enemyPlayer = new EnemyPlayer(game, "enemy_player");
        game.addPlayer(enemyPlayer);
        var enemy = UnitFactory.createEvilPlayerUnit(game, "targeting_unit", 10, enemyPlayer, level, new Coordinates(3, 3));
        game.addUnit(enemy);
        enemy.setCommand(new AttackCommand(unit));

        game.removeUnit(unit);
        assertNull(game.getUnitNullable(unit.getId()));
        assertNull(level.getUnit(unit.getCoordinates()));
        assertFalse(player.getUnits().contains(unit));
    }

    @Test
    public void testMoveUnit()
    {
        var game = Game.create();
        var player = new HumanPlayer(game, "test_player", Coordinates.zero());
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);
        game.setCurrentLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        game.addUnit(unit);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var newCoordinates = new Coordinates(2, 2);
        moveUnit(unit, unit.getLevel(), newCoordinates);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(unit.getLevel(), level);
        assertEquals(level.getUnit(newCoordinates), unit);
        assertEquals(unit.getCoordinates(), newCoordinates);
        assertTrue(player.getUnits().contains(unit));
    }
}
