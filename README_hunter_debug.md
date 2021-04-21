# Hunter-Debug

This is a fork of the beautiful [hunter library](https://github.com/Leaking/Hunter).

Main differences between this fork and the original library are:

 * There's no @HunterDebugImpl annotation (You can just provide your own custom logger for the @HunterDebug annotation).
 * The @HunterDebug and @HunterDebugClass annotations support the following parameters:
    * debugResult[true]: By default this plugin will print both the method call and the method output (with the costed execution time). If you don't want to print for some methods their output, then set this property to false
    * logLevel[Log.INFO]: Log priority used for logging
 * The HunterLoggerHandler class has 2 separate methods to log the method call and its result. Thus, when you install your custom logger you can customize both of them:
    * protected void logEnter(int priority, String tag, String methodName, String params)
    * protected void logExit(int priority, String tag, String methodName, long costedMillis, String result)
 * The method `HunterLoggerHandler.installLogImpl` has been renamed to `HunterLoggerHandler.installLog`

That's pretty much it.



Hunter-debug is a gradle plugin based on [Hunter](https://github.com/Leaking/Hunter), It's inspired by JakeWharton's [hugo](https://github.com/JakeWharton/hugo), But Hunter-debug
has some advantages over hugo.

|       | Hugo     | Hunter-Debug     |
| ---------- | :-----------:  | :-----------: |
| support kotlin     | no     | yes     |
| custom logger     | no     | yes     |
| object toString     | no     | yes     |
| compile speed     | normal     | fast     |



Hunter-Debug's developed with ASM instead of AspectJ so that it has a faster compile speed.

## How to use it

Add some lines to your build.gradle

```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-debug-library:0.9.6'
}

repositories {
    jcenter()
}

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-debug-plugin:1.1.0'
        classpath 'com.quinn.hunter:hunter-transform:1.1.0'
    }
}

apply plugin: 'hunter-debug'

```
Simply add @HunterDebug to your methods will print all parameters and costed time, return value.

For example:

```java


@HunterDebug
private String appendIntAndString(int a, String b) {
    SystemClock.sleep(100);
    return a + " " + b;
}

```

The **@HunterDebug** annotation supports the following options parameters:

 * `debugResult [bool]` if set to false the result output for the annotated method will be skipped
 * `logLevel [int]` Log.INFO by default, change the log level used to print the method in/out


```xml 

MainActivity: ⇢ appendIntAndString[a="5", b="billions"]
              ⇠ appendIntAndString[0ms]="5 billions"

```

If you want to print the debug log with your custom logger. You can use `@HunterDebugImpl` instead of `@HunterDebug`, and 
install a custom HunterLoggerHandler to receive the log message, and send it to your custom logger.
(You can use both `@HunterDebug` and `@HunterDebugImpl` at the same time)

```java 

HunterLoggerHandler.installLog(new HunterLoggerHandler() {
    @Override
    protected void logEnter(int priority, String tag, String methodName, String params) {
        Log.println(priority, tag, String.format("<IN> %s::%s(%s)", tag, methodName, params));
    }

    @Override
    protected void logExit(int priority, String tag, String methodName, long costedMillis, String result) {
        Log.println(priority, tag, String.format("<OUT> %s::%s(%sms) = %s", tag, methodName, costedMillis, result));
    }
});
        
```

Logging works in both debug and release build mode, but you can specify certain mode or disable it.

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}

``` 


## License

Original LICENSE can be seen here: [https://github.com/Leaking/Hunter#license](https://github.com/Leaking/Hunter#license)

    