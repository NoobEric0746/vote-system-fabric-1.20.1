package com.nooberic.vote.networking;

import com.nooberic.vote.VoteSystem;
import com.nooberic.vote.networking.packet.VoteDataC2SPacket;
import com.nooberic.vote.networking.packet.VoteListS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessage {
    public static final Identifier VOTE_LIST_ID = new Identifier(VoteSystem.MOD_ID, "vote_list");
    public static final Identifier VOTE_DATA_ID = new Identifier(VoteSystem.MOD_ID, "vote_data");

    @Environment(EnvType.CLIENT)
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VOTE_DATA_ID, VoteDataC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(VOTE_LIST_ID, VoteListS2CPacket::receive);
    }

}
