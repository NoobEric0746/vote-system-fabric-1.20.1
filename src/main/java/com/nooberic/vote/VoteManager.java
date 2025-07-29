package com.nooberic.vote;

import com.nooberic.vote.networking.ModMessage;
import com.nooberic.vote.persistent.VoteData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.*;

public class VoteManager {
    public static Map<Item, Integer> votes_l = new HashMap<>();
    public static List<Item> voteItems_l = new ArrayList<>();

    public void addVoteItem(Item item, MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        if (serverState.voteItems.size() < 27 && !serverState.voteItems.contains(item)) {
            serverState.voteItems.add(item);
            serverState.votes.putIfAbsent(item, 0);
        }
    }

    public static void updVotes(PlayerEntity user){
        PacketByteBuf buf = PacketByteBufs.create();
        VoteData serverState = VoteData.getServerState(user.getServer());
        buf.writeBoolean(true);
        buf.writeInt(serverState.voteItems.size());
        for (Item item : serverState.voteItems) {
            buf.writeItemStack(new ItemStack(item));
        }
        ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessage.VOTE_LIST_ID, buf);
    }

    public void voteFor(Item item,MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        serverState.votes.computeIfPresent(item, (k, v) -> v + 1);
    }

    public List<Item> getVoteItems() {
        return new ArrayList<>(voteItems_l);
    }

    // 获取排序后的投票结果
    public List<Map.Entry<Item, Integer>> getSortedVotes(MinecraftServer server) {
        VoteData serverState = VoteData.getServerState(server);
        List<Map.Entry<Item, Integer>> sorted = new ArrayList<>(serverState.votes.entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sorted;
    }
}