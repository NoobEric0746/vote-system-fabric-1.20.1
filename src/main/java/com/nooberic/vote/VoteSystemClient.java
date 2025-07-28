package com.nooberic.vote;

import com.nooberic.vote.networking.ModMessage;
import net.fabricmc.api.ClientModInitializer;

public class VoteSystemClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessage.registerClientPackets();
    }
}
