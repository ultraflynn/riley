package com.ultraflynn.riley;

public interface Instrumentation
{
  void addOutputChannel(OutputChannel channel);

  boolean removeOutputChannel(OutputChannel channel);

  void clearOutputChannels();

  Instrumentation record(String event);
}