package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.api.BoatTypeRegistry;
import com.rena.dinosexpansion.common.entity.misc.CustomBoatEntity;
import com.rena.dinosexpansion.common.entity.util.BoatType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CustomBoatItem extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final CustomBoatEntity.BoatTypeMaterial material;

    public CustomBoatItem(CustomBoatEntity.BoatTypeMaterial material, Properties properties) {
        super(properties);
        this.material = material;
    }



    /**
     * this will create a material and also directly registers it
     * @param name specified in {@link CustomBoatEntity.BoatTypeMaterial#getName()}
     * @param planks specified in {@link CustomBoatEntity.BoatTypeMaterial#getPlanks()}
     * @param texture specified in {@link CustomBoatEntity.BoatTypeMaterial#getBoatTexture()}
     * @param properties
     */
    public CustomBoatItem(String name, Supplier<IItemProvider> planks, ResourceLocation texture, Properties properties) {
        super(properties);
        this.material = new CustomBoatEntity.BoatTypeMaterial() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public IItemProvider getBoatItem() {
                return CustomBoatItem.this;
            }

            @Override
            public IItemProvider getPlanks() {
                return planks.get();
            }

            @Override
            public ResourceLocation getBoatTexture() {
                return texture;
            }
        };
        BoatTypeRegistry.register(this.material);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else {
            Vector3d vector3d = playerIn.getLook(1.0F);
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vector3d.scale(5.0D)).grow(1.0D), field_219989_a);
            if (!list.isEmpty()) {
                Vector3d vector3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow((double)entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vector3d1)) {
                        return ActionResult.resultPass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                CustomBoatEntity boatentity = new CustomBoatEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
                boatentity.setBoat(this.material);
                boatentity.rotationYaw = playerIn.rotationYaw;
                if (!worldIn.hasNoCollisions(boatentity, boatentity.getBoundingBox().grow(-0.1D))) {
                    return ActionResult.resultFail(itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.addEntity(boatentity);
                        if (!playerIn.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
                }
            } else {
                return ActionResult.resultPass(itemstack);
            }
        }
    }
}
