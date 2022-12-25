package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.UnitFactory;
import com.jwbutler.rpg.units.commands.MoveCommand;
import com.jwbutler.rpg.units.commands.StayCommand;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * no, not that kind of unit test
 */
public final class UnitTest
{
    @Test
    public void testUpdate()
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        var player = new HumanPlayer("test_player", Coordinates.zero());
        controller.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        controller.addLevel(level);

        var unit = UnitFactory.createPlayerUnit(controller, "test_unit", 10, player, level, Coordinates.zero());
        controller.addUnit(unit);

        assertEquals(unit.getActivity(), Activity.STANDING);
        assertEquals(unit.getDirection(), Direction.SE);
        assertEquals(unit.getFrameNumber(), 0);
        assertEquals(unit.getCommand(), new StayCommand(controller));
        assertNull(unit.getNextCommand());

        for (int i = 1; i <= 3; i++)
        {
            unit.update();

            assertEquals(unit.getActivity(), Activity.STANDING);
            assertEquals(unit.getDirection(), Direction.SE);
            assertEquals(unit.getFrameNumber(), 0);
            assertEquals(unit.getCommand(), new StayCommand(controller));
            assertNull(unit.getNextCommand());
        }

        var moveCommand = new MoveCommand(controller, new Coordinates(0, 2));
        unit.setNextCommand(moveCommand);

        for (int i = 0; i <= 1; i++)
        {
            unit.update();

            assertEquals(unit.getActivity(), Activity.WALKING);
            assertEquals(unit.getDirection(), Direction.S);
            assertEquals(unit.getFrameNumber(), i);
            assertEquals(unit.getCommand(), moveCommand);
            assertNull(unit.getNextCommand());
        }

        for (int i = 0; i <= 1; i++)
        {
            unit.update();

            assertEquals(unit.getCoordinates(), new Coordinates(0, 1));
            assertEquals(unit.getActivity(), Activity.WALKING);
            assertEquals(unit.getDirection(), Direction.S);
            assertEquals(unit.getFrameNumber(), i);
            assertEquals(unit.getCommand(), moveCommand);
            assertNull(unit.getNextCommand());
        }
    }
}
