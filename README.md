## SwordToOffer

Rush! Rush!

PDF ：https://pan.baidu.com/s/1g-JzhQBGcTpwphzrN5blFw 提取码：ysth 

[TOC]

### 概念

#### 代码质量

- 规范性：书写清晰、布局清晰、命名合理
- 完整性：完成基本功能、考虑边界条件、做好错误处理
- 鲁棒性：采取防御性编程、处理无效的输入

#### 回溯法

可以看作是蛮力法的升级，从解决问题的所有可能解法中选择出一个可行方案，非常适合由多个步骤组成，并非每个步骤都有多个选项的问题。

可以用树结构来形象的表示回溯法，在某一步时，该步骤可以看作是一个节点，该步骤的所有选择可以看作是该节点的子节点，因此如果一个叶节点的状态满足题目的约束条件，则可以看作是找到了一个解决方案，而从该节点到根节点的连线，则是该解决方案。而如果一个叶节点不满足条件，那么需要回溯到上一个节点再尝试其他选项，当所有选项都尝试过后，且都不满足时，则再次回溯到上一个节点，如果所有的节点都不满足，则该问题无解。

通常回溯法适合用递归来实现。

#### 动态规划

如果一个问题满足以下两种情况，那么久可以使用动态规划来解决：

- 求最优解，一般为最大值或最小值
- 该问题可以分解为若干个子问题，并且子问题之间还有重叠的更小的问题，分解后的每个子问题也存在最优解

由于子问题会在分解大问题的过程中重复出现，为了避免重复求解子问题，可以采取自下往上的顺序优先计算小问题的最优解并保存，再依次为基础解决大问题的最优解。

由于并不知道哪个是最优解，因此需要将所有的结果都计算出来并比较得到最优解。

#### 贪婪算法

一种在每一步选择中都采取在当前状态下最好或最优（即最有利）的选择，从而希望导致结果是最好或最优的算法。

贪心算法在有最优子结构的问题中尤为有效。最优子结构的意思是局部最优解能决定全局最优解。简单地说，问题能够分解成子问题来解决，子问题的最优解能递推到最终问题的最优解。

贪心算法与[动态规划](https://zh.wikipedia.org/wiki/动态规划)的不同在于它对每个子问题的解决方案都做出选择，不能回退。动态规划则会保存以前的运算结果，并根据以前的结果对当前进行选择，有回退功能。

一旦一个问题可以通过贪心法来解决，那么贪心法一般是解决这个问题的最好办法。由于贪心法的高效性以及其所求得的答案比较接近最优结果，贪心法也可以用作辅助算法或者直接解决一些要求结果不特别精确的问题。



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

[变态跳台阶](https://www.nowcoder.com/practice/22243d016f6b47f2a6928b4313c85387?tpId=13&&tqId=11162&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。

**分析：**

如果第一次跳 1 级，那么剩下的 n-1 级，跳法有 f(n-1) 种；如果第一次跳 2 级，那么剩下的 n-2 级，跳法有 f(n-2) 种；依次类对，如果第一次跳 n-1 级，则剩下的有 1 种跳法，因此总跳法为: f(n) = 1+f(n-1) + f(n-2)+....+f(1)  （第一个1是跳n阶只有一种方法），一阶的时候 f(1) = 1 ；有两阶的时候可以有 f(2) =1+f(1)=2；有三阶的时候可以有 f(3) = 1+f(2)+f(1)=4...依次内推，有n阶时f(n)=2^(n-1)。       

```java
public class Solution {
    public int JumpFloorII(int target) {
        return 1 << (target - 1);
    }
}
```

[矩形覆盖](https://www.nowcoder.com/practice/72a5a919508a4251859fb2cfb987a0e6?tpId=13&&tqId=11163&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

我们可以用2x1的小矩形（如左下图中的矩形，宽高比为1:2）横着或者竖着去覆盖更大的矩形。请问用n个2x1的小矩形无重叠地覆盖一个2xn的大矩形，总共有多少种方法？比如n=3时，2x3的矩形块有3种覆盖方法：

![](https://uploadfiles.nowcoder.com/images/20200218/6384065_1581999858239_64E40A35BE277D7E7C87D4DCF588BE84)

**分析：**

- n=1，形状等于小矩形，有1种方法；
- n=2时，都横放或者都竖放，有2种方法；
- n=3时，如上图所示，有3种方法，但可以看出，其摆放方法为n为2的数量加n为1的数量

因此该问题还是斐波那契数列，解法与青蛙跳台阶一致。

### [9、旋转数组的最小数字](https://www.nowcoder.com/practice/9f3231a991af4f55b95579b44b7a01ba?tpId=13&&tqId=11159&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。

**分析：**

首先需要了解到，该题目可以直接通过遍历数组来找出最小元素，但这样没有利用到该数组为递增排序的特性，并且时间复杂度为 O(n)。

以{3,4,5,1,2}为例，可以看到旋转过后，数组其实是分为了两部分，{3,4,5}和{1,2}，并且前边数组的元素都是要大于后边数组的，**并且最小的元素就是这两个数组的分界线，是后一个数组的首个元素。**因此该问题可以通过二分法实现O(logn)复杂度的解法，用两个指针来分别指向数组的第一个和最后一个元素，接着找到数组的中间元素，如果该元素位于第一个数组，则它应该是>=第一个指针指向的元素的，此时，最小元素应该位于该中间元素的后边，因此移动第一个指针到该中间元素。

而如果该中间元素位于后面的数组，则它的应该是<=第二个指针指向的元素，此时最小元素应该位于中间元素的前边，因此移动第二个指针到该中间元素。

按照上边的思路，第一个指针总是指向第一个数组，第二个指针总是指向第二个数组，当他们指向相邻的元素时，第二个指针指向的就是最小的元素。

需要注意两个额外的情况：

- 当把数组的前0个元素移动到后面时的情况，即数组本身也是一个旋转数组，此时第一个数字就是最小的数字，这里需要在初始化中间元素index时，将其初始化为0的原因，如果此时`array[index1] >= array[index2]` 不满足则直接返回第一个元素。
- index1、index2 和 indexMid 指向的元素相同的情况，例如数组 {0,1,1,1,1}的旋转数组{1,0,1,1,1} 和 {1,1,1,0,1}，这两个数组中，index1、index2 和 indexMid 都为 1，但是在第一个数组中 indexMid 数组后边的子数组，而第二个数组数组中则属于第一个子数组，**这种情况下，只能通过顺序查找。**

```java
public class Solution {
    public int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0) return 0;
        int index1 = 0;
        int index2 = array.length - 1;
        int indexMid = index1;
        while (array[index1] >= array[index2]) {
            if (index2 - index1 == 1) {
                indexMid = index2;
                break;   
            }
            indexMid = (index1 + index2) / 2; 
            //如果第一个指针、中间元素、第二个指针，三个值都相等，则使用顺序查找
            if (array[index1] == array[indexMid] && array[index2] == array[indexMid]) {
                return minInOrder(array, index1, index2);
            }
            if (array[index1] <= array[indexMid]) {
                //移动第一个指针到中间元素
                index1 = indexMid;
            } else if (array[index2] >= array[indexMid]) {
                //移动第二个指针到中间元素
                index2 = indexMid;
            }
        }
        return array[indexMid];
    }
    
    public int minInOrder(int [] array, int index1, int index2){
        int result = array[index1];
        for (int i = index1 + 1; i <= index2; i++) {
            if (result > array[i]) continue;
            result = array[i];
        }
        return result;
    }
}
```



**考点：**

二分查找

### [10、矩阵中的路径](https://www.nowcoder.com/practice/c61c6999eecb4b8f88a98f66b273a3cc?tpId=13&&tqId=11218&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。例如下面的 3x4的矩阵中包含一条字符串 "bfce" 的路径(路径中字母已加粗)。但矩阵中不包含字符串"abfb" 的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。

a	**b**	t 	g

c	**f**	 **c**	 s

j 	d	**e**	 h

**分析：**

该问题是一个典型的可以使用回溯法解决的题目，先在矩阵中选中一个格子作为起点，如果该格子中的字符与所要查找路径上对应位数的字符一致（例如，起点的话，则与路径上的第一个字符一致），则在其上下左右查找下一个字符，如果没有找到，则返回到前一个字符重新查找，该过程即是可以递归的，对于与下面代码中的 `hasPathCore()` 方法，而 `hasPath()` 的作用就是选择路径起点，这里时通过遍历所有的格子。

```java
public class Solution {
    private int mPathLength;

    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || matrix.length == 0 || rows <= 0 || cols <= 0
                || str == null || str.length == 0) {
            return false;
        }
        //需要与矩阵大小一致的布尔数组来保存是否已经进入
        boolean[] visited = new boolean[rows * cols];
        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col <= cols; col++) {
                if (hasPathCore(matrix, rows, cols, str, row, col, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPathCore(char[] matrix, int rows, int cols, char[] str,
                                int row, int col, boolean[] visited) {
        boolean hasNextCharPath = false;
        //必须首先判断是否已经查找完毕，否则会有越界的异常
        if (mPathLength == str.length) return true;
        if (row >= 0 && row < rows && col >= 0 && col < cols
                && matrix[row * cols + col] == str[mPathLength]
                && !visited[row * cols + col]) {
            mPathLength++;
            visited[row * cols + col] = true;
            hasNextCharPath = hasPathCore(matrix, rows, cols, str, row - 1, col, visited)
                    || hasPathCore(matrix, rows, cols, str, row, col - 1, visited)
                    || hasPathCore(matrix, rows, cols, str, row + 1, col, visited)
                    || hasPathCore(matrix, rows, cols, str, row, col + 1, visited);
            if (!hasNextCharPath) {
                mPathLength--;
                visited[row * cols + col] = false;
            }
        }
        return hasNextCharPath;
    }
}
```

**考点：**

dfs、回溯

### [11、机器人的运动范围](https://www.nowcoder.com/practice/6e5207314b5241fb83f2329e89fdecc8?tpId=13&&tqId=11219&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？

**分析：**

这个问题考察的依旧是回溯法，问题问的是机器人能够达到多少个格子并且是从坐标 (0, 0) 开始的，因此，可以从  (0, 0) 开始从这个点的上下左右开始递归查找，如果满足条件则加1，并且继续递归下一个的上下左右，如果不满足，则返回到这个点为止的格子个数，最后加起来的即是能够达到的。

```java
public class RobotSport {

    public int movingCount(int threshold, int rows, int cols) {
        if (threshold <= 0 || rows <= 0 || cols <= 0) return 0;
        boolean[] visited = new boolean[rows * cols];
        return movingCountCore(threshold, rows, cols, 0, 0, visited);
    }

    private int movingCountCore(int threshold, int rows, int cols, int startRow,
                                int startCol, boolean[] visited) {
        int count = 0;
        if (startRow >= 0 && startRow < rows && startCol >= 0 && startCol < cols
                && getDigitSum(startRow) + getDigitSum(startCol) <= threshold
                && !visited[startRow * cols + startCol]) {
            visited[startRow * cols + startCol] = true;
            count = 1 + movingCountCore(threshold, rows, cols, startRow - 1, startCol, visited)
                    + movingCountCore(threshold, rows, cols, startRow, startCol - 1, visited)
                    + movingCountCore(threshold, rows, cols, startRow + 1, startCol, visited)
                    + movingCountCore(threshold, rows, cols, startRow, startCol + 1, visited);
        }
        return count;
    }

    /**
     * num=1348, %10=8, /10=134
     * num=134, %10=4, /10=13
     * num=13, %10=3, /10=1
     * num=1, %10=1, /10=0
     */
    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
```

### [12、剪绳子](https://www.nowcoder.com/practice/57d85990ba5b440ab888fc72b0751bf8?tpId=13&&tqId=33257&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1，m<=n），每段绳子的长度记为k[1],...,k[m]。请问k[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

**分析：**

该问题目的是求出剪出的各段绳子长度的乘积最大值，也就是求最优解，因此可以可以使用**动态规划**来求解。

在剪第一刀的时候，有 n-1种可能的选择，即剪出来的可能是 1,2,3..n-1，因此最大乘积 f(n) = max(f(i) * f(n - i))

由于该问题已经在题目中表明 n 是大于 1 的，因此不用对这种情况做处理，这里需要用一个长度为 tartget + 1 的数组来保存每一个长度的最大乘积，之所以使用  tartget + 1 是为了便于理解，不从 0 开始，避免复杂的各种 -1，而是直接使用与长度对应的值来从数组中获取即可。

该种解法，空间复杂度为 O(n)，时间复杂度因为需要进行两次遍历，因此为 O(n^2)。

```java
public class Solution {
    public int cutRope(int target) {
        if (target == 2) return 1;
        if (target == 3) return 2;
        int[] maxResults = new int[target + 1]; // 存储长度从 0 到 target 的最大乘积
        maxResults[0] = 0; // 长度为 0 的最大乘积
        maxResults[1] = 1; // 长度为 1 的最大乘积
        maxResults[2] = 2; // 长度为 2 的最大乘积
        maxResults[3] = 3; // 长度为 3 的最大乘积
        int max = maxResults[target];
        for (int i = 4; i <= target; i++) { // 遍历每个长度
            for (int j = 1; j <= i / 2; j++) { // 对每个长度中可剪的所有情况求最优解
                int result = maxResults[j] * maxResults[i - j];
                if (max < result) {
                    max = result;
                }
                maxResults[i] = max; // 将对应长度的最优解保存
            }
        }
        max = maxResults[target];
        return max;
    }
}
```

该问题还可以使用**贪婪算法**来实现，当 n >= 5 的时候，尽可能多的去剪长度为 3 的绳子，而当剩下的绳子为 4 时，可以选择不剪，因为此时 2 x 2 也是 4，那么为什么要选择 3 呢，因为当 n >= 5 的时候

 3 * n -3 > n  >=  2 * n - 2 > n。

```java
    public int cutRope (int target) {
        if (target == 2) return 1;
        if (target == 3) return 2;
        int countOf3 = target / 3;
        // 分别对取余后的结果做处理
        if (target % 3 == 0) {
            return (int)(Math.pow(3, countOf3));
        } else if (target % 3 == 1) {
            return (int)(Math.pow(3, --countOf3)) * 4;
        } else {
            return (int)(Math.pow(3, countOf3)) * 2;
        }
    }
```

### [13、二进制中 1 的个数](https://www.nowcoder.com/practice/8ee967e43c2c4ec193b040ea7fbb10b8?tpId=13&&tqId=11164&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入一个整数，输出该数32位二进制表示中1的个数。其中负数用补码表示。例如 9 的二进制位 1001，有 2 位是 1，因此输出为 2。

**分析：**

关于位运算，可以看[聊一聊位运算]([https://wavever.github.io/2019/03/16/%E8%BA%B2%E4%B8%8D%E5%BC%80%E7%9A%84%E4%BD%8D%E8%BF%90%E7%AE%97/](https://wavever.github.io/2019/03/16/躲不开的位运算/))，该题的思路首先就是 1 & 1 = 1，只需要将对应位置的每个数与1做与操作即可，但是如果只是将 n 每次右移，那么当 n 为负数时，则会出现右移后左边补 1 的情况，从而导致死循环。因此一个思路就是不右移，而是左移，但不是左移 n，而是左移 1，例如 1 左移后为 10 即十进制的2，再左移为 100，那么与操作后就可以计算出 1 的个数，这种算法循环次数等于二进制位数：

```java
    public int NumberOf1(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            // 如果不为 0，则说明还有 1
            if ((flag & n ) != 0) ++count;
            flag = flag << 1;
        }
        return count;
    }
```

**更高效的解法：**

可以发现如果一个数 n 的最右边为 1，那么 n & (n - 1) 就是将最右边的这个 1 变为 0，例如 1001 减去 1 后为 1000。

而如果最右边为 0，那么 n - 1 则是将最右边的一个 1 变为 0，而把它右边的 0 都变为 1，如 1100 减去 1 后变为 1011，此时  n & (n - 1) 也是将最右边的 1 变为 0。

因此可以得到更高效的写法，这种写法有多少个 1 就遍历多少次：

```java
public int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            ++count;
            // n & n - 1 相当于把最右边的1变为0
            n &= (n - 1);
        }
        return count;
    }
```

### [14、数值的整数次方](https://www.nowcoder.com/practice/1a834e5e3e1a4b7ba251417554e07c00?tpId=13&&tqId=11165&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。

**分析：**

在数学上 0 的 0 次方没有意义，返回 0 或者 1 都是可以的。当 exponent 为负数时，要对求出的值取倒数，需要考虑除零错误。

当 exponent 为 偶数 16 时，可以看作是在 8 次方的基础上平方，而 8 次方又可以看作是在 4 次方的基础上平方，可以得到公式：a^n = a^(n/2) * a^(n/2)。

而当 exponent 为奇数 17 时，可以看作是在 16 的基础上再乘一次，因为可以得到公式：a^n = a^(n/2) * a^(n/2) * a。

```java
public class Solution {
    public double Power(double base, int exponent) {
        if (base == 0) return 0;
        int exponentTemp = exponent;
        if (exponent < 0) {
            exponentTemp = -exponent;
        }
        double result = getPowerResult(base, exponentTemp);
        if (exponent < 0) {
            result = 1 / result;
        }
        return result;
  }
    
    private double getPowerResult(double base, int exponent){
        if (exponent == 0) return 1;
        if (exponent == 1) return base;
        // 使用右移来实现除二操作
        double result = getPowerResult(base, exponent >> 1);
        result *= result;
        if ((exponent & 1) == 1) // 判断是否为奇数，比取余计算快
            result *= base;
        return result;
    }
}
```

### 15、打印从 1 到 n 最大的 n 位数

输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。

**分析：**

如果使用循环打印数字的方式，那么首先要将 n 转换为对应的数，但这里由于 n 的值不确定，因此无法保证是使用何种类型的变量来保存这个数，如果直接使用 int，可能会造成溢出。

基于以上考虑，该问题可以使用字符串来实现，首先需要考虑如何通过字符串来模拟数字的加法，二是将数字打印出来。

- 模拟数字加法：可以看到 n 位所有的十进制数就是从 0 到 9 排列一遍，因此可以用递归来实现
- 打印数字：不需要打印出前边的 0

```java
public class PrintN {

    public void print1ToN(int n) {
        if (n <= 0) return;
        char[] numChar = new char[n];
        for (int i = 0; i < 10; i++) {
            //例如n=3，默认从 000 开始
            numChar[0] = (char) (i + '0');
            //010-090，再递归则为001-009，此时 startIndex 为 2，等于 n -1,则开始打印
            print1ToNRecursively(numChar, 0);
        }
    }

    /**
     * 递归实现打印
     * @param numChar 打印的数组
     * @param startIndex 开始打印的 index
     */
    private void print1ToNRecursively(char[] numChar, int startIndex) {
        //当打印的位数等于长度时开始打印
        if (startIndex == numChar.length - 1) {
            printNumber(numChar);
            System.out.println();
            return;
        }
        //从 0-9 打印每个位数的值，例如 100,101...109
        for (int i = 0; i < 10; i++) {
            numChar[startIndex + 1] = (char) (i + '0');
            print1ToNRecursively(numChar, startIndex + 1);
        }
    }

    private void printNumber(char[] numChar) {
        if (numChar == null || numChar.length == 0) return;
        boolean isFirstZero = true; //用来过滤开头的0，例如 010，打印为 10 
        for (char c : numChar) {
            // 因为默认数组中都是空的，因此需要对比下是否 char 为 0
            if ((c == '0' && isFirstZero) || c == 0) continue;
            isFirstZero = false;
            System.out.print(c); //不采用换行的打印
        }
    }

    public static void main(String[] args) {
        new PrintN().print1ToN(3);
    }
}

```

### 16、删除链表的节点

#### 在 O(1) 时间内删除链表节点

给定单向链表的头指针和一个节点指针，定义一个函数在 O(1) 时间内删除该节点。

**分析：**

由于链表的特性，如果查找一个节点，则需要的时间复杂度为 O(n)，本题目需要的时间复杂度为 O(1)，那么可以考虑，将要删除节点的下一个节点的内容复制到删除节点处，然后删除下一个节点，那么就等于删除了节点，需要考虑两种情况，一是删除节点为尾节点，此时仍然是需要从头开始遍历，然后得到它的前序节点后完成删除操作；二是链表中只有一个节点，此时在删除节点后，还需要将链表的头节点置为 null。

**因为时间复杂度的要求，这里是直接认为要删除的节点就是在链表中。**

```java
    void delete(Node head, Node willDelete) {
        if (head == null || willDelete == null) return;
        if (willDelete.next != null) { //删除非尾节点
            willDelete.value = willDelete.next.value;
            willDelete.next = willDelete.next.next;
            willDelete.next.next = null;
        } else if (head == willDelete) { // 链表只有一个节点
            head = null;
        } else {//删除的是链表的尾节点
            Node node = head;
            while (node.next != willDelete) {
                node = node.next;
            }
            node.next = null;
        }
    }
```

#### [删除链表中重复的节点](https://www.nowcoder.com/practice/fc533c45b73a41b0b44ccba763f866ef?tpId=13&&tqId=11209&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表 1->2->3->3->4->4->5 处理后为 1->2->5。

**分析：**

该题目可能存在头节点就是重复节点的情况，判断是否为重复节点即从头开始遍历链表，如果一个节点的值和其下一个节点的值一致，那么就可以删除这两个节点，因此需要保存当前节点的前一个节点，并且要确保该前节点要和下一个没有重复的节点相连。

```java
public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        ListNode Head = new ListNode();
        Head.next = pHead;
        ListNode pre = Head;
        ListNode last = Head.next;
        while (last != null) {
            if (last.next != null && last.val == last.next.val) {
                // 找到最后的一个相同节点
                while (last.next != null && last.val == last.next.val) {
                    last = last.next;
                }
                pre.next = last.next;
                last = last.next;
            } else {
                pre = pre.next;
                last = last.next;
            }
        }
        return Head.next;
    }
```

### [17、正则表达式匹配](https://www.nowcoder.com/practice/45327ae22b7b413ea21df13ee7d6429c?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数用来匹配包括 . 和 * 的正则表达式。模式中的字符 '.' 表示任意一个字符，而 * 表示它前面的字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab* ac* a"匹配，但是与"aa.a"和"ab*a"均不匹配

**分析：**

 这里 * 是指前一个字符可以出现0次或多次，即 ab*，表示b可以出现0次或多次，需要注意数组越界的问题。

```java
public class _18_RegularMatch {

    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) return false;
        // 如果字符和表达式均为空数组，则表示匹配
        if (str.length == 0 && pattern.length == 0) return true;
        // 如果字符已经没有了，但是表达式还有，则不匹配
        if (str.length != 0 && pattern.length == 0) return false;
        return matchCore(str, pattern, 0, 0);
    }

    public boolean matchCore(char[] str, char[] pattern, int strIndex, int patternIndex) {
        //如果字符串和模式都检查完，则匹配成功
        if (str.length == strIndex && pattern.length == patternIndex) return true;
        //如果字符串还有，但模式已经没有，则匹配失败
        if (str.length != strIndex && pattern.length == patternIndex) return false;
        //如果模式的第2位为 *，则需要考虑多种情况
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
            //字符串字符和模式中*号前的字符相等，或者模式中*号前是.，即表示任意字符，此时有3种情况
            if (strIndex != str.length && (str[strIndex] == pattern[patternIndex]
                    || pattern[patternIndex] == '.')) {
                //1、*号匹配这个字符，则字符串移动一位，模式移动*号和其前一位共2位
                return matchCore(str, pattern, strIndex + 1, patternIndex + 2)
                        //2、*号可以匹配任意多字符，因此这里只移动字符串的下标，去检查字符串的下一个字符
                        || matchCore(str, pattern, strIndex + 1, patternIndex)
                        //直接跳过这个*号，和下边的逻辑一样
                        || matchCore(str, pattern, strIndex, patternIndex + 2);
            } else {
                //字符串字符与模式中*号前的字符不想等，此时直接将模式往后移动2位，
                //因为*号可以匹配0位，等于直接跳过了*和它前一位这个匹配
                return matchCore(str, pattern, strIndex, patternIndex + 2);
            }
        }
        // 字符如果相等，或者是表达式为.则该位匹配，去检查下一位
        if (strIndex != str.length && (str[strIndex] == pattern[patternIndex]
                || pattern[patternIndex] == '.')) {
            return matchCore(str, pattern, strIndex + 1, patternIndex + 1);
        }
        return false;
    }

    public static void main(String[] args) {
        char[] str = new char[]{'a', 'a', 'a'};
        char[] pattern = new char[]{'a', 'b', '.', 'a','c','*','a'};
        char[] a = new char[]{};
        char[] b = new char[]{'.'};
        boolean result = new _18_RegularMatch().match(a, b);
        System.out.println("result=" + result);
    }
}
```

### [18、表示数值的字符串]([https://www.nowcoder.com/practice/6f8c901d091949a5837e24bb82a731f2?tpId=13&rp=1&ru=%252Fta%252Fcoding-interviews&qru=%252Fta%252Fcoding-interviews%252Fquestion-ranking](https://www.nowcoder.com/practice/6f8c901d091949a5837e24bb82a731f2?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking))

请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

**分析：**

表示数字的字符串遵循模式 +-A.BeE+-C：

- A 表示整数部分，它的前边可以有+ 或 -，或者没有
- B 表示小数部分，跟在 . 的后边
- C 表示指数部分，跟在 e 或者 E 后边，其也可以有 + - 

```java
public class _19_StringNum {

    private int mIndex;

    public boolean isNumeric(char[] str) {
        if (str == null || str.length == 0) return false;
        // 先检查整数部分
        boolean result = scanInteger(str);
        // 检查小数部分
        if (mIndex < str.length && str[mIndex] == '.') {
            mIndex++;
            // 这里用或的原因：小数的结果对整数的结果并不影响：
            // 小数可以没有整数部分，如 .12 等于 0.12
            // 小数点后边可以没有数字，如 11. 等于 11.0
            result = scanUnsignedInteger(str) || result;
        }
        // 检查指数部分
        if (mIndex < str.length && (str[mIndex] == 'e' || str[mIndex] == 'E')) {
            mIndex++;
            // 这里用 && 的原因：e后边的结果和前边的结果必须都成立
            // 指数前边必须有数字，如 e12 不能表示数字
            // 指数后边必须有数字，如 12e 不能表示数字
            result = result && scanInteger(str);
        }
        // 最后要确保所有的字符都已经检查完毕，这里 mIndex 因为是检查完最后一位后还会++，因此和 length
        // 来比较
        return result && mIndex == str.length;
    }

    public boolean scanInteger(char[] str) {
        if (mIndex < str.length && (str[mIndex] == '+' || str[mIndex] == '-')) {
            mIndex++;
        }
        return scanUnsignedInteger(str);
    }

    public boolean scanUnsignedInteger(char[] str) {
        int indexCache = mIndex;
        while (mIndex < str.length && str[mIndex] >= '0' && str[mIndex] <= '9') {
            mIndex++;
        }
        return mIndex > indexCache;
    }

    public static void main(String[] args) {
        char[] str = {'1', 'a', '3', '.', '2', '3'}; //1a3.23 false
        char[] str1 = {'1', '1', '1'}; // 111 true
        char[] str2 = {'1', '1', '.', '4','e','+','9'}; // 11.4e+9 true
        boolean result = new _19_StringNum().isNumeric(str2);
        System.out.println("result=" + result);
    }
}
```

### [19、调整数组顺序使奇数位于偶数前边](https://www.nowcoder.com/practice/beb5aa231adc45b2a5dcc5b62c93f593?tpId=13&&tqId=11166&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

**分析：**

首先看下如何判断一个数为偶数，可以通过取余的方式来判断，也可以通过移位的方式，如下：

```java
public boolean isEven(int num) {
  // 偶数右移再左移后不变
  return (num >> 1) << 1 == num;
}
```

第一种方式，通过**空间换时间**的方式，使用一个临时数组，接着遍历两次整数数组，第一次遍历查找奇数，第二次遍历查找偶数，这样可以保证相对位置不变，空间复杂度和时间复杂度都为 O(n)：

```java
    public void reOrderArray(int [] array) {
      	if (array == null || array.length == 0) return;
        int[] tempArray = new int[array.length];
        int tempArrayIndex = 0;
        for (int i : array) {
            if (isEven(i)) continue;
            tempArray[tempArrayIndex] = i;
            tempArrayIndex++;
        }
        for (int i : array) {
            if (!isEven(i)) continue;
            tempArray[tempArrayIndex] = i;
            tempArrayIndex++;
        }
    }
```

第二种方式时可以采用类似于冒泡法来解决，使用两个指针分别指向数组的开头和结尾，头指针扫描到偶数，尾指针扫描奇数，当扫描到时交换这两个数，但需要考虑位置不变，因此需要多次遍历，时间复杂度为 O(n^2)，这里不做解释了。

### [20、链表中倒数第 k 个节点](https://www.nowcoder.com/practice/529d3ae5a407492994ad2a246518148a?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个链表，输出该链表中倒数第 k 个节点，这里从1开始奇计数，如链表：123456，倒数第3个节点为6。

**分析：**

只遍历一次链表就能找到倒数第 k 个节点，可以定义两个指针，第一个指针从链表的头指针开始遍历向前走 k 步，第二个指针保持不动，从第 k 步开始，第二个指针开始从头遍历，因为两个指针的距离保持在 k，因此当第一个指针到达尾节点时，第二个指针正好指向倒数第 k 个节点。

```java
public class _20_FindKthToTail {

    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k == 0) return null;
        ListNode aheadNode = head;
        ListNode beHeadNode = head;
        for (int i = 0; i < k - 1; i++) {
            if (aheadNode.next != null) {
                aheadNode = aheadNode.next;
            } else {
                // 如果k的值大于链表的长度，则返回空
                return null;
            }
        }
        while (aheadNode.next != null) {
            aheadNode = aheadNode.next;
            beHeadNode = beHeadNode.next;
        }
        return beHeadNode;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        System.out.println(new _20_FindKthToTail().FindKthToTail(node1, 2).val);
    }
}
```

### [21、链表中环的入口节点](https://www.nowcoder.com/practice/253d2c59ec3e4bc68da16833f79a38e4?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null，例如：

```
1 -> 2 -> 3 -> 4 -> 2
```

上面的链表中，2 指向 3，3 指向 4，而 4 又指向了 2 造成了环，则 2 就是环的入口节点。

**分析：**

第一步是如何判断一个链表包含环，可以通过两个指针来实现，一个指针走的快，一个走的慢，如果快的可以追上慢的，那么就包含环，否则不包含。

第二步是如何找到环的入口，依旧可以用两个指针来解决，假设环的长度为 n ，第一个指针先走 n 步，然后两个指针同时开始走，当两个指针相遇时，这个节点就是环的入口。

```java
public class _21_FindCircleStart {

    public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        ListNode meetingNode = getMeetingNode(pHead);
        if (meetingNode == null) return null;
        int loopLength = 1;
        ListNode loopNode = meetingNode;
        //获取环的长度
        while (loopNode.next != meetingNode){
            loopNode = loopNode.next;
            loopLength++;
        }
        System.out.println("circle length=" + loopLength);
        ListNode node1 = pHead;
        //指针1先走环的长度步
        for (int i = 0; i < loopLength; i++) {
            node1 = node1.next;
        }
        //指针1和指针2一起开始走，相遇的节点即为入口
        ListNode node2 = pHead;
        while (node1 != node2) {
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1;
    }

    //寻找环中任意一个节点
    public ListNode getMeetingNode (ListNode pHead){
        if (pHead == null) return null;
        ListNode slowNode = pHead.next;
        if (slowNode == null) return null;
        ListNode fastNode = slowNode.next;
        while (fastNode != null && slowNode != null) {
            //快指针和慢指针相遇，即该节点为环内节点
            if (fastNode == slowNode) {
                return fastNode;
            }
            slowNode = slowNode.next;
            fastNode = fastNode.next;
            if (fastNode != null) {
                fastNode = fastNode.next;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2;
        System.out.println(new _21_FindCircleStart().EntryNodeOfLoop(node1).val);
    }
}
```

### [22、反转链表](https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个链表，反转链表后，输出新链表的表头。

**分析：**

为了保证反转过程中链表不断开，需要用 3 个节点来分别保存当前节点，其前一个节点和其后一个节点的值。

```java
public class _22_RevertNode {

    public ListNode ReverseList(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode reverseNode = null;
        //用3个节点分别保存当前节点，它的前一个节点和它后一个节点的值
        ListNode node = head;
        ListNode nextNode = null;
        ListNode preNode = null;
        while (node != null) {
            nextNode = node.next;
            //尾节点的next为null，则为反转后的头节点
            if (nextNode == null) {
                reverseNode = node;
            }
            //反转节点
            node.next = preNode;
            preNode = node;
            node = nextNode;
        }
        return reverseNode;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        System.out.println(new _22_RevertNode().ReverseList(node1).val);
    }
}
```

### [23、合并两个排序的链表](https://www.nowcoder.com/practice/d8b6b4358f774294a89de2a6ac4d9337?tpId=13&&tqId=11169&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入两个递增的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。例如链表1位：135，链表2为：246，则合并后的链表为：123456。

**分析：**

 首先从头节点开始合并，比较两个链表的头节点，如上面的例子，这里链表1的头节点1小于链表2的头节点2，因此1作为合并链表后的头节点，接着就比较链表1第二个节点和链表2头节点，这个过程与之前一致，因此这个问题可以通过递归来完成。

```java
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode headNode;
        if (list1.val < list2.val) {
            headNode = list1;
            headNode.next = Merge(list1.next, list2);
        } else {
            headNode = list2;
            headNode.next = Merge(list1, list2.next);
        }
        return headNode;
    }
```

### [24、树的子结构](https://www.nowcoder.com/practice/6e196c44c7004d15b1610b9afca8bd88?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入两颗二叉树 A 和 B，判断 B 是不是 A 的子结构。

这里以下边的二叉树为例，左边的树A包含右边的树B。

```
 	 8						  8
	/ \						 / \
   8   7				    9   2 
  / \
 9   2				
 	/ \
   4   7
```

**分析：**

首先从树A的根节点开始遍历，根节点就是8，与树B的根节点相同，接着判断树A根节点的子树和树B根节点的子树是否相同，这里树A根节点的左子节点为8，而树B则为9，不相同。记着依旧遍历树A，判断方法则和之前一样，可以使用递归来实现，第一步是通过HasSubtree来遍历二叉树A，如果发现某个节点与B的头节点相同，则调用doesTree1HasTree2来判断是否A中以该节点为根节点的子树和树B具有相同的结构。

**二叉树中要时刻注意是否节点为空的情况，保证代码的健壮性。**

```java
class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}

public class _24_IfTree1hasTree2 {
    
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        boolean result = false;
        //如果有节点相同，则去递归判断是否包含
        if (root1.val == root2.val) {
            result = doesTree1HasTree2(root1, root2);
        }
        //如果节点相同但是不包含，则继续检查左子树是否包含
        if (!result) {
            result = HasSubtree(root1.left, root2);
        }
        //继续检查右子树是否包含
        if (!result) {
            result = HasSubtree(root1.right, root2);
        }
        return result;
    }

    public boolean doesTree1HasTree2(TreeNode root1, TreeNode root2) {
        //如果树2遍历完了，则说明包含
        if (root2 == null) return true;
        //如果树1遍历完了，树2还有叶子节点，则说明不包含
        if (root1 == null) return false;
        //值不同，不包含
        if (root1.val != root2.val) {
            return false;
        }
        //继续递归判断左子树和右子树
        return doesTree1HasTree2(root1.left, root2.left)
                && doesTree1HasTree2(root1.right, root2.right);
    }
}s
```

### [25、二叉树的镜像](https://www.nowcoder.com/practice/564f4c26aa584921bc75623e48ca3011?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请完成一个函数，输入一颗二叉树，该函数输出它的镜像，例如：

```
二叉树的镜像定义：源二叉树 
    	    8
    	   /  \
    	  6   10
    	 / \  / \
    	5  7 9 11
    	镜像二叉树
    	    8
    	   /  \
    	  10   6
    	 / \  / \
    	11 9 7   5
```

**分析：**

二叉树的镜像可以看作是左右子树的交换，可以通过递归来完成，前序遍历每个节点，如果遍历到的节点有子节点，则交换它们。

```java
public class Solution {
    public void Mirror(TreeNode root) {
        if (root == null) return;
        if (root.left == null && root.right == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
    }
}
```

### [26、对称的二叉树](https://www.nowcoder.com/practice/ff05d44dfdb04e1d83bdbdab320efbcb?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数，用来判断一棵二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。

**分析：**

 要判断一颗二叉树是否是镜像，即是判断前序遍历的结果，和从右节点开始的前序遍历结果是否相等。

```java
public class Solution {
    boolean isSymmetrical(TreeNode pRoot)
    {
        return isSymmetricalCore(pRoot, pRoot);
    }
    
    boolean isSymmetricalCore(TreeNode pRoot1, TreeNode pRoot2) {
        if (pRoot1 == null && pRoot2 == null) return true;
        if (pRoot1 == null || pRoot2 == null) return false;
        if (pRoot1.val != pRoot2.val) return false;
        // 比较两个二叉树的左右子树是否相等
        return isSymmetricalCore(pRoot1.left, pRoot2.right) 
            && isSymmetricalCore(pRoot1.right, pRoot2.left);
    }
}
```

### [27、顺时针打印矩阵](https://www.nowcoder.com/practice/9b4c81a02cd34f76be2659fa0d54342a?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵：

```
1  2  3  4
5  6  7  8
9  10 11 12
13 14 15 16
```

一次打印出来1，2，3，4，8，12，16，15，14，13，9，5，6，7，11，10。

**分析：**

 首先需要考虑循环打印的条件，可以发现 5x5 的矩阵最后一圈只有一个数字，对于的坐标为 (2,2)，这里 5>2x2，而对于 6x6 的矩阵而言，最后一圈有4个数字，其左上角坐标仍然为 (2,2)，6>2x2 依旧成立，因此循环的条件是 col > startX * 2 并且 row > startY * 2。

每次打印一圈的时候，可以分为4个步骤：从左到右，从上到下，从右到左，从下到上。这里需要考虑下特殊情况，例如只有一行、一列或是一个数字的情况。从左到右总是需要的，而当终止行号大于起始行号时，才需要从上到下；从右到左则需要至少有两行两列，即终止行号和列好都要大于起始行号和列号；从下到上则是需要至少三行两列，即终止行号比起始行号至少大于2，同时终止列号大于起始列号。

```java
public class Solution {
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        if (matrix == null ) return null;
        int rows = matrix.length;
        if (rows <= 0) return null;
        int columns = matrix[0].length;
        if (columns <= 0) return null;
        ArrayList<Integer> list = new ArrayList<>();
        int start = 0;
        while (rows > 2 * start && columns > 2 * start) {
            int endX = columns - 1 - start;
            int endY = rows - 1 - start;
            //从左到右打印
            for (int i = start; i <= endX; i++) {
                list.add(matrix[start][i]);
            }
            //必须终止行号大于起始行号，才会从上到下打印
            if (endY > start) { 
                for (int i = start + 1; i <= endY; i++) {
                    list.add(matrix[i][endX]);
                } 
            }
            //至少有两行两列，即终止行号和列好都要大于起始行号和列号，才会
            //从右到左打印
            if (endX > start && endY > start) {
                for (int i = endX -1; i >= start; i--) {
                    list.add(matrix[endY][i]);
                }
            }
            //需要至少三行两列，即终止行号比起始行号至少大于2，
            //才会从下到上打印
            if (endX > start && endY - 1> start) {
                for (int i = endY -1; i >= start + 1; i--) {
                   list.add(matrix[i][start]); 
                }
            }
            start++;
        }
        return list;
    }
}
```

### [28、包含 min 函数的栈](https://www.nowcoder.com/practice/4c776177d2c04c2494f2555c9fcc1e49?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的 min 函数。在该栈中，调用 min、push 及 pop 的时间复杂度应都为O(1)。

**分析：**

这个问题的意思是 min 函数的实现是使用栈内部的 push 和 pop 函数来实现。这里采用辅助空间的算法，通过一个辅助栈来保存每次 push 时的最小值，然后 min 函数中直接对这个弹出这个辅助栈元素即可。

```java
public class Solution {

    private Stack<Integer> mDataStack = new Stack();
    private Stack<Integer> mMinStack = new Stack();
    
    public void push(int node) {
        mDataStack.push(node);
        if (mMinStack.isEmpty() || min() > node) {
            // 此时辅助栈为空或者是顶部的值小于要push的值，更新
            mMinStack.push(node);
        } else {
            // 此时最小值并没有变，还是需要push，即每次push都需要有对应的最小值
            mMinStack.push(min());
        }
    }
    
    public void pop() {
        mDataStack.pop();
        mMinStack.pop();
    }
    
    public int min() {
        return mMinStack.peek();
    }
}
```

### [29、栈的压入、弹出序列](https://www.nowcoder.com/practice/d77d11405cc7470d82554cb392585106?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。

**分析：**

按 1,2,3,4,5 入栈顺序，1,2,3,4 先入栈，然后 4 出栈，接着5再入栈，接着就依次出栈，序列就是 4,5,3,2,1。而序列4,3,5,1,2，4要先出栈，则需要1234先入栈，接着43出栈，然后5入栈，5出栈，接着需要1出栈，但此时栈顶元素为2，然后在尚未压入栈的数字中搜索1，并没有1，因此这个不是。解决这个问题需要建立一个辅助栈，把输入的第一个序列中的数字依次压入辅助栈，并按照第二个序列的顺序依次从该栈中弹出数字。

因此规律就是：如果下一个弹出的数字刚好是栈顶数字，则直接弹出。如果不在栈顶，则把压栈序列中还没有入栈的数字压入辅助栈，直到把下一个需要弹出的数字压入栈为止。如果所有的数字都压入栈后还没有找到下一个弹出的数字，则该序列不是一个弹出序列。

```java
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null
                || pushA.length == 0 || popA.length == 0) {
            return false;
        }
        Stack<Integer> s = new Stack<Integer>();
        //用于标识弹出序列的位置
        int popIndex = 0;
        for (int i = 0; i < pushA.length; i++) {
            s.push(pushA[i]);
            //如果栈不为空，且栈顶元素等于弹出序列
            while (!s.empty() && s.peek() == popA[popIndex]) {
                //出栈
                s.pop();
                //弹出序列向后一位
                popIndex++;
            }
        }
        return s.empty();
    }
```

### 30、从上到下打印二叉树

```
 	8
   / \
  6  10
 / \ / \
5  7 9 11
```

#### [题目一：不分行从上到下打印二叉树](https://www.nowcoder.com/practice/7fe2212963db4790b57431d9ed259701?tpId=13&&tqId=11175&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

从上往下打印出二叉树的每个节点，同层节点从左至右打印，以上边的二叉树为例，打印结果为：8,6,10,5,7,9,11。

**考查：**

树的遍历、队列

**分析：**

通过分析发现在打印完8后，需要打印6和10，因此需要将6和10先进行保存，接着打印6时，需要保存5和7，此时保存的数就有10、5和7，然后打印10，可以发现时先入先出的一种数据结构即队列。因此规律就是每次打印节点时，如果有子节点则将字节点入队，然后继续打印队列头节点，重复该操作即可。

```java
public class Solution {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        //如果树为空，则返回空列表
        if (root == null) return result;
        //使用一个队列来保存树的节点
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode frontNode;
        while(!queue.isEmpty()) {
            frontNode = queue.pop();
            result.add(frontNode.val);
            //先保存左节点
            if (frontNode.left != null) {
                queue.offer(frontNode.left);
            }
            //再保存右节点
            if (frontNode.right != null) {
                queue.offer(frontNode.right);
            }
        }
        return result;
    }
}
```

#### [题目二：分行从上到下打印二叉树](https://www.nowcoder.com/practice/445c44d982d04483b04a54f298796288?tpId=13&&tqId=11213&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

从上往下打印出二叉树的每个节点，同层节点从左至右打印，以上边的二叉树为例，打印结果为：

8

10 6 

5 7 9 11

**分析：**

本题目与前一题目类似，为了使二叉树同层节点从左到右打印，需要两个变量：一个表示当前层还未打印节点的个数；另一个表示下一层节点的数目。

```java
public class Solution {
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> treeList = new ArrayList<>();
        if (pRoot == null) return treeList;
        //每一层需要打印的节点
        ArrayList<Integer> floorList = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        TreeNode frontNode;
        int waitPrintCount = 1; //本层等待打印的个数，默认为根节点1个
        int nextFloorCount = 0; //下一层需要打印节点的个数
        while(!queue.isEmpty()) {
             frontNode = queue.pop();
            //先保存左节点
            if (frontNode.left != null) {
                queue.offer(frontNode.left);
                nextFloorCount++;
            }
            //再保存右节点
            if (frontNode.right != null) {
                queue.offer(frontNode.right);
                nextFloorCount++;
            }
            // 这里由于需要在waitPrintCount为0后，将nextFloorCount的值
            //赋给waitPrintCount，因此需要先遍历节点再出队
            floorList.add(frontNode.val);
            waitPrintCount--;
            if (waitPrintCount == 0) {
                ArrayList<Integer> tempList = new ArrayList<>();
                tempList.addAll(floorList);
                treeList.add(tempList);
                floorList.clear();
                //这里需要去打印下一行了
                waitPrintCount = nextFloorCount;
                nextFloorCount = 0;
            }
        }
        return treeList;
    }
}
```

#### [题目三：之字形打印二叉树](https://www.nowcoder.com/practice/91b69814117f4e8097390d107d2efbe0?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推，以上边的二叉树为例，打印结果为：

8

10, 6

5, 7, 9, 11

**分析：**

需要使用两个辅助栈来分别保存奇数层和偶数层的节点。

```java
import java.util.ArrayList;
import java.util.Stack;

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) return result;
        //二叉树的层数，默认从1开始
        int floor = 1;
        //用于保存奇数层节点的栈
        Stack<TreeNode> oddFloorStack = new Stack<>();
        oddFloorStack.add(pRoot);
        //用于保存偶数层节点的栈
        Stack<TreeNode> evenFloorStack = new Stack<>();
        TreeNode printNode = null;
        while (!oddFloorStack.isEmpty() || !evenFloorStack.isEmpty()) {
            //保存每一层的节点值
            ArrayList<Integer> floorList = new ArrayList<>();
            if (floor % 2 == 1) { //奇数层，下一层为偶数层，是从右向左打印，因此是从左到右入栈，先保存左节点
                while (!oddFloorStack.isEmpty()) {
                    printNode = oddFloorStack.pop();
                    if (printNode.left != null) {
                        evenFloorStack.push(printNode.left);
                    }
                    if (printNode.right != null) {
                        evenFloorStack.push(printNode.right);
                    }
                    floorList.add(printNode.val);
                }
            } else {//偶数层，下一层为奇数层，是从左到右打印，因此是从右到左入栈，先保存右节点
                while (!evenFloorStack.isEmpty()) {
                    printNode = evenFloorStack.pop();
                    if (printNode.right != null) {
                        oddFloorStack.push(printNode.right);
                    }
                    if (printNode.left != null) {
                        oddFloorStack.push(printNode.left);
                    }
                    floorList.add(printNode.val);
                }
            }
            //一层遍历完成，将这层节点的值保存
            result.add(floorList);
            System.out.println();
            floor++;
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);
        TreeNode node1 = new TreeNode(6);
        TreeNode node2 = new TreeNode(10);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(7);
        TreeNode node5 = new TreeNode(9);
        TreeNode node6 = new TreeNode(11);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        new Solution().Print(root);
    }
}
```

### [31、二叉搜索树的后序遍历序列](https://www.nowcoder.com/practice/a861533d45854474ac791d90e447bafd?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则返回true,否则返回false。假设输入的数组的任意两个数字都互不相同。**左节点小于根节点，根节点小于右节点。**

```
 	  8
   / \
  6  10
 / \ / \
5  7 9 11
```

例如输入 [5, 7, 6, 9, 11, 10, 8] 就是上边二叉树的后序遍历，而数组 [7, 4, 6, 5] 不是。

**分析：**

后序遍历结果的最后一个数字就是根节点的值，而比它小的都是左子树节点，比它大的都是右子树。数组 [7, 4, 6, 5] 中，根节点的值为5，此时最前边的7比它大，因此根节点没有左子树，因此 [7, 4, 6] 都是右子树的值，但右子树中的值应该都比根节点大，而4比5小，因此[7, 4, 6, 5] 不是后序遍历序列。

```java
public class SwordToOffer31 {

    public boolean verifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return verifySquenceOfBSTCore(sequence, 0, sequence.length - 1);
    }

    private boolean verifySquenceOfBSTCore(int[] sequence, int start, int end) {
        //如果只有一个节点，那么就无需继续递归
        if (start == end) return true;
        int root = sequence[end];
        //右子树下标，默认要为-1，因为需要判断第一个就满足的情况
        int rightChildIndex = -1;
        //获取右子树起始的下标
        for (int i = start; i <= end; i++) {
            if (sequence[i] > root) {
                rightChildIndex = i; //1
                break;
            }
        }
        //没有右子树，继续遍历左子树，start不变，end为减去根节点，即end-1
        if (rightChildIndex == -1) {
            return verifySquenceOfBSTCore(sequence, start, end - 1);
        }
        //如果右子树中有比根节点小的值，则不存在
        for (int i = rightChildIndex; i < end; i++) {
            if (sequence[i] < root) return false;
        }
        //递归判断左子树是否满足
        boolean leftChildResult = true;
        if (rightChildIndex > start) {
            //start是左子树起点，end是右子树前一个
            leftChildResult = verifySquenceOfBSTCore(sequence, start, rightChildIndex - 1);
        }
        //递归判断右子树是否满足
        boolean rightChildResult = true;
        if (rightChildIndex < end) {
            //start是右子树起始节点，end根节点前一个，因此要-1
            rightChildResult = verifySquenceOfBSTCore(sequence, rightChildIndex, end - 1);
        }
        return leftChildResult && rightChildResult;
    }

    public static void main(String[] args) {
        boolean result1 = new SwordToOffer31().verifySquenceOfBST(new int[]{5, 7, 6, 9, 11, 10, 8});//true
        boolean result2 = new SwordToOffer31().verifySquenceOfBST(new int[]{4, 6, 7, 5});//true
        boolean result3 = new SwordToOffer31().verifySquenceOfBST(new int[]{7, 4, 6, 5});//false
        System.out.println("result=" + result3);
    }
}
```

### [32、二叉树中和为某一值的路径](https://www.nowcoder.com/practice/b736e784e3e34731af99065031301bca?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一颗二叉树的根节点和一个整数，按字典序打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。

```
	10
   /  \
  5   12
 / \ 
4   7 
```

以上边的树为例，当整数为22时，满足条件的路径有：[10, 5, 7] 和 [10, 12]。

**分析：**

这里通过二叉树前序遍历的方式来对树进行遍历，当检查完一条路径后则返回到上一个节点继续检查其另一个字节点的路径。

```java
public class Solution {
    private ArrayList<ArrayList<Integer>> mPathList = new ArrayList<>();
    private ArrayList<Integer> mTempList = new ArrayList<>();
    
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if (root == null) return mPathList;
        mPathList.clear();
        mTempList.clear();
        findPathCore(root, 0, target);
        return mPathList;
    }
    
    public void findPathCore(TreeNode root, int currentSum, int target) {
        currentSum += root.val;
        mTempList.add(root.val);
        //当前节点没有子节点，并且值满足条件
        if (currentSum == target && root.left == null && root.right == null) {
            ArrayList<Integer> pathList = new ArrayList<>(mTempList.size()); 
            pathList.addAll(mTempList);
            mPathList.add(pathList);
        }
        //遍历左节点
        if (root.left != null) {
            findPathCore(root.left, currentSum, target);
        }
        //遍历右节点
        if (root.right != null) {
            findPathCore(root.right, currentSum, target);
        }
        //删除这个节点的值，即最后一个值
        mTempList.remove(mTempList.size() - 1);
    }
}
```

### [33、复杂链表的复制](https://www.nowcoder.com/practice/f836b2c43afc4b35ad6adc41ec941dba?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random 指向一个随机节点），请对此链表进行深拷贝，并返回拷贝后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

![dB6fiR.png](https://s1.ax1x.com/2020/08/24/dB6fiR.png)

**分析：**

将复制过程分解为 3 个步骤，同时每个步骤用图形化的方式表示出来，有助于理清思路。

第一步：根据原生链表的每个节点 A 创建对应的 A'，并将 A' 链接在 A 的后面，经过这一步后链表的结构如下：

![dB64Rx.png](https://s1.ax1x.com/2020/08/24/dB64Rx.png)

第二步：给复制后的节点设置 random 指针。如图2所示，A 的 random 指向 C，那么 C' 就是 C 的 next 指针指向的节点，根据这个关系，可以对复制后的节点设置 random 指针，如下：

[![dBcSQf.png](https://s1.ax1x.com/2020/08/24/dBcSQf.png)](https://imgchr.com/i/dBcSQf)

第三步：将这个长链表拆分为两个链表，奇数位置的节点组成原始链表，偶数位置的链表组成复制后的链表。

```java
class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

public class Solution {
    public RandomListNode clone(RandomListNode pHead) {
        if (pHead == null) return null;
        //1、根据原生链表的每个节点 A 创建对应的 A'，并将 A' 链接在 A 的后面
        RandomListNode currentNode = pHead;
        while (currentNode != null) {
            RandomListNode cloneNode = new RandomListNode(currentNode.label);
            RandomListNode nextNode = currentNode.next;
            currentNode.next = cloneNode;
            cloneNode.next = nextNode;
            currentNode = nextNode;
        }
        //2、给复制后的节点设置 random 指针
        currentNode = pHead;
        while (currentNode != null) {
            //设置 A' 的 random
            currentNode.next.random = currentNode.random != null ? currentNode.random.next : null;
            //继续判断 B
            currentNode = currentNode.next.next;
        }
        //3、拆分链表
        currentNode = pHead;
        //默认赋值后的链表头为第二个节点
        RandomListNode cloneHead = pHead.next;
        while (currentNode != null) {
            RandomListNode cloneNode = currentNode.next;
            currentNode.next = cloneNode.next;
            //修改复制节点的next为下一个复制节点
            cloneNode.next = cloneNode.next != null ? cloneNode.next.next : null;
            currentNode = currentNode.next;
        }
        return cloneHead;
    }

    public static void main(String[] args) {
        RandomListNode a = new RandomListNode(1);
        RandomListNode b = new RandomListNode(2);
        RandomListNode c = new RandomListNode(3);
        RandomListNode d = new RandomListNode(4);
        RandomListNode e = new RandomListNode(5);
        a.next = b;
        a.random = c;
        b.next = c;
        b.random = e;
        c.next = d;
        d.next = e;
        d.random = b;
        RandomListNode clone = new Solution().Clone(a);
        show(clone);
    }

    private static void show(RandomListNode head) {
        while (head != null) {
            System.out.print(head.label);
            head = head.next;
        }
    }
}
```

### [34、二叉搜索树与双向链表](https://www.nowcoder.com/practice/947f6eb80d944a84850b0538bf0ec3a5?tpId=13&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向，如下图，左边为二叉搜索树，右边为转换后的排序双向链表。

![dyXUHO.png](https://s1.ax1x.com/2020/08/25/dyXUHO.png)

**分析：**

二叉搜索树是一种排序的数据结构，左节点<根节点<右节点。中序遍历是按照从小到大的顺序遍历二叉树

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }
}
*/
public class Solution {
    
    //指向双向链表的尾节点
    public TreeNode lastNode;
    
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return null;
        if(pRootOfTree.left == null && pRootOfTree.right==null){
            lastNode = pRootOfTree;// 最后的一个节点可能为最右侧的叶节点
            return pRootOfTree;
        }
        // 1.将左子树构造成双链表，并返回链表头节点
        TreeNode left = Convert(pRootOfTree.left);
        // 3.如果左子树链表不为空的话，将当前root追加到左子树链表
        if(left!=null){
            lastNode.right = pRootOfTree;
            pRootOfTree.left = lastNode;
        }
        lastNode = pRootOfTree;// 当根节点只含左子树时，则该根节点为最后一个节点
        // 4.将右子树构造成双链表，并返回链表头节点
        TreeNode right = Convert(pRootOfTree.right);
        // 5.如果右子树链表不为空的话，将该链表追加到root节点之后
        if(right!=null){
            right.left = pRootOfTree;
            pRootOfTree.right = right;
        }
        return left != null ? left : pRootOfTree; 
    }
}
```

