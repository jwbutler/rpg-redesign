package com.jwbutler.rpg.sprites.animations;

import java.util.List;
import javax.annotation.Nonnull;

public interface Animation
{
    @Nonnull
    List<String> getFilenames();

    @Nonnull
    static Animation of(@Nonnull List<String> filenames)
    {
        return new Animation()
        {
            @Nonnull
            @Override
            public List<String> getFilenames()
            {
                return filenames;
            }
        };
    }

    @Nonnull
    static Animation of(@Nonnull String... filenames)
    {
        return new Animation()
        {
            @Nonnull
            @Override
            public List<String> getFilenames()
            {
                return List.of(filenames);
            }
        };
    }
}
