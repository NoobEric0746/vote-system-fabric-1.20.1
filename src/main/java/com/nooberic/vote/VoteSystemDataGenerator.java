package com.nooberic.vote;

import com.nooberic.vote.datagen.ModENUSLanProvider;
import com.nooberic.vote.datagen.ModModelsProvider;
import com.nooberic.vote.datagen.ModZHCNLanProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VoteSystemDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelsProvider::new);
		pack.addProvider(ModENUSLanProvider::new);
		pack.addProvider(ModZHCNLanProvider::new);
	}
}
