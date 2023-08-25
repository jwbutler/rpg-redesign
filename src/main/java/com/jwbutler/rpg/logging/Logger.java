package com.jwbutler.rpg.logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        var time = LocalTime.now().format(DateTimeFormatter.ISO_TIME);
        System.out.printf("[%s] %s %s\n", level, time, message);
    }
    
    public enum Level
    {
        INFO,
        WARN,
        ERROR
    }
}
