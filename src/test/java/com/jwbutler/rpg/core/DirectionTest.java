package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public final class DirectionTest
{
    @Test
    public void testNearestBetween()
    {
        assertEquals(
            Direction.nearestBetween(new Coordinates(0, 0), new Coordinates(0, 1)),
            Direction.S
        );
        assertEquals(
            Direction.nearestBetween(new Coordinates(0, 0), new Coordinates(0, 2)),
            Direction.S
        );
        assertEquals(
            Direction.nearestBetween(new Coordinates(0, 0), new Coordinates(1, 1)),
            Direction.SE
        );
        assertEquals(
            Direction.nearestBetween(new Coordinates(0, 0), new Coordinates(2, 2)),
            Direction.SE
        );
    }
}
