## 实现 Singleton 模式
实现单例模式的方法有很多，这里挑几种比较常见的。
### 饿汉式
会在类加载的时候就进行初始化，如果不是很早就需要使用，则会造成浪费。

``` java
public class Singleton {
    private static final Singleton singletonHungry = new Singleton();
}
```
### 懒汉式
只在需要时才会进行初始化，避免资源的浪费，缺点是第一次加载时会比较耗时，每次获取都要同步。

``` java
private static Singleton singletonLazy;

    private static synchronized Singleton getSingletonLazy() {
        if (singletonLazy == null) {
            singletonLazy = new Singleton();
        }
   	return singletonLazy;
}
```
### DCL
Double Check Lock，优点：只在需要时初始化、线程安全、获取到对象后再次调用不进行同步锁，但在某些情况下还是会失效，关于 volatile 关键字可以参看[这篇文章](http://www.importnew.com/18126.html)。

``` java
private static volatile Singleton singletonDCL;

    private static Singleton getSingletonDCL() {
        if (singletonDCL == null) { //避免不必要的同步
            synchronized (Singleton.class) {
                if (singletonDCL == null) { //
                    singletonDCL = new Singleton();
            		}
        	  }
    	 }
    return singletonDCL;
}
```

### 静态内部类
具有懒加载的同时保证了唯一性，推荐使用。
``` java
public static Singleton getSingletonInner() {
   return SingletonHolder.singleton;
}

private static class SingletonHolder {
   private static Singleton singleton = new Singleton();
}
```