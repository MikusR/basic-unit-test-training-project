# Task 8

## 1. Untested branch in AnimalCsvParser.java

```java
            if (header == null) {
                return new ParseResult(animals, skipped);
            }
```

While in current implementation this check is redundant. Testing empty file handling is necessary.

## 2. Untested branches in AnimalSorter.java

Only one of sorting methods is being tested for null and empty input.

## 3. Untested branches in AnimalReportWriter.java

```java
 throw new RuntimeException("Failed to write report line", e);
```
These errors would occur after some data has already been successfully written. Probably hard to test for

## 4. Untested branches in Animal

These appear to be standard versions of those methods without significant changes. Testing is not necessary.
Changes to base `Animal` class would probably break other tests and that would make errors visible.
If changes from default behavior is necessary then tests should be made.