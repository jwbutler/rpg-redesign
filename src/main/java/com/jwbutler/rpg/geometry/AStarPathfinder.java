package com.jwbutler.rpg.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import static com.jwbutler.rpg.geometry.GeometryUtils.hypotenuse;
import static com.jwbutler.rpg.geometry.GeometryUtils.manhattanDistance;

/**
 * Ported from rpg-js
 */
public final class AStarPathfinder implements Pathfinder
{
    /**
     * @param estimatedCost Exact cost from start => node + heuristic cost from node => goal
     */
    private record Node
    (
        int x,
        int y,
        double cost,
        double estimatedCost,
        @CheckForNull Node parent
    )
    {
        @Nonnull
        Coordinates getCoordinates()
        {
            return new Coordinates(x(), y());
        }

        double totalCost()
        {
            return cost() + estimatedCost();
        }
    }

    /**
     * @param candidates Expected to include {@code start} and {@code end}
     */
    @CheckForNull
    @Override
    public List<Coordinates> findPath(
        @Nonnull Coordinates start,
        @Nonnull Coordinates end,
        @Nonnull Set<Coordinates> candidates
    )
    {
        var open = new PriorityQueue<>(Comparator.comparing(Node::totalCost));
        var startNode = new Node(
            start.x(),
            start.y(),
            0,
            _getHeuristicCost(start, end),
            null
        );
        open.add(startNode);

        var closed = new HashSet<Coordinates>();
        while (true)
        {
            var current = open.poll();
            if (current == null)
            {
                return null;
            }
            else if (current.getCoordinates().equals(end))
            {
                return traverseParents(current);
            }

            closed.add(current.getCoordinates());

            for (var neighbor : _findNeighbors(current.getCoordinates(), candidates))
            {
                if (!closed.contains(neighbor))
                {
                    var cost = current.cost() + manhattanDistance(current.getCoordinates(), neighbor);
                    var estimatedCost = cost + _getHeuristicCost(neighbor, end);
                    var existing = open.stream()
                        .filter(node -> node.getCoordinates().equals(neighbor))
                        .findFirst()
                        .orElse(null);

                    if (existing != null && existing.estimatedCost() < estimatedCost)
                    {
                        // leave it
                    }
                    else
                    {
                        if (existing != null)
                        {
                            open.remove(existing);
                        }
                        open.add(new Node(
                            neighbor.x(),
                            neighbor.y(),
                            cost,
                            estimatedCost,
                            current
                        ));
                    }
                }
            }
        }
    }

    private static double _getHeuristicCost(@Nonnull Coordinates start, @Nonnull Coordinates end)
    {
        return hypotenuse(start, end);
    }

    @Nonnull
    private static List<Coordinates> traverseParents(@Nonnull Node node)
    {
        var path = new ArrayList<Coordinates>();
        for (var currentNode = node; currentNode != null; currentNode = currentNode.parent())
        {
            path.add(0, new Coordinates(currentNode.x(), currentNode.y()));
        }
        return path;
    }

    @Nonnull
    private static Set<Coordinates> _findNeighbors(@Nonnull Coordinates current, @Nonnull Set<Coordinates> candidates)
    {
        return Arrays.stream(Direction.values())
            .map(current::plus)
            .filter(candidates::contains)
            .collect(Collectors.toSet());
    }
}
