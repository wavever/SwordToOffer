package com.wavever.demo.sword;

/**
 * 面试题3：数组中重复的数字
 */
public class SwordToOffer3 {

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(findDuplicateNum(nums));
    }

    //时间复杂度：O(n)
    //空间复杂度：O(1)
    private static int findDuplicateNum(int[] nums) {
        int num = -1;
        int temp;
        if (nums != null && nums.length > 0) {
            for (int i = 0; i < nums.length; i++) {
                while (nums[i] != i) {
                    if (nums[i] == nums[nums[i]]) {
                        num = nums[i];
                        break;
                    }
                    temp = nums[i];
                    nums[i] = nums[temp];
                    nums[temp] = temp;
                }
            }
        }
        return num;
    }

}
