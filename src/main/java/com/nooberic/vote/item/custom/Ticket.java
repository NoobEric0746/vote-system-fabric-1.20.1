package com.nooberic.vote.item.custom;

import com.nooberic.vote.VoteManager;
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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Ticket extends Item {
    public Ticket(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            VoteManager.updVotes(user,true);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Environment(EnvType.CLIENT)
    public static void openVoteScreen() {
        MinecraftClient.getInstance().setScreen(new VoteScreen());
    }

}
