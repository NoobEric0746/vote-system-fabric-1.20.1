package com.nooberic.vote.command;

import com.mojang.brigadier.CommandDispatcher;
import com.nooberic.vote.VoteSystem;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

public class ModCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        // 添加投票物品命令
        dispatcher.register(CommandManager.literal("addVoteItem")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                        .executes(context -> {
                            ItemStackArgument isa = ItemStackArgumentType.getItemStackArgument(context, "item");
                            Item item = isa.getItem();
                            VoteSystem.VOTE_MANAGER.addVoteItem(item, context.getSource().getServer());
                            context.getSource().sendFeedback(() ->
                                    Text.literal("添加投票物品: ").append(Text.translatable(item.getTranslationKey())), true);
                            return 1;
                        })
                )
        );

        // 去除投票物品命令
        dispatcher.register(CommandManager.literal("removeVoteItem")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                        .executes(context -> {
                            ItemStackArgument isa = ItemStackArgumentType.getItemStackArgument(context, "item");
                            Item item = isa.getItem();
                            VoteSystem.VOTE_MANAGER.removeVoteItem(item, context.getSource().getServer());
                            context.getSource().sendFeedback(() ->
                                    Text.literal("去除投票物品: ").append(Text.translatable(item.getTranslationKey())), true);
                            return 1;
                        })
                )
        );

        // 查看投票结果命令
        dispatcher.register(CommandManager.literal("voteInfo")
                .executes(context -> {
                    List<Map.Entry<Item, Integer>> sortedVotes = VoteSystem.VOTE_MANAGER.getSortedVotes(context.getSource().getServer());

                    context.getSource().sendFeedback(() ->
                            Text.literal("===== 投票结果 =====").formatted(Formatting.GOLD), false);

                    for (Map.Entry<Item, Integer> entry : sortedVotes) {
                        Text itemName = Text.translatable(entry.getKey().getTranslationKey());
                        Text message = Text.literal(" - ")
                                .append(itemName.copy().formatted(Formatting.AQUA))
                                .append(Text.literal(": " + entry.getValue() + " 票").formatted(Formatting.WHITE));

                        context.getSource().sendFeedback(() -> message, false);
                    }
                    return sortedVotes.size();
                })
        );
    }

    public static void registerModCommands() {
        // 注册命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ModCommands.register(dispatcher, registryAccess);
        });

        VoteSystem.LOGGER.info("Registering Commands");
    }
}