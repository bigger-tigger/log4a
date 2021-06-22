# log4a
适配androidx，kotlin实现

## How to use?
1. 在 build.gradle 文件中添加依赖：
```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
	
dependencies {
    compile 'com.github.bigger-tigger:log4a:{last-version}'
}
```

2. 设置并初始化Log4a:  
```kotlin
fun init() {
  // 日志打印级别
  val logPrintLevel = Level.VERBOSE
  // 日志持久化级别
  val logStoreLevel = Level.WARN
  
  // 添加自定义拦截器
  val interceptor = object : Interceptor {
    override fun interceptor(logData: LogData) {
      logData.tag = "Log4a-" + logData.tag
    }
  }
  
  val androidAppender = AndroidAppender.Builder()
    .setLevel(logPrintLevel)
    .addInterceptor(interceptor)
    .create()
    
  val bufferPath = "" // buffer存储位置
  val logPath = "" // log存储位置
  val BUFFER_SIZE = 1024 * 400 // 400K阈值，超过400k就向log中写入
  val fileAppender = FileAppender.Builder(context)
    .setLogFilePath(logPath)
    .setLevel(logStoreLevel)
    .addInterceptor(interceptor)
    .setBufferFilePath(bufferPath)
    .setFormatter(DateFileFormatter())
    .setCompress(false)
    .setBufferSize(BUFFER_SIZE)
    .create()
    
  val logger = AppenderLogger.Builder()
    .append(androidAppender)
    .append(fileAppender)
    .create()
    
  Log4a.setLogger(logger)
}
```

3. 使用方式与 android.util.Log 完全一致：
```kotlin
Log4a.i(TAG, "Hello，Log4a!")
```

4. 选择在合适的时候刷新缓存**或者**释放内存  
```kotlin
//在应用退出的时候刷新缓存
Log4a.flush()
//如果想要释放内存可以调用下面的方法，内部会调用刷新，下次使用需要重新初始化
Log4a.release()
```
