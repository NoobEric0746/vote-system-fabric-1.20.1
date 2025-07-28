package com.nooberic.vote.item.custom;

import com.nooberic.vote.screen.VoteScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Ticket extends Item {
    public Ticket(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            MinecraftClient.getInstance().setScreen(new VoteScreen());
        }
        return super.use(world, user, hand);
    }
}
