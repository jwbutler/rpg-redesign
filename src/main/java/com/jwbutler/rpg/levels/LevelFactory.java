package com.jwbutler.rpg.levels;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Dimensions;

public final class LevelFactory
{
    private LevelFactory() {}

    public static final Supplier<Level> LEVEL_ONE = () -> new LevelBuilder()
        .name("level_one")
        .dimensions(new Dimensions(40, 40))
        .addTiles(_tileBox(new Dimensions(40, 40), TileType.GRASS))
        .build();

    public static final Supplier<Level> TEST_LEVEL = () -> new LevelBuilder()
        .name("test_level")
        .dimensions(new Dimensions(10, 10))
        .addTiles(_tileBox(new Dimensions(10, 10), TileType.GRASS))
        .build();

    @NonNull
    private static Map<Coordinates, TileType> _tileBox(@NonNull Dimensions dimensions, @NonNull TileType tileType)
    {
        Map<Coordinates, TileType> map = new HashMap<>();
        for (int y = 0; y < dimensions.height(); y++)
        {
            for (int x = 0; x < dimensions.width(); x++)
            {
                map.put(new Coordinates(x, y), tileType);
            }
        }
        return map;
    }
}
