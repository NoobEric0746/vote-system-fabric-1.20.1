package com.nooberic.vote.networking.packet;

import com.nooberic.vote.VoteManager;
import com.nooberic.vote.VoteSystem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class VoteDataC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ItemStack itemStack = buf.readItemStack();
        Item item = itemStack.getItem();
        VoteSystem.VOTE_MANAGER.voteFor(item);
        player.sendMessage(Text.literal("ok"));
    }
}
