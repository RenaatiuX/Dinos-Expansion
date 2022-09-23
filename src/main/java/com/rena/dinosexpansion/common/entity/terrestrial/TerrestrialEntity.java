package com.rena.dinosexpansion.common.entity.terrestrial;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public abstract class TerrestrialEntity extends AnimalEntity {

    public static final DataParameter<Boolean> HAS_SADDLE = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_TAMED = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    public static final DataParameter<Optional<UUID>> PLAYER_KNOCKOUT_ID = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    public static final DataParameter<Boolean> HAS_CHEST = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> HAS_ARMOR = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> CHILD = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> KNOCKOUT = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> STUNNED = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> HUNGER = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> STATUS = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.VARINT);
    public static final DataParameter<String> RARITY = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> SEX = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.STRING);
    public static final DataParameter<Integer> GROWTH = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Byte> TAMING_PROGRESS = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Byte> LEVEL_PROGRESS = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(TerrestrialEntity.class, DataSerializers.VARINT);

    protected TerrestrialEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
