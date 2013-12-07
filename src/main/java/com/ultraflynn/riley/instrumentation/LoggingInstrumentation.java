package com.ultraflynn.riley.instrumentation;

import com.ultraflynn.riley.Instrumentation;
import com.ultraflynn.riley.OutputChannel;

import java.io.PrintStream;

public final class LoggingInstrumentation implements Instrumentation
{
  private final PrintStream stream;

  public LoggingInstrumentation(PrintStream stream)
  {
    this.stream = stream;
  }

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
  public Instrumentation record(String event)
  {
    stream.println(System.currentTimeMillis() + " " + event);
    return this;
  }
}