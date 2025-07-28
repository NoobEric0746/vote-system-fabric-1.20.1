package com.nooberic.vote.item;

import com.nooberic.vote.VoteSystem;
import com.nooberic.vote.item.custom.Ticket;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TICKET = registerItems("ticket", new Ticket(new Item.Settings()));

    private static Item registerItems(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(VoteSystem.MOD_ID, id), item);
    }

    public static void registerModItems() {
        VoteSystem.LOGGER.info("Registering Items");
    }
}
