package com.nooberic.vote.datagen;

import com.nooberic.vote.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModZHCNLangProvider extends FabricLanguageProvider {
    public ModZHCNLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.TICKET, "票券");
        translationBuilder.add("item.vote-system.ticket.tooltip", "右键使用参与投票");
    }
}
