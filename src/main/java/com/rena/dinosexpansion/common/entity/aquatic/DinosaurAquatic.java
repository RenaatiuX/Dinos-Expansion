package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.common.entity.ia.navigator.SemiAquaticPathNavigator;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public abstract class DinosaurAquatic extends Dinosaur implements ISemiAquatic {

    private static final int MAX_TIME_ON_LAND = 1000;
    private static final int MAX_TIME_IN_WATER = 1000;
    public boolean movesOnLand;
    public float onLandProgress;
    public int timeInWater = 0;
    public int timeOnLand = 0;
    protected boolean isAmphibious = false;
    protected boolean isLandNavigator;
    protected int breachCooldown = 0;
    protected boolean isGoingDownAfterBreach = false;
    protected float jumpX;
    protected float jumpY;
    protected float jumpZ;

    public DinosaurAquatic(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        switchNavigator(true);
    }

    @Override
    public Iterable<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return null;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    public boolean doesBreachAttack() {
        return false;
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            PathNavigator prevNav = this.navigator;
            this.navigator = new GroundPathNavigator(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new AquaticMoveController(this, 1F);
            PathNavigator prevNav = this.navigator;
            this.navigator = new SemiAquaticPathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean shouldEnterWater() {
        if(!isAmphibious){
            return true;
        }
        return this.timeInWater == 0 && timeOnLand > MAX_TIME_ON_LAND;
    }

    @Override
    public boolean shouldLeaveWater() {
        return isAmphibious && this.timeInWater > MAX_TIME_IN_WATER && timeOnLand < MAX_TIME_ON_LAND;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return 20;
    }

    /*protected double getStrongAttack(){
        return this.getAttributeManager().getAttributeValue(Attributes.ATTACK_DAMAGE);
    }*/

    public static AttributeModifierMap.MutableAttribute createAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public abstract double swimSpeed();

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    /*public void destroyBoat(Entity sailor) {
        if (sailor.getRidingEntity() != null && sailor.getRidingEntity() instanceof BoatEntity && !world.isRemote) {
            BoatEntity boat = (BoatEntity) sailor.getRidingEntity();
            boat.remove();
            if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                for (int i = 0; i < 3; ++i) {
                    boat.entityDropItem(new ItemStack(Block.getBlockFromItem(ItemTags.PLANKS), 1, boat.getBoatType()), 0.0F);
                }
                for (int j = 0; j < 2; ++j) {
                    boat.entityDropItem(Items.STICK, 1);
                }
            }
        }
    }*/

    @Override
    public void livingTick() {
        super.livingTick();
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("TimeOnLand", this.timeOnLand);
        nbt.putInt("TimeInWater", this.timeInWater);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.timeOnLand = nbt.getInt("TimeOnLand");
        this.timeInWater = nbt.getInt("TimeInWater");
    }

    protected boolean useSwimAI() {
        return this.isInWater();
    }

    @Override
    public boolean isInWater() {
        return super.isInWater();
    }

    @Override
    public boolean canBeSteered() {
        return this.isInWater() && super.canBeSteered();
    }

    @Override
    public void travel(Vector3d travelVector) {
        super.travel(travelVector);
    }

    @Override
    public Vector3d getPositionVec() {
        return new Vector3d(this.getPosX(), this.getPosY() + 0.5D, this.getPosZ());
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        if (!canBreathOnLand()) {
            if (this.isAlive() && !this.isInWater()) {
                --i;
                this.setAir(i);

                if (this.getAir() == -40) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAir(500);
            }
        }
    }

    public boolean canBreathOnLand() {
        return true;
    }

    public boolean canDinoHunt(Entity target, boolean hunger) {
        //missing code
        return true;
    }

    public boolean canHuntMobsOnLand() {
        return true;
    }
}
