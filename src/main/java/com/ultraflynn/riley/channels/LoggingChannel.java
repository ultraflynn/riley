package com.ultraflynn.riley.channels;

import com.google.common.base.Optional;
import com.ultraflynn.riley.OutputChannel;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingChannel implements OutputChannel
{
  private final Logger logger;
  private final Level level;

  public static Builder builder()
  {
    return new Builder();
  }

  private LoggingChannel(Builder builder)
  {
    logger = builder.logger.get();
    level = builder.level;
  }

  public static final class Builder
  {
    private Optional<Logger> logger = Optional.absent();
    private Optional<String> name = Optional.absent();
    private Level level = Level.INFO;

    public Builder logger(Logger logger)
    {
      this.logger = Optional.of(logger);
      return this;
    }

    public Builder name(String name)
    {
      this.name = Optional.of(name);
      return this;
    }

    public Builder level(Level level)
    {
      this.level = level;
      return this;
    }

    public LoggingChannel build()
    {
      if (!logger.isPresent()) {
        if (name.isPresent()) {
          logger = Optional.of(Logger.getLogger(name.get()));
        } else {
          logger = Optional.of(Logger.getAnonymousLogger());
        }
      }
      return new LoggingChannel(this);
    }
  }

  @Override
  public void writeln(String content)
  {
    logger.log(level, content);
  }
}