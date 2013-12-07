package com.ultraflynn.riley.instrumentation;

import com.ultraflynn.riley.Instrumentation;
import com.ultraflynn.riley.OutputChannel;

public final class NoopInstrumentation implements Instrumentation
{
  @Override
  public void addOutputChannel(OutputChannel outputChannel)
  {
  }

  @Override
  public boolean removeOutputChannel(OutputChannel channel)
  {
    return false;
  }

  @Override
  public void clearOutputChannels()
  {
  }

  @Override
  public Instrumentation record(String event)
  {
    return this;
  }
}