package com.ultraflynn.riley.instrumentation;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ultraflynn.riley.Instrumentation;
import com.ultraflynn.riley.OutputChannel;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class ThreadSafeInstrumentation implements Instrumentation
{
  private static final Logger LOGGER = Logger.getLogger(ThreadSafeInstrumentation.class.getName());
  private static final long SHUTDOWN_TIMEOUT = 5;
  private final ExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  private final OptionalBlockingQueue<Entry> events = OptionalBlockingQueue.newQueue();
  private final Set<OutputChannel> outputChannels = Sets.newCopyOnWriteArraySet();
  private volatile boolean keepRunning = true;

  public static ThreadSafeInstrumentation newInstance()
  {
    return new ThreadSafeInstrumentation();
  }

  private ThreadSafeInstrumentation()
  {
    startConsumer();
    addShutdownHook();
  }

  private void startConsumer()
  {
    executor.submit(new Runnable()
    {
      @Override
      public void run()
      {
        while (keepRunning) {
          Optional<Entry> entry = events.dequeue();
          if (entry.isPresent()) {
            output(entry.get().timestamp + " " + entry.get().event);
          }
        }
      }
    });
  }

  private void output(String content)
  {
    for (OutputChannel channel : outputChannels) {
      channel.writeln(content);
    }
  }

  private void addShutdownHook()
  {
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        keepRunning = false;
        executor.shutdown();
        try {
          executor.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
          drainEventQueue();
        }
      }
    });
  }

  private void drainEventQueue()
  {
    LOGGER.info("Draining the event queue");
    executor.shutdownNow();
    Collection<? super Entry> remaining = Lists.newArrayList();
    events.drainTo(remaining);
    LOGGER.info(remaining.size() + " events remaining. Ignored.");
  }

  @Override
  public void addOutputChannel(OutputChannel channel)
  {
    outputChannels.add(channel);
  }

  @Override
  public boolean removeOutputChannel(OutputChannel channel)
  {
    return outputChannels.remove(channel);
  }

  @Override
  public void clearOutputChannels()
  {
    outputChannels.clear();
  }

  @Override
  public Instrumentation record(String event)
  {
    events.queue(new Entry(System.currentTimeMillis(), event));
    return this;
  }

  private static final class Entry
  {
    private final long timestamp;
    private final String event;

    private Entry(long timestamp, String event)
    {
      this.timestamp = timestamp;
      this.event = event;
    }

    @Override
    public String toString()
    {
      return "[" + timestamp + "] " + event;
    }
  }
}