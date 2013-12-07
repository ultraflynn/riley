package com.ultraflynn.riley;

import com.ultraflynn.riley.instrumentation.ThreadSafeInstrumentation;

public final class Riley implements Instrumentation
{
  private static class SingletonContainer
  {
    public static Instrumentation INSTANCE = ThreadSafeInstrumentation.newInstance();
  }

  public static Instrumentation getInstance()
  {
    return SingletonContainer.INSTANCE;
  }

  public static Instrumentation getInstance(OutputChannel... channels)
  {
    for (OutputChannel channel : channels) {
      SingletonContainer.INSTANCE.addOutputChannel(channel);
    }
    return SingletonContainer.INSTANCE;
  }

  @Override
  public void addOutputChannel(OutputChannel channel)
  {
    SingletonContainer.INSTANCE.addOutputChannel(channel);
  }

  @Override
  public boolean removeOutputChannel(OutputChannel channel)
  {
    return SingletonContainer.INSTANCE.removeOutputChannel(channel);
  }

  @Override
  public void clearOutputChannels()
  {
    SingletonContainer.INSTANCE.clearOutputChannels();
  }

  @Override
  public Instrumentation record(String event)
  {
    SingletonContainer.INSTANCE.record(event);
    return this;
  }

  public static Instrumentation event(String event)
  {
    SingletonContainer.INSTANCE.record(event);
    return SingletonContainer.INSTANCE;
  }
}