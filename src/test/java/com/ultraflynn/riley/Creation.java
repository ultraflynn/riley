package com.ultraflynn.riley;

import org.junit.Test;

public final class Creation
{
  @Test
  public void getInstance()
  {
    Instrumentation instrumentation = Riley.getInstance();
    instrumentation.record("test");
  }

  @Test
  public void createNewInstance()
  {
    Riley riley = new Riley();
    riley.record("test");
  }

  @Test
  public void staticMethod()
  {
    Riley.event("test");
  }
}