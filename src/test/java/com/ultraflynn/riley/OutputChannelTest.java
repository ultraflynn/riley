package com.ultraflynn.riley;

import com.ultraflynn.riley.channels.LoggingChannel;
import com.ultraflynn.riley.channels.PrintStreamChannel;
import org.junit.Test;

public final class OutputChannelTest
{
  @Test
  public void getInstance_noConfiguredChannels() throws Exception
  {
    Instrumentation instrumentation = Riley.getInstance();
    runTicks(instrumentation);
  }
  @Test
  public void getInstance_printStreamChannel() throws Exception
  {
    Instrumentation instrumentation = Riley.getInstance(PrintStreamChannel.getInstance(System.out));
    runTicks(instrumentation);
  }

  @Test
  public void getInstance_loggingChannel() throws Exception
  {
    Instrumentation instrumentation = Riley.getInstance(LoggingChannel.builder().name("test").build());
    runTicks(instrumentation);
  }

  private void runTicks(Instrumentation instrumentation) throws InterruptedException
  {
    int n = 0;
    while (n++ < 120) {
      instrumentation.record("TICK");
      Thread.sleep(4);
    }
  }

  @Test
  public void loggingChannelConfiguration()
  {
    Riley.getInstance(LoggingChannel.builder().build());
  }
}