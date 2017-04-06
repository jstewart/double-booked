# Double Booked

Given a sequence of events, each having a start and end time, write a program that will return the sequence of all pairs of overlapping events.

For the purpose of this exercise, the definition of an overlapping event is and event for which it's duration is in between the start and stop time of another event, including the cases where the edges overlap exactly.

For example, if event a has a start time of 8:00 and an end time of 8:30, and event b starts at 8:30 and ends at 9:30, a collision will be detected.

## Solution
To exercise the test suite, run `lein test`
