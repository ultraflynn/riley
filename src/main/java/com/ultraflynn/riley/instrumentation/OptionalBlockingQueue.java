package com.ultraflynn.riley.instrumentation;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Queue;

public final class OptionalBlockingQueue<T>
{
  private final Queue<Optional<T>> queue = Lists.newLinkedList();

  public static <T> OptionalBlockingQueue<T> newQueue()
  {
    return new OptionalBlockingQueue<T>();
  }

  private OptionalBlockingQueue()
  {
  }

  public synchronized void queue(T item)
  {
    queue.add(Optional.of(item));
    notifyAll();
  }

  public synchronized Optional<T> dequeue()
  {
    while (queue.isEmpty()) {
      try {
        wait();
      } catch (InterruptedException e) {
        return Optional.absent();
      }
    }
    return queue.remove();
  }

  public synchronized void drainTo(Collection<? super T> remaining)
  {
    for (Optional<T> item : queue) {
      remaining.add(item.get());
    }
  }
}