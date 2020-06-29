# SwordToOffer
Rush! Rush!

## 1、实现单例模式

实现单例模式的方法有很多，这里挑几种比较常见的：

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

## [2、二维数组的查找](https://www.nowcoder.com/practice/abc3fe2ce8e146608e868a70efebf62e?tpId=13&&tqId=11154&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

### 分析

该二维数组有如下规律：每一行右边的大于左边的，每一列下边的大于上边的，因此，此时可以看出右上角的数在它的那一行和那一列是处于中间的位置，因此如果所要查找的数比它大，那么这一行它左边的数就都被排除了，而如果比它小，则这一列的数都被排除。

### 代码

```java
public class Solution {
    public boolean Find(int target, int [][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) return false;
        int rows = 0;
        int colums = array[0].length - 1;
        while (rows < array.length && colums >= 0) {
            if (target == array[rows][colums]) return true;
            if (target > array[rows][colums])
                ++rows;
            else
                --colums;
        }
        return false;
    }
}
```

### [3、替换空格](https://www.nowcoder.com/practice/4060ac7e3e404ad1a894ef3e17650423?tpId=13&&tqId=11155&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。

### 分析

这个题目在 java 中可以直接通过 String 的 replaceAll 方法来实现，而主要考察的是指针的使用，通过两个指针来实现 O(n) 的解法。

### 代码

```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
    	if (str ==null || str.length() ==0) return "";
        return str.toString().replaceAll(" ", "%20");
    }
}
```

