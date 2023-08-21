package com.jwbutler.rpg.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * A simplified wrapper for {@link BlockingQueue} which only supports a single element at a time.
 */
public final class SingletonBlockingQueue<T>
{
    @NonNull
    private final BlockingQueue<T> queue;

    public SingletonBlockingQueue()
    {
        queue = new ArrayBlockingQueue<>(1);
    }

    public void offer(@NonNull T element)
    {
        // noinspection ResultOfMethodCallIgnored
        queue.offer(element);
    }

    @NonNull
    @Blocking
    public T take()
    {
        try
        {
            return queue.take();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public T poll()
    {
        return queue.poll();
    }
}
