package com.nooberic.vote.item;

import com.nooberic.vote.VoteSystem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup VS_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(VoteSystem.MOD_ID, "vs_group"),
            ItemGroup.create(null, -1).displayName(Text.translatable("itemGroup.vs_group"))
                    .icon(() -> new ItemStack(ModItems.TICKET))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.TICKET);
                    }).build());

    public static void registerModItemsGroups() {
        VoteSystem.LOGGER.info("Registering Item Groups");
    }
}
