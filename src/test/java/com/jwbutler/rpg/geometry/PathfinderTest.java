package com.jwbutler.rpg.geometry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jspecify.annotations.NonNull;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public final class PathfinderTest
{
    /**
     * # #
     * # #
     * .#.
     */
    @Test
    public void testFindPath()
    {
        var pathfinder = new AStarPathfinder();
        var start = new Coordinates(0, 0);
        var end = new Coordinates(2, 0);
        var candidates = Set.of(
            new Coordinates(0, 0),
            new Coordinates(0, 1),
            new Coordinates(0, 2),
            new Coordinates(1, 2),
            new Coordinates(2, 2),
            new Coordinates(2, 1),
            new Coordinates(2, 0)
        );
        var path = pathfinder.findPath(start, end, candidates);
        var expectedPath = List.of(
            new Coordinates(0, 0),
            new Coordinates(0, 1),
            new Coordinates(1, 2),
            new Coordinates(2, 1),
            new Coordinates(2, 0)
        );
        assertEquals(path, expectedPath);
    }

    @Test
    public void testSimpleDiagonalPath()
    {
        var pathfinder = new AStarPathfinder();
        var start = new Coordinates(0, 0);
        var end = new Coordinates(1, 1);
        var candidates = Set.of(start, new Coordinates(1, 0), end);
        var path = pathfinder.findPath(start, end, candidates);
        var expectedPath = List.of(start, end);
        assertEquals(path, expectedPath);
    }

    /**
     * #..
     * .#.
     * ..#
     */
    @Test
    public void testLengthTwoDiagonalPath()
    {
        var pathfinder = new AStarPathfinder();
        var start = new Coordinates(0, 0);
        var end = new Coordinates(2, 2);
        var candidates = Set.of(start, new Coordinates(1, 0), new Coordinates(1, 1), new Coordinates(1, 2), end);
        var path = pathfinder.findPath(start, end, candidates);
        var expectedPath = List.of(start, new Coordinates(1, 1), end);
        assertEquals(path, expectedPath);
    }

    /**
     * #...
     * .#..
     * ..#.
     * ...#
     */
    @Test
    public void testLengthThreeDiagonalPath()
    {
        var pathfinder = new AStarPathfinder();
        var start = new Coordinates(0, 0);
        var end = new Coordinates(3, 3);
        var candidates = _box(4, 4);
        var path = pathfinder.findPath(start, end, candidates);
        var expectedPath = List.of(start, new Coordinates(1, 1), new Coordinates(2, 2), end);
        assertEquals(path, expectedPath);
    }

    @NonNull
    private static Set<Coordinates> _box(int width, int height)
    {
        var set = new HashSet<Coordinates>();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                set.add(new Coordinates(x, y));
            }
        }
        return set;
    }
}
