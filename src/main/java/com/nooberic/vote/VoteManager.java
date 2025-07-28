package com.nooberic.vote;

import com.nooberic.vote.networking.ModMessage;
import com.nooberic.vote.screen.VoteScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

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

    public static void updVotes(PlayerEntity user, Boolean openScreen){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(true);
        buf.writeInt(VoteManager.voteItems.size());
        for (Item item : VoteManager.voteItems) {
            buf.writeItemStack(new ItemStack(item));
        }
        ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessage.VOTE_LIST_ID, buf);
    }

    public void voteFor(Item item) {
        votes.computeIfPresent(item, (k, v) -> v + 1);
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