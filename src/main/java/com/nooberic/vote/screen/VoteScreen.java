package com.nooberic.vote.screen;

import com.nooberic.vote.VoteSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class VoteScreen extends Screen {
    private static final int ITEMS_PER_PAGE = 27;
    private int currentPage = 0;

    public VoteScreen() {
        super(Text.literal("投票系统"));
    }

    @Override
    protected void init() {
        super.init();
        List<Item> items = VoteSystem.VOTE_MANAGER.getVoteItems();

        // 添加物品按钮
        int startIndex = currentPage * ITEMS_PER_PAGE;
        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            if (startIndex + i >= items.size()) break;

            Item item = items.get(startIndex + i);
            addDrawableChild(new ItemButtonWidget(
                    10 + (0) * 20,
                    30 + (i) * 20,
                    100, 18,
                    new ItemStack(item),
                    Text.translatable(item.getTranslationKey()),
                    button -> openConfirmScreen(item)
            ));
        }

        // 添加翻页按钮
        addDrawableChild(ButtonWidget.builder(Text.literal("上一页"), b -> prevPage())
                .dimensions(width / 2 - 50, height - 30, 50, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("下一页"), b -> nextPage())
                .dimensions(width / 2 + 5, height - 30, 50, 20).build());
    }

    private void openConfirmScreen(Item item) {
        if (client != null) {
            client.setScreen(new ConfirmScreen(item));
        }
    }

    private void prevPage() {
        if (currentPage > 0) currentPage--;
        clearAndInit();
    }

    private void nextPage() {
        int totalPages = (int) Math.ceil((double) VoteSystem.VOTE_MANAGER.getVoteItems().size() / ITEMS_PER_PAGE);
        if (currentPage < totalPages - 1) {
            currentPage++;
            clearAndInit();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer, "选择您要投票的物品", width / 2 - 50, 10, 0xFFFFFF, true);

        int totalPages = (int) Math.ceil((double) VoteSystem.VOTE_MANAGER.getVoteItems().size() / ITEMS_PER_PAGE);
        context.drawText(textRenderer, "第 " + (currentPage + 1) + " 页 / 共 " + totalPages + " 页",
                width / 2 - 30, height - 50, 0xAAAAAA, true);
    }

    // 自定义物品按钮
    static class ItemButtonWidget extends ButtonWidget {
        private final ItemStack stack;

        public ItemButtonWidget(int x, int y, int width, int height,
                                ItemStack stack, Text name, PressAction onPress) {
            super(x, y, width, height, name, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.stack = stack;
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            super.renderButton(context, mouseX, mouseY, delta);
            context.drawItem(stack, getX() + 1, getY() + 1);
        }
    }
}