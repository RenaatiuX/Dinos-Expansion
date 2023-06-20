package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TribeSaveData extends WorldSavedData {

    public static final String NAME = DinosExpansion.MOD_ID + "tribes";

    private final Map<String, Tribe> tribes = new HashMap<>();

    public TribeSaveData() {
        super(NAME);
    }


    @Nullable
    public Tribe getTribeWithName(String name){
        return tribes.get(name);
    }

    public boolean hasTribe(Tribe t){
        return hasTribe(t.getName());
    }

    public boolean hasTribe(String name){
        return this.tribes.containsKey(name);
    }

    public int getSize(){
        return tribes.size();
    }

    public void remove(Tribe t){
        this.tribes.remove(t.getName());
    }

    public Collection<Tribe> getTribes(){
        return this.tribes.values();
    }

    public void addTribe(Tribe t){
        if (!hasTribe(t))
            this.tribes.put(t.getName(), t);
    }

    @Override
    public void read(CompoundNBT nbt) {
        for (int i = 0; nbt.contains("tribe" + i); i++) {
            Tribe t = Tribe.fromNbt(nbt.getCompound("tribe" + i));
            tribes.put(t.getName(), t);
            System.out.println("this reads the tribes");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        int i = 0;
        for (Tribe t : tribes.values()) {
            if (t.getSize() > 0) {
                compound.put("tribe" + i, t.write());
                i++;
            }
        }
        return compound;
    }

    public static void addTribe(Tribe toAdd, ServerWorld world){
        TribeSaveData data = getData(world);
        data.tribes.put(toAdd.getName(), toAdd);
        data.markDirty();
    }


    public static void removeTribe(Tribe t, ServerWorld world){
        TribeSaveData data = getData(world);
        data.tribes.remove(t.getName());
        data.markDirty();
    }
    public static TribeSaveData getData(ServerWorld world){
        return world.getSavedData().getOrCreate(TribeSaveData::new, NAME);
    }
}
