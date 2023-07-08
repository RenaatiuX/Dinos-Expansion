package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.api.BoatTypeRegistry;
import com.rena.dinosexpansion.common.entity.util.BoatType;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomBoatEntity extends BoatEntity {
    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(CustomBoatEntity.class, DataSerializers.VARINT);


    public CustomBoatEntity(EntityType<? extends CustomBoatEntity> type, World world) {
        super(type, world);
    }

    public CustomBoatEntity(World worldIn, double x, double y, double z) {
        this(EntityInit.CUSTOM_BOAT.get(), worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BOAT_TYPE, 0);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        this.getBoat().toNbt(compound);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if (BoatTypeMaterial.hasMaterialInNbt(compound))
            setBoat(BoatTypeMaterial.fromNbt(compound));
    }

    public void setBoat(BoatTypeMaterial boatType) {
        this.dataManager.set(BOAT_TYPE, BoatTypeRegistry.BOAT_TYPES.indexOf(boatType));
    }

    @Override
    public Item getItemBoat() {
        return getBoat().getBoatItem().asItem();
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.lastYd = this.getMotion().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != BoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.onLivingFall(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && !this.removed) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                this.entityDropItem(this.getBoat().getPlanks().asItem());
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - y);
            }

        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public BoatTypeMaterial getBoat() {
        return BoatTypeRegistry.BOAT_TYPES.get(this.dataManager.get(BOAT_TYPE));
    }

    /**
     * all Boat types have to be registered at {@link BoatTypeRegistry#register(BoatTypeMaterial)}
     */
    public static interface BoatTypeMaterial{

        /**
         * reads the Boat Material from the given nbt
         */
        public static BoatTypeMaterial fromNbt(CompoundNBT nbt){
           return BoatTypeRegistry.BOAT_TYPES.get(nbt.getInt("boat_type_material"));
        }

        /**
         *
         * @return whether a material was stored to that nbt
         */
        public static boolean hasMaterialInNbt(CompoundNBT nbt){
            return nbt.contains("boat_type_material", 99);
        }

        /**
         *
         * @return the name of the given material, should be unique
         */
        public String getName();

        /**
         *
         * @return this boat item whith which the boat then can be spawned
         */
        public IItemProvider getBoatItem();

        /**
         *
         * @return the planks this boat is made of
         */
        public IItemProvider getPlanks();

        /**
         *
         * @return the texture that will be applied to the model
         */
        public ResourceLocation getBoatTexture();

        /**
         *
         * @return the model of the boat
         */
        default BoatModel getModel(CustomBoatEntity entity){
            return new BoatModel();
        }

        /**
         * saves the material to the give nbt
         */
        default void toNbt(CompoundNBT nbt){
            nbt.putInt("boat_type_material", BoatTypeRegistry.BOAT_TYPES.indexOf(this));
        }


    }
}
