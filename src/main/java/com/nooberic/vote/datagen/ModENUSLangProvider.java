package com.nooberic.vote.datagen;

import com.nooberic.vote.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModENUSLangProvider extends FabricLanguageProvider {
    public ModENUSLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.TICKET, "Ticket");
        translationBuilder.add("item.vote-system.ticket.tooltip", "Right click to join the vote");
    }
}
