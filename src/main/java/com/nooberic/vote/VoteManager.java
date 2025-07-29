package com.nooberic.vote;

import com.nooberic.vote.networking.ModMessage;
import com.nooberic.vote.persistent.VoteData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class VoteManager {
    public static List<Item> localVoteItems = new ArrayList<>();

    public void addVoteItem(Item item, MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        if (!VoteData.voteItems.contains(item)) {
            VoteData.voteItems.add(item);
            VoteData.votes.putIfAbsent(item, 0);
        }
    }

    public void removeVoteItem(Item item, MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        if (VoteData.voteItems.contains(item)) {
            VoteData.voteItems.remove(item);
            VoteData.votes.remove(item);
        }
    }

    public static void updVotes(PlayerEntity user) {
        PacketByteBuf buf = PacketByteBufs.create();
        VoteData serverState = VoteData.getServerState(Objects.requireNonNull(user.getServer()));
        buf.writeBoolean(true);
        buf.writeInt(VoteData.voteItems.size());
        for (Item item : VoteData.voteItems) {
            buf.writeItemStack(new ItemStack(item));
        }
        ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessage.VOTE_LIST_ID, buf);
    }

    public void voteFor(Item item, MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        VoteData.votes.computeIfPresent(item, (k, v) -> v + 1);
    }

    public List<Item> getVoteItems() {
        return new ArrayList<>(localVoteItems);
    }

    // 获取排序后的投票结果
    public List<Map.Entry<Item, Integer>> getSortedVotes(MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        List<Map.Entry<Item, Integer>> sorted = new ArrayList<>(VoteData.votes.entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sorted;
    }
}