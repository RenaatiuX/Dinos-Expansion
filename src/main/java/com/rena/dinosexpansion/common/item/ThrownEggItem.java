package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.projectile.ThrowEggEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ThrownEggItem extends Item {

    protected Supplier<EntityType<?>> spawningEntity;
    protected int minEntities, maxEntities;
    protected double chanceEntity, chanceTamedEntity;

    protected boolean allTameOrNot;

    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity) {
        this(properties, spawningEntity, 0, 1);
    }

    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity, int exactAmountEntities) {
        this(properties, spawningEntity, exactAmountEntities, exactAmountEntities);
    }

    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity, int minEntities, int maxEntities) {
        this(properties, spawningEntity, minEntities, maxEntities, .01d);
    }

    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity, int minEntities, int maxEntities, double chanceEntity) {
        this(properties, spawningEntity, minEntities, maxEntities, chanceEntity, .3d);
    }

    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity, int minEntities, int maxEntities, double chanceEntity, double chanceTamedEntity) {
        this(properties, spawningEntity, minEntities, maxEntities, chanceEntity, chanceTamedEntity, true);
    }

    /**
     * look at {@link com.rena.dinosexpansion.common.entity.projectile.ThrowEggEntity} for explanation of all of these fields
     */
    public ThrownEggItem(Properties properties, Supplier<EntityType<?>> spawningEntity, int minEntities, int maxEntities, double chanceEntity, double chanceTamedEntity, boolean allTameOrNot) {
        super(properties);
        this.spawningEntity = spawningEntity;
        this.minEntities = minEntities;
        this.maxEntities = maxEntities;
        this.chanceEntity = chanceEntity;
        this.chanceTamedEntity = chanceTamedEntity;
        this.allTameOrNot = allTameOrNot;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            ThrowEggEntity eggentity = new ThrowEggEntity(playerIn, worldIn);
            eggentity.setItem(itemstack);
            eggentity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            eggentity.setChanceEntity(this.chanceEntity);
            eggentity.setChanceTamedEntity(this.chanceTamedEntity);
            eggentity.setSpawningEntity(this.spawningEntity);
            eggentity.setMinEntitiesSpawn(this.minEntities);
            eggentity.setMaxEntitiesSpawn(this.maxEntities);
            eggentity.setAllTamedOrNot(this.allTameOrNot);
            worldIn.addEntity(eggentity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }


}
