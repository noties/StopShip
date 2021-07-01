# StopShip

A convenience utility around Android's `STOPSHIP` functionality with build-in Lint support. 
Kotlin and Java supported.

```groovy
implementation 'io.noties:stopship:1.0.0'
```

Some code might be added for runtime debugging or testing purposes. And it must not be shipped.
Imagine mocked credentials, altered behaviour or fake data. These must be removed from the application
before a release.
 
Android Studio comes with a `STOPSHIP` comment and associated Lint rule that would stop build
if such comment is present. For this to work `STOPSHIP` comment Lint rule must be marked as fatal:

```groovy
// build.gradle
android {
  lintOptions {
    fatal 'StopShip'
  }
}
```

It is also crucial to create isolated code blocks that are easy to remove. And the removal 
must not break or alter expected behaviour. This can be done with a truthy `if` condition:

```java
// STOPSHIP: 01.07.2021 
if (true) {
  // the altering code
  doSomethingBad();

  // It is common to insert an altering behaviour
  //   before an actual implementation. So STOPSHIP code
  //   might return, so real code is not executed
  return
}

doSomethingReal();
```

This works well until the condition must be set to `false`, in order to temporarily 
disable the code block. `STOPSHIP` comment is still present, which breaks the build even though
the code is not executed. So, the `STOPSHIP` comment is removed. And next time the code is going to
be run (by using `true` in the if condition) the `STOPSHIP` comment must be added again. 
This might be a tedious process with a room for an error. Which can result in a bad _shipping_.

## Kotlin

Usage in Kotlin project:

```kotlin
import io.noties.stopship.stopShip

fun someMethod() {

  stopShip {
    doWhatYouHaveToDo()
    return  
  }

  // original method
}
```

In order to disable the StopShip lint rule, just add an underscore before `stopShip` =&gt; `_stopShip`:

```kotlin
import io.noties.stopship._stopShip

fun someMethod() {

  _stopShip {
    // won't be run and Lint won't report the usage
    doWhatYouHaveToDo()
    return  
  }

  // original method
}
```

## Java

In Java there is no way to create a code block with a return functionality as in Kotlin. So
the usage is a bit different (if an early return is required):

```java
// NB! the static import
import static io.noties.stopship.StopShip.stopShip;

void someMethod() {
  
  if (stopShip(() -> {
    doWhatYouHaveToDo();
  })) return;
  
  // original mettod
}
```

Disabling code block is done similarly with an underscore:

```java
import static io.noties.stopship.StopShip._stopShip;

void someMethod() {
  
  if (_stopShip(() -> {
    // won't be run and Lint won't report the usage
    doWhatYouHaveToDo();
  })) return;
  
  // original mettod
}
```

If there is no need for an early return, then code can be simplified to:

```java
stopShip(() -> {
  doWhatYouHaveToDo();
});
```

## Proguard / R8

The underscored variants can be safely removed with these rules:

```proguard
-assumenosideeffects class io.noties.stopship.StopShip {
    public static *** _stopShip(...);
}

-assumenosideeffects class io.noties.stopship.StopShipKt {
    public static *** _stopShip(...);
}
```

