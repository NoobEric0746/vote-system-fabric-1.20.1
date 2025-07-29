package com.nooberic.vote.networking.packet;

import com.nooberic.vote.VoteSystem;
import com.nooberic.vote.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.message.SentMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class VoteDataC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ItemStack itemStack = buf.readItemStack();
        Item item = itemStack.getItem();
        if(player.getMainHandStack().getItem() == ModItems.TICKET) {
            if (!player.getAbilities().creativeMode) {
                player.getMainHandStack().decrement(1);
                player.addExperienceLevels(5);
            }
            VoteSystem.VOTE_MANAGER.voteFor(item, server);
            player.sendMessage(Text.literal("Vote Accepted").formatted(Formatting.GREEN));
        }
        else{
            player.sendMessage(Text.literal("Network Error").formatted(Formatting.RED));
        }
    }
}
