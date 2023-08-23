package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Activity;
import com.jwbutler.rpg.units.UnitFactory;
import com.jwbutler.rpg.units.UnitUtils;
import com.jwbutler.rpg.units.commands.DefendCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;
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
        var game = Game.create();
        var player = Player.create("test_player", Faction.PLAYER);
        game.addPlayer(player);
        var level = LevelFactory.TEST_LEVEL.get();
        game.addLevel(level);

        var unit = UnitFactory.createPlayerUnit(game, "test_unit", 10, player, level, Coordinates.zero());
        UnitUtils.addUnit(unit, game);

        assertEquals(unit.getActivity(), Activity.STANDING);
        assertEquals(unit.getDirection(), Direction.SE);
        assertEquals(unit.getFrameNumber(), 0);
        assertEquals(unit.getCommand(), new DefendCommand());
        assertNull(unit.getNextCommand());

        for (int i = 1; i <= 3; i++)
        {
            unit.update();

            assertEquals(unit.getActivity(), Activity.STANDING);
            assertEquals(unit.getDirection(), Direction.SE);
            assertEquals(unit.getFrameNumber(), 0);
            assertEquals(unit.getCommand(), new DefendCommand());
            assertNull(unit.getNextCommand());
        }

        var moveCommand = new MoveCommand(new Coordinates(0, 2));
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
