package com.ultraflynn.riley.channels;

import com.ultraflynn.riley.OutputChannel;

import java.io.PrintStream;

public final class PrintStreamChannel implements OutputChannel
{
  private final PrintStream printStream;

  public static PrintStreamChannel getInstance(PrintStream printStream)
  {
    return new PrintStreamChannel(printStream);
  }

  private PrintStreamChannel(PrintStream printStream)
  {
    this.printStream = printStream;
  }

  @Override
  public void writeln(String content)
  {
    printStream.println(content);
  }
}