package com.jwbutler.rpg.logging;

import org.jspecify.annotations.NonNull;

public final class Logger
{
    private Logger() {}

    public static void log(@NonNull String message)
    {
        log(message, Level.INFO);
    }

    public static void log(@NonNull String message, @NonNull Level level)
    {
        System.out.printf("[%s] %s\n", level, message);
    }
    
    public enum Level
    {
        INFO,
        WARN,
        ERROR
    }
}
