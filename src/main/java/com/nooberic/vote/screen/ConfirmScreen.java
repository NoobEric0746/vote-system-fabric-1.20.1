package com.nooberic.vote.screen;

import com.nooberic.vote.networking.ModMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class ConfirmScreen extends Screen {
    private final Item selectedItem;

    public ConfirmScreen(Item item) {
        super(Text.literal("确认投票"));
        this.selectedItem = item;
    }

    @Override
    protected void init() {
        super.init();

        // 添加按钮
        addDrawableChild(ButtonWidget.builder(Text.literal("确认投票"), b -> submitVote())
                .dimensions(width / 2 - 100, height - 40, 90, 20)
                .build());

        addDrawableChild(ButtonWidget.builder(Text.literal("取消"), b -> close())
                .dimensions(width / 2 + 10, height - 40, 90, 20)
                .build());
    }

    private void submitVote() {
        // 记录投票
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeItemStack(new ItemStack(selectedItem));
        ClientPlayNetworking.send(ModMessage.VOTE_DATA_ID, buf);
//        VoteSystem.VOTE_MANAGER.voteFor(selectedItem);
        close();
        if (client != null && client.player != null) {
            //client.player.sendMessage(Text.literal("投票成功! 感谢您的参与").formatted(Formatting.GREEN), false);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // 渲染选中物品信息
        context.drawText(textRenderer, "您选择了:", width / 2 - 100, 30, 0xFFFFFF, false);
        context.drawItem(new ItemStack(selectedItem), width / 2 - 8, 50);
        context.drawText(textRenderer, Text.translatable(selectedItem.getTranslationKey()),
                width / 2 - 50, 80, 0xFFFF00, true);

        context.drawText(textRenderer, "确认要投票给此物品吗?", width / 2 - 100, height - 70, 0xFFFFFF, false);
    }
}