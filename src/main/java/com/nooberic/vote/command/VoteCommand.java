package com.nooberic.vote.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.nooberic.vote.VoteSystem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class VoteCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // 添加投票物品命令
        dispatcher.register(CommandManager.literal("addvote")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("item", StringArgumentType.string())
                        .executes(context -> {
                            Identifier itemId = new Identifier(context.getArgument("item", String.class));
                            Item item = Registries.ITEM.get(itemId);

                            VoteSystem.VOTE_MANAGER.addVoteItem(item);
                            context.getSource().sendFeedback(() ->
                                    Text.literal("添加投票物品: ").append(Text.translatable(item.getTranslationKey())), true);
                            return 1;
                        })
                )
        );

        // 查看投票结果命令
        dispatcher.register(CommandManager.literal("voteinfo")
                .executes(context -> {
                    List<Map.Entry<Item, Integer>> sortedVotes = VoteSystem.VOTE_MANAGER.getSortedVotes();

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
}