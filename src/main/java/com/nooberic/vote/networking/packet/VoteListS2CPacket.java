package com.nooberic.vote.networking.packet;

import com.nooberic.vote.VoteManager;
import com.nooberic.vote.item.custom.Ticket;
import com.nooberic.vote.screen.VoteScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class VoteListS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender sender) {
        List<Item> list = new ArrayList<>();
        boolean openScreen = buf.readBoolean();
        int count = buf.readInt();
        for (int i = 0; i < count; i++) {
            ItemStack itemStack = buf.readItemStack();
            list.add(itemStack.getItem());
        }
        VoteManager.voteItems = list;
        if(openScreen){
            if(client.world.isClient){
                openVoteScreen();
            }
        }
    }
    @Environment(EnvType.CLIENT)
    public static void openVoteScreen() {
        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().setScreen(new VoteScreen());
        });
    }


}
