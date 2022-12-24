package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.UnitFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public final class GameControllerTest
{
    @Test
    public void testAddLevel()
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        GameController.setInstance(controller);
        var level = LevelFactory.TEST_LEVEL.get();

        controller.addLevel(level);
        assertEquals(state.getLevel(level.getId()), level);
    }

    @Test
    public void testAddUnit()
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        GameController.setInstance(controller);
        var player = new HumanPlayer("test_player", Coordinates.zero());
        controller.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        controller.addLevel(level);
        var unit = UnitFactory.createPlayerUnit("test_unit", 10, player, level, Coordinates.zero());

        controller.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));
    }

    @Test
    public void testRemoveUnit()
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        GameController.setInstance(controller);
        var player = new HumanPlayer("test_player", Coordinates.zero());
        controller.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        controller.addLevel(level);
        var unit = UnitFactory.createPlayerUnit("test_unit", 10, player, level, Coordinates.zero());

        controller.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        controller.removeUnit(unit);
        assertNull(state.getUnitNullable(unit.getId()));
        assertNull(level.getUnit(unit.getCoordinates()));
        assertFalse(player.getUnits().contains(unit));
    }

    @Test
    public void testMoveUnit()
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        GameController.setInstance(controller);
        var player = new HumanPlayer("test_player", Coordinates.zero());
        controller.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        controller.addLevel(level);
        var unit = UnitFactory.createPlayerUnit("test_unit", 10, player, level, Coordinates.zero());

        controller.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var newCoordinates = new Coordinates(2, 2);
        controller.moveUnit(unit, unit.getLevel(), newCoordinates);

        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(unit.getLevel(), level);
        assertEquals(level.getUnit(newCoordinates), unit);
        assertEquals(unit.getCoordinates(), newCoordinates);
        assertTrue(player.getUnits().contains(unit));
    }
}
