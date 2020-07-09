## SwordToOffer

Rush! Rush!

[TOC]

### 概念

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

