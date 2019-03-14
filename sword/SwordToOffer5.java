package com.wavever.demo.sword;

/**
 * 面试题5：替换空格
 */
public class SwordToOffer5 {
    public static void main(String[] args) {
        System.out.println(replaceSpace(new StringBuffer(" How old are you")));
    }

    private static String replaceSpace(StringBuffer sb) {
        if (sb == null || sb.length() == 0) return "";
        return sb.toString().replaceAll(" ", "%20");
    }
}
