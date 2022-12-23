package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.units.Unit;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
        var level = Level.create("test_level");
        engine.addLevel(level);
        var unit = Unit.create("test_unit", 10, level, Coordinates.zero());

        engine.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);
    }

    @Test
    public void testRemoveUnit()
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var level = Level.create("test_level");
        engine.addLevel(level);
        var unit = Unit.create("test_unit", 10, level, Coordinates.zero());

        engine.addUnit(unit);
        assertEquals(state.getUnit(unit.getId()), unit);
        assertEquals(level.getUnit(unit.getCoordinates()), unit);

        engine.removeUnit(unit);
        assertNull(state.getUnitNullable(unit.getId()));
        assertNull(level.getUnit(unit.getCoordinates()));
    }
}
