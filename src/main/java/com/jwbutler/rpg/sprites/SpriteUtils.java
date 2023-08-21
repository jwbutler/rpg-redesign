package com.jwbutler.rpg.sprites;

import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.graphics.ImageUtils.imageFileExists;

public final class SpriteUtils
{
    public static final String BEHIND_SUFFIX = "_B";

    private SpriteUtils() {}

    @NonNull
    public static String substituteBehindFilename(@NonNull String filename)
    {
        String behindFilename = filename + BEHIND_SUFFIX;
        if (imageFileExists(behindFilename))
        {
            return behindFilename;
        }
        return filename;
    }
}
