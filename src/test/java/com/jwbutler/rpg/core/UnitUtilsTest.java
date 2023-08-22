package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.UnitFactory;
import com.jwbutler.rpg.units.UnitUtils;
import com.jwbutler.rpg.units.commands.AttackCommand;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * TODO - this doesn't totally make sense anymore
 * since I refactored the base classes
 */
public final class UnitUtilsTest
{
    @Test
    public void testAddUnit()
    {
        var game = Game.create();
        var player = Player.create("test_player", Faction.PLAYER);
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        UnitUtils.addUnit(unit, game);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));
    }

    @Test
    public void testRemoveUnit()
    {
        var game = Game.create();
        var player = Player.create("test_player", Faction.PLAYER);
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        UnitUtils.addUnit(unit, game);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var enemyPlayer = Player.create("test_player", Faction.ENEMY);
        game.addPlayer(enemyPlayer);
        var enemy = UnitFactory.createEvilPlayerUnit(game, "targeting_unit", 10, enemyPlayer, level, new Coordinates(3, 3));
        UnitUtils.addUnit(enemy, game);
        enemy.setCommand(new AttackCommand(unit));

        UnitUtils.removeUnit(unit, game);
        assertNull(game.getUnitNullable(unit.getId()));
        assertNull(level.getUnit(unit.getCoordinates()));
        assertFalse(player.getUnits().contains(unit));
    }

    @Test
    public void testMoveUnit()
    {
        var game = Game.create();
        var player = Player.create("test_player", Faction.PLAYER);
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        UnitUtils.addUnit(unit, game);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var newCoordinates = new Coordinates(2, 2);
        UnitUtils.moveUnit(unit, unit.getLevel(), newCoordinates);

        assertEquals(game.getUnit(unit.getId()), unit);
        assertEquals(unit.getLevel(), level);
        assertEquals(level.getUnit(newCoordinates), unit);
        assertEquals(unit.getCoordinates(), newCoordinates);
        assertTrue(player.getUnits().contains(unit));
    }
}
