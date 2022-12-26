package com.jwbutler.rpg.geometry;

import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public final class PathfinderTest
{
    @Test
    public void testFindPath()
    {
        var pathfinder = new AStarPathfinder();
        var start = new Coordinates(0, 0);
        var end = new Coordinates(2, 0);
        var candidates = Set.of(
            new Coordinates(0, 0),
            new Coordinates(0, 1),
            new Coordinates(1, 1),
            new Coordinates(2, 1),
            new Coordinates(2, 0)
        );
        var path = pathfinder.findPath(start, end, candidates);
        var expectedPath = List.of(
            new Coordinates(0, 0),
            new Coordinates(0, 1),
            new Coordinates(1, 1),
            new Coordinates(2, 1),
            new Coordinates(2, 0)
        );
        assertEquals(path, expectedPath);
    }
}
