package com.jwbutler.rpg.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.annotation.Nonnull;

import com.google.common.util.concurrent.Uninterruptibles;

/**
 * A simplified wrapper for {@link BlockingQueue} which only supports a single element at a time.
 */
public final class SingletonBlockingQueue<T>
{
    @Nonnull
    private final BlockingQueue<T> queue;

    public SingletonBlockingQueue()
    {
        queue = new ArrayBlockingQueue<>(1);
    }

    public void offer(@Nonnull T element)
    {
        // noinspection ResultOfMethodCallIgnored
        queue.offer(element);
    }

    @Nonnull
    @Blocking
    public T take()
    {
        return Uninterruptibles.takeUninterruptibly(queue);
    }
}
