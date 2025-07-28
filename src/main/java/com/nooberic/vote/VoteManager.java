package com.nooberic.vote;

import net.minecraft.item.Item;

import java.util.*;

public class VoteManager {
    public static Map<Item, Integer> votes = new HashMap<>();
    public static List<Item> voteItems = new ArrayList<>();

    public void addVoteItem(Item item) {
        if (voteItems.size() < 27 && !voteItems.contains(item)) {
            voteItems.add(item);
            votes.putIfAbsent(item, 0);
        }
    }

    public void voteFor(Item item) {
    }

    public List<Item> getVoteItems() {
        return new ArrayList<>(voteItems);
    }

    // 获取排序后的投票结果
    public List<Map.Entry<Item, Integer>> getSortedVotes() {
        List<Map.Entry<Item, Integer>> sorted = new ArrayList<>(votes.entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sorted;
    }
}