package com.jwbutler.rpg.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.geometry.GeometryUtils.hypotenuse;

/**
 * Ported from rpg-js
 */
final class AStarPathfinder implements Pathfinder
{
    /**
     * @param cost Exact cost from start => node
     * @param estimatedCost Heuristic cost from node => goal
     */
    private record Node
    (
        int x,
        int y,
        double cost,
        double estimatedCost,
        @Nullable Node parent
    )
    {
        @NonNull
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
     * http://theory.stanford.edu/~amitp/GameProgramming/AStarComparison.html
     * http://theory.stanford.edu/~amitp/GameProgramming/ImplementationNotes.html#sketch
     */
    @Nullable
    @Override
    public List<Coordinates> findPath(
        @NonNull Coordinates start,
        @NonNull Coordinates end,
        @NonNull Set<Coordinates> candidates
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
                    var cost = current.cost() + 1;
                    var estimatedCost = _getHeuristicCost(neighbor, end);
                    var totalCost = cost + estimatedCost;
                    var existing = open.stream()
                        .filter(node -> node.getCoordinates().equals(neighbor))
                        .findFirst()
                        .orElse(null);

                    if (existing != null && existing.totalCost() < totalCost)
                    {
                        // leave it
                    }
                    else
                    {
                        if (existing != null)
                        {
                            open.remove(existing);
                        }
                        var node = new Node(
                            neighbor.x(),
                            neighbor.y(),
                            cost,
                            estimatedCost,
                            current
                        );
                        open.add(node);
                    }
                }
            }
        }
    }

    private static double _getHeuristicCost(@NonNull Coordinates start, @NonNull Coordinates end)
    {
        return hypotenuse(start, end);
    }

    @NonNull
    private static List<Coordinates> traverseParents(@NonNull Node node)
    {
        var path = new ArrayList<Coordinates>();
        for (var currentNode = node; currentNode != null; currentNode = currentNode.parent())
        {
            path.add(0, new Coordinates(currentNode.x(), currentNode.y()));
        }
        return path;
    }

    @NonNull
    private static Set<Coordinates> _findNeighbors(@NonNull Coordinates current, @NonNull Set<Coordinates> candidates)
    {
        return Arrays.stream(Direction.values())
            .map(current::plus)
            .filter(candidates::contains)
            .collect(Collectors.toSet());
    }
}
