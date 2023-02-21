package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public class BoomerangEntity extends Entity {

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<UUID>> RETURN_UNIQUE_ID = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<ItemStack> BOOMERANG_STACK = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.ITEMSTACK);


    private BlockPos activatedPos;
    protected boolean isBouncing;
    private double bounceFactor;
    private float prevBoomerangRotation;
    private boolean turningAround;
    protected int timeBeforeTurnAround;
    List<ItemEntity> itemsPickedUp;
    private ItemStack selfStack;
    private Hand hand;
    private double damage;
    private double speed = 1.0d;

    public BoomerangEntity(EntityType<BoomerangEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.bounceFactor = 0.85D;
        this.turningAround = false;
        this.timeBeforeTurnAround = 30;
        this.itemsPickedUp = new ArrayList<>();
        this.hand = Hand.MAIN_HAND;
    }

    /**
     *
     * @param worldIn
     * @param entity
     * @param boomerang has to be from type {@link com.rena.dinosexpansion.common.item.TieredBoomerang}
     * @param hand
     */
    public BoomerangEntity(World worldIn, PlayerEntity entity, ItemStack boomerang, Hand hand) {
        this(EntityInit.BOOMERANG.get(), worldIn);
        this.dataManager.set(BOOMERANG_STACK, boomerang);
        this.setRotation(entity.rotationYaw, entity.rotationPitch);
        double x = -MathHelper.sin(entity.rotationYaw * 3.141593F / 180.0F);
        double z = MathHelper.cos(entity.rotationYaw * 3.141593F / 180.0F);

        double motionX = 0.5D * x * (double) MathHelper.cos(entity.rotationPitch / 180.0F * 3.141593F);
        double motionY = -0.5D * (double) MathHelper.sin(entity.rotationPitch / 180.0F * 3.141593F);
        double motionZ = 0.5D * z * (double) MathHelper.cos(entity.rotationPitch / 180.0F * 3.141593F);
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
        return entity.getPosY() + (double) entity.getEyeHeight() - 0.10000000149011612D;
    }

    public void setEntityDeadWithPlayerDeadOrMissing() {
        if (getRenderedItemStack() != null && !this.world.isRemote) {
            ItemEntity itementity = new ItemEntity(world, this.getPosX(), this.getPosY(), this.getPosZ(), selfStack);
            itementity.setDefaultPickupDelay();
            itementity.setMotion(itementity.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.1F, (double) (world.rand.nextFloat() * 0.05F), (double) ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.1F)));
            world.addEntity(itementity);
        }
        super.setDead();
    }

    @Override
    public void setMotion(Vector3d motionIn) {
        super.setMotion(motionIn.mul(speed, speed, speed));
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void tick() {
        PlayerEntity player = this.getReturnTo();

        if (player == null) {
            setEntityDeadWithPlayerDeadOrMissing();
        }

        if (player != null && player.getShouldBeDead()) {
            setEntityDeadWithPlayerDeadOrMissing();
        }

        if (this.ticksExisted % 11 == 0) {
            BlockPos currentPos = this.getPosition();
            //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_LOOP.get(), SoundCategory.PLAYERS, 0.4F, 1.0F);
        }

        Vector3d vec3d1 = this.getPositionVec();
        Vector3d vec3d = this.getPositionVec().add(this.getMotion());
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, (Entity) null));

        if (raytraceresult != null) {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
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
                        state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }

                if (state.getBlock() instanceof AbstractButtonBlock && DinosExpansionConfig.ACTIVATES_BUTTONS.get()) {
                    if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                        this.timeBeforeTurnAround = 0;
                    }

                    if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                        this.activatedPos = pos;
                        state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }

                if (state.getBlock() instanceof PressurePlateBlock && DinosExpansionConfig.ACTIVATES_PRESSURES_PLATES.get()) {
                    if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                        this.timeBeforeTurnAround = 0;
                    }

                    if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                        this.activatedPos = pos;
                        state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }

                if (state.getBlock() instanceof TripWireBlock && DinosExpansionConfig.ACTIVATES_TRIP_WIRE.get()) {
                    if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_BUTTON.get()) {
                        this.timeBeforeTurnAround = 0;
                    }

                    if (this.activatedPos == null || !this.activatedPos.equals(pos)) {
                        this.activatedPos = pos;
                        state.getBlock().onBlockActivated(state, this.world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
            }
        }

        if (!this.turningAround) {
            Vector3d motionBefore = this.getMotion();
            this.move(MoverType.SELF, motionBefore);
            Vector3d motionAfter = this.getMotion();

            double newX = motionAfter.x;
            double newY = motionAfter.y;
            double newZ = motionAfter.z;

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

            if (player != null) {
                this.beforeTurnAround(player);

                if (timeBeforeTurnAround-- <= 0) {
                    turningAround = true;
                }
            }
        } else if (player != null) {
            double x = player.getPosX() - this.getPosX();
            double y = this.getReturnEntityY(player) - this.getPosY();
            double z = player.getPosZ() - this.getPosZ();
            double d = Math.sqrt(x * x + y * y + z * z);
            if (d < 1.5D) {
                this.setEntityDead();
            }

            this.setMotion(0.9D * x / d, 0.5D * y / d, 0.9D * z / d);
            this.setPosition(this.getPosX() + this.getMotion().x, this.getPosY() + this.getMotion().y, this.getPosZ() + this.getMotion().z);
        }

        this.determineRotation();
        this.prevBoomerangRotation = this.getBoomerangRotation();
        //crazy for loop
        for (this.setBoomerangRotation(this.getBoomerangRotation() + 36F); this.getBoomerangRotation() > 360F; this.setBoomerangRotation(this.getBoomerangRotation() - 360F)) {
        }

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().expand(0.5D, 0.5D, 0.5D));

        for (Entity entity : list) {
            if (entity instanceof ItemEntity) {
                this.itemsPickedUp.add((ItemEntity) entity);
                if (this.timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_ITEM.get()) {
                    this.timeBeforeTurnAround = 0;
                }
                continue;
            }
            if (!(entity instanceof LivingEntity) || entity == player) {
                continue;
            }

            this.onEntityHit(entity, player);
            if (timeBeforeTurnAround > 0 && DinosExpansionConfig.TURN_AROUND_MOB.get()) {
                timeBeforeTurnAround = 0;
            }
        }

        for (ItemEntity item : itemsPickedUp) {
            item.setMotion(0, 0, 0);
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
        hitEntity.attackEntityFrom(this.causeNewDamage(this, player), (float) this.getDamage(hitEntity, player));
    }

    protected double getDamage(Entity entity, PlayerEntity player){
        return this.damage;
    }

    public DamageSource causeNewDamage(BoomerangEntity boomerang, Entity entity){
        return new IndirectEntityDamageSource("boomerang", boomerang, entity).setProjectile();
    }

    public void setEntityDead() {
        if (this.getReturnTo() != null) {
            if (getRenderedItemStack() != null) {
                if (!this.getReturnTo().getHeldItemMainhand().getItem().equals(Items.AIR)) {
                    if (!this.getReturnTo().getHeldItemOffhand().getItem().equals(Items.AIR)) {
                        this.getReturnTo().dropItem(getRenderedItemStack(), true);
                    } else {
                        this.getReturnTo().setHeldItem(Hand.OFF_HAND, getRenderedItemStack());
                    }
                } else {
                    this.getReturnTo().setHeldItem(Hand.MAIN_HAND, getRenderedItemStack());
                }
            }
        }
        super.setDead();
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ROTATION, 0.0F);
        this.dataManager.register(RETURN_UNIQUE_ID, Optional.empty());
        this.dataManager.register(BOOMERANG_STACK, ItemStack.EMPTY);
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
        this.rotationYaw = -57.29578F * (float) Math.atan2(motion.x, motion.z);
        double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
        this.rotationPitch = -57.29578F * (float) Math.atan2(motion.y, d1);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.damage = compound.getDouble("damage");
        this.speed = compound.getDouble("speed");
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
        ListNBT itemsGathered = compound.getList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < itemsGathered.size(); ++i) {
            CompoundNBT tag = itemsGathered.getCompound(i);
            ItemEntity item = new ItemEntity(this.world, 0.0D, 0.0D, 0.0D);
            item.readAdditional(tag);
            this.itemsPickedUp.add(item);
        }

        this.hand = Hand.valueOf(compound.getString("hand"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putDouble("damage", this.damage);
        compound.putDouble("speed", this.speed);
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

        for (ItemEntity itemEntity : this.itemsPickedUp) {
            if (itemEntity != null) {
                CompoundNBT tag = new CompoundNBT();
                itemEntity.writeAdditional(compound);
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

    public ItemStack getRenderedItemStack(){
        return this.dataManager.get(BOOMERANG_STACK);
    }

}
