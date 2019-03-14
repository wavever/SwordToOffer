package com.wavever.demo.sword;

import java.util.ArrayList;

/**
 * 从尾到头打印链表
 * <p>
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class SwordToOffer6 {
    public static void main(String[] args) {
        System.out.println();
    }

    ArrayList<Integer> list = new ArrayList<>();

    private ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null || list == null) return list;
        if (listNode.next != null) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.value);
        }
        return list;
    }


    private class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }
}
