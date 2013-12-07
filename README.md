# riley
Riley is a statistics gathering and reporting library for Java, designed to be high performance
and thread-safe. It provides an API which allows applications to report events and configure how
often and the format of Rileys output.

## Purpose
Performance measurement and monitoring is quite often an afterthought when developing an
application but once deployed to production the same questions get asked time and again. Knowing
exactly how fast it's taking to operate is vital to knowing where to devote time and effort into
increasing throughput and scalability.

## Terminology

"Event" == Something that happens.

## Reporting

### Totals
The first and most basic measure is a simple total of how many times an operation has occured
since the application was started. It's important that Riley knows the lifecycle of the
application.

### Lifecycle
This relates to the Totals because a common question is "How many times an has occured per
hour (or minute, or day, or any time period)?". Riley knows when the application was started
and it knows the current time thus is can output average number of events over these time
periods.
