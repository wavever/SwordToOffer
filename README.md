## SwordToOffer

Rush! Rush!

[TOC]

### 1、实现单例模式

实现单例模式的方法有很多，这里挑几种比较常见的：

**饿汉式：**

会在类加载的时候就进行初始化，如果不是很早就需要使用，则会造成浪费。

``` java
public class Singleton {
    private static final Singleton singletonHungry = new Singleton();
}
```

**懒汉式：**

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

**DCL：**

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

**静态内部类:**

具有懒加载的同时保证了唯一性，推荐使用。

``` java
public static Singleton getSingletonInner() {
   return SingletonHolder.singleton;
}

private static class SingletonHolder {
   private static Singleton singleton = new Singleton();
}
```

### [2、二维数组的查找](https://www.nowcoder.com/practice/abc3fe2ce8e146608e868a70efebf62e?tpId=13&&tqId=11154&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

**分析：**

该二维数组有如下规律：每一行右边的大于左边的，每一列下边的大于上边的，因此，此时可以看出右上角的数在它的那一行和那一列是处于中间的位置，因此如果所要查找的数比它大，那么这一行它左边的数就都被排除了，而如果比它小，则这一列的数都被排除。

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

### [3、替换空格](https://www.nowcoder.com/practice/4060ac7e3e404ad1a894ef3e17650423?tpId=13&&tqId=11155&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-rankinghttps://www.nowcoder.com/practice/d0267f7f55b3412ba93bd35cfa8e8035?tpId=13&&tqId=11156&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。

**分析：**

这个题目在 java 中可以直接通过 String 的 replaceAll 方法来实现，而主要考察的是指针的使用，通过两个指针来实现 O(n) 的解法。

```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
    	if (str ==null || str.length() ==0) return "";
        return str.toString().replaceAll(" ", "%20");
    }
}
```

### [4、从尾到头打印链表](https://www.nowcoder.com/practice/d0267f7f55b3412ba93bd35cfa8e8035?tpId=13&&tqId=11156&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入一个链表的头节点，从尾到头反过来打印出每个节点的值。

**分析：**

如果直接将链表中节点的指针反转过来，然后依次输出，可以解决问题，但会破坏链表的结构，因此使用这种解法需要预先确定是否可以这样做。打印每个节点的值，需要遍历链表，而从尾到头打印，则是属于“后进先出”，可以通过栈来实现，而**递归**本质上就是栈结构，因此可以选择使用递归来解决。

```java
/**
*    public class ListNode {
*        int val;
*        ListNode next = null;
*
*        ListNode(int val) {
*            this.val = val;
*        }
*    }
*
*/
import java.util.ArrayList;
public class Solution {
    private ArrayList<Integer> list = new ArrayList();
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null) return list;     
        if(listNode != null) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.val);
        }
        return list;
    }
}
```

**考点：**

递归、循环、栈、链表

### [5、重建二叉树](https://www.nowcoder.com/practice/8a19cbe657394eeaac2f6ea9b0f6fcf6?tpId=13&&tqId=11157&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。

**分析：**

首先来看下二叉树的这三种遍历，其中前中后是根据访问根节点的顺序来定的：

- 前序遍历：先访问根节点，再访问左子节点，最后访问右子节点
- 中序遍历：先访问左子节点，再访问根节点，最后访问右子节点
- 后序遍历：先访问左子节点，再访问右子节点，最后访问根节点

前序遍历中，第一个数字总是树的根节点，而在中序遍历中，根节点的值是在中间，左边是左子树，右边是右子树，回到这个题目中，前序遍历的第一个数是1，因此根据中序遍历序列，可以确认左子树有三个数：{4,7,2}，右子树为{5,3,8,6}，所以该问题可以使用递归来实现：

```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {

public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        TreeNode root=reConstructBinaryTree(pre,0,pre.length-1,in,0,in.length-1);
        return root;
    }
    //前序遍历{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
    private TreeNode reConstructBinaryTree(int [] pre,int startPre,int endPre, int [] in,int startIn,int endIn) {
        if(startPre>endPre||startIn>endIn) return null;
        TreeNode root=new TreeNode(pre[startPre]);
        for(int i=startIn;i<=endIn;i++)
            if(in[i]==pre[startPre]){
                root.left=reConstructBinaryTree(pre,startPre+1,startPre+i-startIn,in,startIn,i-1);
                root.right=reConstructBinaryTree(pre,i-startIn+startPre+1,endPre,in,i+1,endIn);
                      break;
            }               
        return root;
    }
}
```

**考点：**

树、递归

### [6、用两个栈实现队列](https://www.nowcoder.com/practice/54275ddae22f475981afa2244dd448c6?tpId=13&&tqId=11158&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

用两个栈来实现一个队列，完成在队列尾部插入节点和在队列头部删除节点的功能，队列中的元素为int类型。

**分析：**

栈是属于“先进后出”，而队列是属于“先进先出”，用两个栈来实现“先进先出”，需要两个栈来配合，当添加元素时，先加入栈A，比如加入元素ab，此时来删除节点，则需要先删除a，那么就将栈A内的元素逐一都压入栈B，此时栈B内则是ba，弹出栈顶元素a，则完成了“先进先出”，因此，实现的关键就在于：插入元素时直接压入栈A，而删除元素时，如果栈B为空，则先将栈A的元素全部压入栈B，再做删除。

```java
public class Solution {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();
    
    public void push(int node) {
        stack1.push(node);
    }
    
    public int pop() {
        if (stack2.size() <= 0) {
            while(stack1.size() > 0){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
```

### [7、斐波那契数列](https://www.nowcoder.com/practice/c6c7742f5ba7442aada113136ddea0c3?tpId=13&&tqId=11160&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

写一个函数，输入 n，求斐波那契数列的第 n 项，斐波那契数列定义如下：

- n=0  0
- n=1  1
- n>1  f(n-1)+f(n-2)

**分析：**

首先，使用递归来求解很简单，但会有严重的效率问题，因为重复的计算太多，改进的方法则是避免重复的计算，对已经计算过的值进行保存，或是从下往上计算，根据f(0)和f(1)计算出f(2)，再根据f(1)和f(2)计算出f(3)，然后通过循环可以得到第n项，时间复杂度为 O(n)。

```java
public int Fibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int f1 = 0;
        int f2 = 1;
        int fn = 0;
        for (int i = 2; i <= n; i++) {
            fn = f1 + f2;//计算的当前值
            f1 = f2;//保存前2位的值，即 n-2
            f2 = fn;//保存前1位的值，即 n-1
        }
        return fn;
    }
```

### [8、青蛙跳台阶问题](https://www.nowcoder.com/practice/8c82a5b80378478f9484d87d1c5f12a4?tpId=13&&tqId=11161&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。

**分析：**

该问题就是斐波那契数列的实际应用，1级台阶有一种跳法，2级台阶则有两种跳法：一次跳2级，或是跳两次，每次跳1级。

```java
public int JumpFloor(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int f1 = 1;
        int f2 = 2;
        int fn = 0;
        for (int i = 3; i <= n; i++) {
            fn = f1 + f2;
            f1 = f2;
            f2 = fn;
        }
        return fn;
    }
```

