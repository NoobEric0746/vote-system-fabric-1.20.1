package com.nooberic.vote;

import com.nooberic.vote.datagen.ModENUSLangProvider;
import com.nooberic.vote.datagen.ModModelsProvider;
import com.nooberic.vote.datagen.ModZHCNLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VoteSystemDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelsProvider::new);
		pack.addProvider(ModENUSLangProvider::new);
		pack.addProvider(ModZHCNLangProvider::new);
	}
}
