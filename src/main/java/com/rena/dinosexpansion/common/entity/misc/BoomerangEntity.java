package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BoomerangEntity extends Entity {

    private BlockPos activatedPos;
    protected boolean isBouncing;
    private double bounceFactor;
    private float prevBoomerangRotation;
    private boolean turningAround;
    protected int timeBeforeTurnAround;
    List<ItemEntity> itemsPickedUp;
    private ItemStack selfStack;
    private Hand hand;
    private static final DataParameter<Float> ROTATION;
    private static final DataParameter<Optional<UUID>> RETURN_UNIQUE_ID;

    public BoomerangEntity(EntityType<BoomerangEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.bounceFactor = 0.85D;
        this.turningAround = false;
        this.timeBeforeTurnAround = 30;
        this.itemsPickedUp = new ArrayList<>();
        this.hand = Hand.MAIN_HAND;
    }

    public BoomerangEntity(EntityType<BoomerangEntity> type, World worldIn, PlayerEntity entity, ItemStack itemstack, Hand hand) {
        this(type, worldIn);
        this.selfStack = itemstack;
        this.setRotation(entity.rotationYaw, entity.rotationPitch);
        double x = -MathHelper.sin(entity.rotationYaw * 3.141593F / 180.0F);
        double z = MathHelper.cos(entity.rotationYaw * 3.141593F / 180.0F);
        double motionX = 0.5D * x * (double)MathHelper.cos(entity.rotationPitch / 180.0F * 3.141593F);
        double motionY = -0.5D * (double)MathHelper.sin(entity.rotationPitch / 180.0F * 3.141593F);
        double motionZ = 0.5D * z * (double)MathHelper.cos(entity.rotationPitch / 180.0F * 3.141593F);
        this.setMotion(new Vector3d(motionX, motionY, motionZ));
        this.setPosition(entity.getPosX(), this.getReturnEntityY(entity), entity.getPosZ());
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();
        this.isBouncing = false;
        this.turningAround = false;
        this.hand = hand;
        this.setReturnToId(entity.getUniqueID());
    }

    public double getReturnEntityY(PlayerEntity entity) {
        return entity.getPosY() + (double)entity.getEyeHeight() - 0.10000000149011612D;
    }

    @Override
    public void tick() {
        PlayerEntity player = this.getReturnTo();
        if (this.ticksExisted % 11 == 0) {
            BlockPos currentPos = this.getPosition();
            //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_LOOP.get(), SoundCategory.PLAYERS, 0.4F, 1.0F);
        }

        Vector3d vec3d1 = this.getPositionVec();
        Vector3d vec3d = this.getPositionVec().add(this.getMotion());
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, (Entity)null));
        if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos pos = new BlockPos(raytraceresult.getHitVec());
            BlockState state = this.world.getBlockState(pos);
            if (state.getMaterial() == Material.PLANTS && DinosExpansionConfig.BREAKS_FLOWERS.get()) {
                this.world.destroyBlock(pos, true);
            }

            if (state.getMaterial() == Material.TALL_PLANTS && DinosExpansionConfig.BREAKS_TALLGRASS.get()) {
                this.world.destroyBlock(pos, true);
            }

            if (state.getBlock() instanceof TorchBlock && DinosExpansionConfig.BREAKS_TORCHES.get()) {
                this.world.destroyBlock(pos, true);
            }

            if (state.getBlock() instanceof LeverBlock && DinosExpansionConfig.ACTIVATES_LEVERS.get()) {
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                    this.timeBeforeTurnAround = 0;
                }

                if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                    this.activatedPos = pos;
                    state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult)raytraceresult.hitInfo);
                }
            }

            if (state.getBlock() instanceof AbstractButtonBlock && DinosExpansionConfig.ACTIVATES_BUTTONS.get()) {
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                    this.timeBeforeTurnAround = 0;
                }

                if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                    this.activatedPos = pos;
                    state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult)raytraceresult.hitInfo);
                }
            }

            if (state.getBlock() instanceof PressurePlateBlock && DinosExpansionConfig.ACTIVATES_PRESSURES_PLATES.get()) {
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                    this.timeBeforeTurnAround = 0;
                }

                if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                    this.activatedPos = pos;
                    state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult)raytraceresult.hitInfo);
                }
            }

            if (state.getBlock() instanceof TripWireBlock && DinosExpansionConfig.ACTIVATES_TRIP_WIRE.get()) {
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                    this.timeBeforeTurnAround = 0;
                }

                if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                    this.activatedPos = pos;
                    state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult)raytraceresult.hitInfo);
                }
            }
        }

        double newX;
        double newY;
        double newZ;
        if (!this.turningAround) {
            Vector3d motionBefore = this.getMotion();
            this.move(MoverType.SELF, motionBefore);
            Vector3d motionAfter = this.getMotion();
            newX = motionAfter.x;
            newY = motionAfter.y;
            newZ = motionAfter.z;
            boolean flag = false;
            if (motionAfter.x != motionBefore.x) {
                newX = -motionBefore.x;
                flag = true;
            }

            if (motionAfter.y != motionBefore.y) {
                newY = -motionBefore.y;
                flag = true;
            }

            if (motionAfter.z != motionBefore.z) {
                newZ = -motionBefore.z;
                flag = true;
            }

            if (flag) {
                this.isBouncing = true;
                this.setMotion((new Vector3d(newX, newY, newZ)).mul(this.bounceFactor, this.bounceFactor, this.bounceFactor));
            }

            this.beforeTurnAround(player);
            if (this.timeBeforeTurnAround-- <= 0) {
                this.turningAround = true;
            }
        } else if (player != null) {
            double x = player.getPosX() - this.getPosX();
            newX = this.getReturnEntityY(player) - this.getPosY();
            newY = player.getPosZ() - this.getPosZ();
            newZ = Math.sqrt(x * x + newX * newX + newY * newY);
            if (newZ < 1.5D) {
                this.setEntityDead();
            }

            this.setMotion(0.9D * x / newZ, 0.5D * newX / newZ, 0.9D * newY / newZ);
            this.setPosition(this.getPosX() + this.getMotion().x, this.getPosY() + this.getMotion().y, this.getPosZ() + this.getMotion().z);
        }

        this.determineRotation();
        this.prevBoomerangRotation = this.getBoomerangRotation();
        this.setBoomerangRotation(this.getBoomerangRotation() + 36.0F);

        while(this.getBoomerangRotation() > 360.0F) {
            this.setBoomerangRotation(this.getBoomerangRotation() - 360.0F);
        }

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().expand(0.5D, 0.5D, 0.5D));

        for (Entity entity : list) {
            if (entity instanceof ItemEntity) {
                this.itemsPickedUp.add((ItemEntity) entity);
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_ITEM.get()) {
                    this.timeBeforeTurnAround = 0;
                }
            } else if (entity instanceof LivingEntity && entity != player) {
                this.onEntityHit(entity, player);
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_MOB.get()) {
                    this.timeBeforeTurnAround = 0;
                }
            }
        }

        for (ItemEntity item : this.itemsPickedUp) {
            item.setMotion(0.0D, 0.0D, 0.0D);
            if (item.isAlive()) {
                Vector3d pos = this.getPositionVec();
                item.setPosition(pos.x, pos.y, pos.z);
            }
        }

        super.tick();
    }

    public void beforeTurnAround(PlayerEntity player) {
    }

    public void onEntityHit(Entity hitEntity, PlayerEntity player) {
        hitEntity.attackEntityFrom(this.causeNewDamage(this, player), (float)this.getDamage(hitEntity, player));
    }

    protected abstract int getDamage(Entity var1, PlayerEntity var2);

    public abstract DamageSource causeNewDamage(BoomerangEntity boomerang, Entity entity);

    public void setEntityDead() {
        if (this.getReturnTo() != null && this.selfStack != null) {
            if (this.hand == Hand.OFF_HAND) {
                if (this.getReturnTo().getHeldItemOffhand().isEmpty()) {
                    this.getReturnTo().setHeldItem(Hand.OFF_HAND, this.selfStack);
                } else {
                    this.getReturnTo().inventory.addItemStackToInventory(this.selfStack);
                }
            } else {
                this.getReturnTo().inventory.addItemStackToInventory(this.selfStack);
            }
        }

        super.setDead();
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ROTATION, 0.0F);
        this.dataManager.register(RETURN_UNIQUE_ID, Optional.empty());
    }

    public float getBoomerangRotation() {
        return this.dataManager.get(ROTATION);
    }

    public void setBoomerangRotation(float rotationIn) {
        this.dataManager.set(ROTATION, rotationIn);
    }

    @Nullable
    public UUID getReturnToId() {
        return this.dataManager.get(RETURN_UNIQUE_ID).orElse(null);
    }

    public void setReturnToId(@Nullable UUID uuid) {
        this.dataManager.set(RETURN_UNIQUE_ID, Optional.ofNullable(uuid));
    }

    @Nullable
    public PlayerEntity getReturnTo() {
        try {
            UUID uuid = this.getReturnToId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean isReturnTo(LivingEntity entityIn) {
        return entityIn == this.getReturnTo();
    }

    public void determineRotation() {
        Vector3d motion = this.getMotion();
        this.rotationYaw = -57.29578F * (float)Math.atan2(motion.x, motion.z);
        double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
        this.rotationPitch = -57.29578F * (float)Math.atan2(motion.y, d1);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.isBouncing = compound.getBoolean("IsBouncing");
        this.bounceFactor = compound.getDouble("BounceFactor");
        this.prevBoomerangRotation = compound.getFloat("PrevBoomerangRotation");
        this.setBoomerangRotation(compound.getFloat("BoomerangRotation"));
        this.turningAround = compound.getBoolean("TurningAround");
        this.timeBeforeTurnAround = compound.getInt("TimeBeforeTurnAround");
        if (compound.contains("xPos") && compound.contains("yPos") && compound.contains("zPos")) {
            this.activatedPos = new BlockPos(compound.getInt("xPos"), compound.getInt("yPos"), compound.getInt("zPos"));
        }

        if (compound.contains("ReturnToUUID", 8)) {
            try {
                this.setReturnToId(UUID.fromString(compound.getString("ReturnToUUID")));
            } catch (Throwable var6) {
            }
        }

        this.selfStack = ItemStack.read(compound.getCompound("SelfStack"));
        ListNBT itemsGathered = compound.getList("ItemsPickedUp", 10);

        for(int i = 0; i < itemsGathered.size(); ++i) {
            CompoundNBT tag = itemsGathered.getCompound(i);
            ItemEntity item = new ItemEntity(this.world, 0.0D, 0.0D, 0.0D);
            item.readAdditional(tag);
            this.itemsPickedUp.add(item);
        }

        this.hand = Hand.valueOf(compound.getString("hand"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("IsBouncing", this.isBouncing);
        compound.putDouble("BounceFactor", this.bounceFactor);
        compound.putFloat("PrevBoomerangRotation", this.prevBoomerangRotation);
        compound.putFloat("BoomerangRotation", this.getBoomerangRotation());
        compound.putBoolean("TurningAround", this.turningAround);
        compound.putInt("TimeBeforeTurnAround", this.timeBeforeTurnAround);
        if (this.activatedPos != null) {
            compound.putInt("xPos", this.activatedPos.getX());
            compound.putInt("yPos", this.activatedPos.getY());
            compound.putInt("zPos", this.activatedPos.getZ());
        }

        if (this.getReturnToId() == null) {
            compound.putString("ReturnToUUID", "");
        } else {
            compound.putString("ReturnToUUID", this.getReturnToId().toString());
        }

        CompoundNBT selfStackTag = new CompoundNBT();
        this.selfStack.write(selfStackTag);
        compound.put("SelfStack", selfStackTag);
        ListNBT itemsGathered = new ListNBT();

        for(int i = 0; i < this.itemsPickedUp.size(); ++i) {
            if (this.itemsPickedUp.get(i) != null) {
                CompoundNBT tag = new CompoundNBT();
                this.itemsPickedUp.get(i).writeAdditional(compound);
                itemsGathered.add(tag);
            }
        }

        compound.put("ItemsPickedUp", itemsGathered);
        compound.putString("hand", this.hand.toString());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public abstract ItemStack getRenderedItemStack();

    static {
        ROTATION = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.FLOAT);
        RETURN_UNIQUE_ID = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    }
}
