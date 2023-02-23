package com.rena.dinosexpansion.common.entity.terrestrial.ambient;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.events.HostileAnimalEvent;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Campanile extends AmbientDinosaur implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> IS_IN_SHELL = EntityDataManager.createKey(Campanile.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected int ticksInShell, shellCooldown, forceTicksInShell;

    public Campanile(EntityType<Campanile> type, World world) {
        super(type, world, new DinosaurInfo("campanile", 20, 20, 10, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 50));
        updateInfo();
    }

    public Campanile(World world) {
        this(EntityInit.CAMPANILE.get(), world);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new CampanileHideGoal(this, 200, 200));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.8D){
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.5D) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.1D);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_IN_SHELL, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("InShell", this.isInShell());
        compound.putInt("ticksInShell", this.ticksInShell);
        compound.putInt("shellCooldown", this.shellCooldown);
        compound.putInt("forceTicksInShell", this.forceTicksInShell);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setInShell(compound.getBoolean("InShell"));
        this.ticksInShell = compound.getInt("ticksInShell");
        this.shellCooldown = compound.getInt("shellCooldown");
        this.forceTicksInShell = compound.getInt("forceTicksInShell");
    }

    public boolean isInShell() {
        return this.dataManager.get(IS_IN_SHELL);
    }

    public void setInShell(boolean inShell) {
        this.dataManager.set(IS_IN_SHELL, inShell);
    }

    protected void setShellCooldown(int shellCooldown) {
        this.shellCooldown = shellCooldown;
    }

    protected void setTicksInShell(int ticksInShell) {
        this.ticksInShell = ticksInShell;
    }

    public boolean hasShellCooldown(){
        return shellCooldown > 0;
    }

    public boolean canStayInShell(){
        return ticksInShell > 0;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!world.isRemote){
            if (ticksInShell > 0)
                ticksInShell--;
            if (shellCooldown > 0)
                shellCooldown --;
            if(forceTicksInShell > 0)
                forceTicksInShell--;
        }
    }

    public boolean isForcedInShell(){
        return this.forceTicksInShell > 0;
    }

    /**
     * forces the campanile to stay in the shell
     * @param ticksForcedInShell the amount of ticks the campanils is forced to stay in the shell
     */
    public void setForcedInShell(int ticksForcedInShell){
        this.forceTicksInShell = ticksForcedInShell;
        this.setInShell(true);
        this.navigator.clearPath();
        this.setMotion(0,0,0);
    }

    @Override
    public List<Item> getFood() {
        return Lists.newArrayList(Items.GRASS);
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return this.rand.nextDouble() < 0.01 ? narcoticValue - 1 : narcoticValue;
    }

    public boolean isThereNearbyMobs() {
        List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(2.0D, 2.0D, 2.0D), null);
        return list.stream().anyMatch(this::isAScaryAnimal);
    }

    public boolean isAScaryAnimal(LivingEntity entity) {
        if (entity == this)
            return false;
        boolean scared = false;
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative() && !isOwner(entity)) {
            scared = true;
        } else if (entity instanceof Dinosaur) {
            scared = true;
        } else if (entity.getWidth() >= 1.5 && (!(entity instanceof PlayerEntity))) {
            scared = true;
        }
        if (entity.isSneaking())
            scared = false;
        HostileAnimalEvent event = new HostileAnimalEvent(this, entity, scared);
        MinecraftForge.EVENT_BUS.post(event);
        return event.isHostile();
    }

    @Override
    public void applyKnockback(float strength, double ratioX, double ratioZ) {
        if (!isInShell())
            super.applyKnockback(strength, ratioX, ratioZ);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0 && this.isInShell() && source.getTrueSource() != null) {
            this.setForcedInShell(60);
            this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, this.getRNG().nextFloat() + 0.8F);
            if (this.getRidingEntity() != null) {
                this.getRidingEntity().attackEntityFrom(source, amount * .9f);
            }
            if (source.isProjectile())
                return false;
            else
                return super.attackEntityFrom(source, amount * .1f);
        }
        if (!this.isInShell()) {
            this.setForcedInShell(60);
        }
        return super.attackEntityFrom(source, amount);
    }

    private PlayState predicate(AnimationEvent<Campanile> event) {
        if (this.isInShell()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.hiding", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.move", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }


    public static class CampanileHideGoal extends Goal {

        protected final Campanile campanile;
        protected int maxTicksInShell, shellCooldown;

        public CampanileHideGoal(Campanile campanile, int maxTicksInShell, int shellCooldown) {
            this.campanile = campanile;
            this.maxTicksInShell = maxTicksInShell;
            this.shellCooldown = shellCooldown;
        }

        @Override
        public boolean shouldExecute() {
            //when not in shell || when in shell
            return (!campanile.isInShell() && campanile.isOnGround() && !campanile.hasShellCooldown() && campanile.isThereNearbyMobs()) || // when campanile isnt in shell it will only go into its shell when on ground and there are nearby hostile mobs
                    (campanile.isInShell() && !campanile.isForcedInShell() && (!campanile.canStayInShell() || !campanile.isThereNearbyMobs())); // when campanile is in shell it will go out of it either when no hostile mobs are nearby or the maxTicks that he can stay in the shell are reached
        }

        @Override
        public void startExecuting() {
            if (campanile.isInShell()){
                campanile.setShellCooldown(this.shellCooldown);
                campanile.setInShell(false);
            }else {
                campanile.setTicksInShell(this.maxTicksInShell);
                campanile.setInShell(true);
            }
        }
    }
}
