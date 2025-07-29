package com.nooberic.vote.persistent;

import com.mojang.logging.LogUtils;
import com.nooberic.vote.VoteSystem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteData extends PersistentState {
    private static final Logger LOGGER = LogUtils.getLogger();
    public int size = 0;
    public static Map<Item, Integer> votes = new HashMap<>();
    public static List<Item> voteItems = new ArrayList<>();

    public VoteData(){
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("size", votes.size());
        int idx=1;
        for (Map.Entry<Item, Integer> entry : votes.entrySet()) {
            nbt.putInt(String.valueOf(idx)+"key",Item.getRawId(entry.getKey()));
            nbt.putInt(String.valueOf(idx)+"num",entry.getValue());
            idx++;
        }
        return nbt;
    }

    public static VoteData createFromNBT(NbtCompound nbt){
        VoteData state = new VoteData();
        state.size = nbt.getInt("size");
        for(int i=1;i<=state.size;i++){
            Item item = Item.byRawId(nbt.getInt(String.valueOf(i)+"key"));
            Integer num = nbt.getInt(String.valueOf(i)+"num");
            state.votes.putIfAbsent(item, num);
            state.voteItems.add(item);
        }
        return state;
    }

//    private static Type<VoteData> type = new Type<>(
//            VoteData::new, // 若不存在 'StateSaverAndLoader' 则创建
//            VoteData::createFromNBT, // 若存在 'StateSaverAndLoader' NBT, 则调用 'createFromNbt' 传入参数
//            null // 此处理论上应为 'DataFixTypes' 的枚举，但我们直接传递为空(null)也可以
//    );

    public static VoteData getServerState(MinecraftServer server) {
        // (注：如需在任意维度生效，请使用 'World.OVERWORLD' ，不要使用 'World.END' 或 'World.NETHER')
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        // 当第一次调用了方法 'getOrCreate' 后，它会创建新的 'StateSaverAndLoader' 并将其存储于  'PersistentStateManager' 中。
        //  'getOrCreate' 的后续调用将本地的 'StateSaverAndLoader' NBT 传递给 'StateSaverAndLoader::createFromNbt'。
        VoteData state = persistentStateManager.getOrCreate(VoteData::createFromNBT, VoteData::new, VoteSystem.MOD_ID);

        // 若状态未标记为脏(dirty)，当 Minecraft 关闭时， 'writeNbt' 不会被调用，相应地，没有数据会被保存。
        // 从技术上讲，只有在事实上发生数据变更时才应当将状态标记为脏(dirty)。
        // 但大多数开发者和模组作者会对他们的数据未能保存而感到困惑，所以不妨直接使用 'markDirty' 。
        // 另外，这只将对应的布尔值设定为 TRUE，代价是文件写入磁盘时模组的状态不会有任何改变。(这种情况非常少见)
        state.markDirty();

        return state;
    }
}