package com.jwbutler.rpg.levels;

public enum TileType
{
    GRASS,
    DIRT,
    WALL;

    public final boolean isBlocking()
    {
        return switch (this)
        {
            case WALL -> true;
            default   -> false;
        };
    }
}
