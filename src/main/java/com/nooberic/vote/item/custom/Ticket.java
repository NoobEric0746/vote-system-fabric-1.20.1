package com.nooberic.vote.item.custom;

import com.nooberic.vote.VoteManager;
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
        if (!world.isClient) {
            VoteManager.updVotes(user);
        }
//        user.getStackInHand(hand).decrement(1);
//        user.addExperienceLevels(5);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
