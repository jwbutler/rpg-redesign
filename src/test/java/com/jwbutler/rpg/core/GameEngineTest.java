package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public final class GameEngineTest
{
    @Test
    public void testAddLevel()
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var level = Level.create("test_level");

        engine.addLevel(level);
        assertEquals(state.getLevel(level.getId()), level);
    }

    @Test
    public void testAddUnit()
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var player = Player.create("test_player", Faction.PLAYER);
        engine.addPlayer(player);
        var level = Level.create("test_level");
        engine.addLevel(level);
        var unit = Unit.create("test_unit", 10, player, level, Coordinates.zero());

        engine.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));
    }

    @Test
    public void testRemoveUnit()
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var player = Player.create("test_player", Faction.PLAYER);
        engine.addPlayer(player);
        var level = Level.create("test_level");
        engine.addLevel(level);
        var unit = Unit.create("test_unit", 10, player, level, Coordinates.zero());

        engine.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        engine.removeUnit(unit);
        assertNull(state.getUnitNullable(unit.getId()));
        assertNull(level.getUnit(unit.getCoordinates()));
        assertFalse(player.getUnits().contains(unit));
    }

    @Test
    public void testMoveUnit()
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var player = Player.create("test_player", Faction.PLAYER);
        engine.addPlayer(player);
        var level = Level.create("test_level");
        engine.addLevel(level);
        var unit = Unit.create("test_unit", 10, player, level, Coordinates.zero());

        engine.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
        assertTrue(player.getUnits().contains(unit));

        var newCoordinates = new Coordinates(2, 2);
        engine.moveUnit(unit, unit.getLevel(), newCoordinates);

        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(unit.getLevel(), level);
        assertEquals(level.getUnit(newCoordinates), unit);
        assertEquals(unit.getCoordinates(), newCoordinates);
        assertTrue(player.getUnits().contains(unit));
    }
}
