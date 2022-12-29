package com.jwbutler.rpg.sprites;

import javax.annotation.Nonnull;

import static com.jwbutler.rpg.graphics.ImageUtils.imageFileExists;

public final class SpriteUtils
{
    public static final String BEHIND_SUFFIX = "_B";

    private SpriteUtils() {}

    @Nonnull
    public static String substituteBehindFilename(@Nonnull String filename)
    {
        String behindFilename = filename + BEHIND_SUFFIX;
        if (imageFileExists(behindFilename))
        {
            return behindFilename;
        }
        return filename;
    }
}
