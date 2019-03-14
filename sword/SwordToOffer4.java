package com.wavever.demo.sword;

/**
 * 面试题4：二维数组中的查找
 */
public class SwordToOffer4 {
    public static void main(String[] args) {
        int[][] nums = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        System.out.println(findNum(1, nums));
    }

    private static boolean findNum(int target, int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) return false;
        int rows = 0;
        int colums = array[0].length - 1;
        while (rows < array.length && colums >= 0) {
            if (target == array[rows][colums]) return true;
            if (target > array[rows][colums]) {
                ++rows;
            } else {
                --colums;
            }
        }
        return false;
    }
}
