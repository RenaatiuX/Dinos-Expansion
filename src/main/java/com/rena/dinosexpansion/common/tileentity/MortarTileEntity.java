package com.rena.dinosexpansion.common.tileentity;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.common.container.MortarContainer;
import com.rena.dinosexpansion.common.recipe.MortarRecipe;
import com.rena.dinosexpansion.core.init.BlockEntityInit;
import com.rena.dinosexpansion.core.init.RecipeInit;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class MortarTileEntity extends ContainerTileEntity implements IAnimatable, IAnimationTickable, ITickableTileEntity {

    protected AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected int counter, maxCounter, counterPercentage;

    public MortarTileEntity() {
        super(BlockEntityInit.MORTAR.get(), 3);
        counter = 0;
        maxCounter = 1;
        counterPercentage = 0;
    }

    private PlayState predicate(AnimationEvent<MortarTileEntity> event){
        if (getBlockState().get(BlockStateProperties.POWERED)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("working", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void tick() {
        if (!world.isRemote){
            ItemStack input1 = getStackInSlot(0);
            ItemStack input2 = getStackInSlot(1);
            if (!input1.isEmpty() || !input2.isEmpty()){
                MortarRecipe recipe = getRecipe();
                if (recipe == null || !canProcess(recipe)){
                    counter = 0;
                }else{
                    if (counter <= 0){
                        startProcessing(recipe);
                        counter++;
                    }else if (counter <= maxCounter){
                        counter++;
                    }
                    if (counter >= maxCounter){
                        finishProcessing(recipe);
                        counter = 0;
                    }
                }
            }else {
                counter = 0;
            }
            counterPercentage = (int) ((double) counter * 100d / (double) maxCounter);
            BlockState state = world.getBlockState(getPos());
            if (state.get(BlockStateProperties.POWERED) != counter > 0) {
                world.setBlockState(pos, state.with(BlockStateProperties.POWERED, counter > 0),
                        Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);

            }
        }
    }

    protected void finishProcessing(MortarRecipe recipe){
        //respect that u can put only one ingredient
        if (recipe.getCounts().length == 1){
            if (getStackInSlot(0).isEmpty()){
                getStackInSlot(1).shrink(recipe.getCounts()[0]);
            }else {
                getStackInSlot(0).shrink(recipe.getCounts()[0]);
            }
        }else {
            shrinkCorrectOne(0, recipe);
            shrinkCorrectOne(1, recipe);
        }
        ItemStack output = getStackInSlot(2);
        if (output.getItem() == recipe.getRecipeOutput().getItem()){
            output.grow(recipe.getRecipeOutput().getCount());
        }else {
            setInventorySlotContents(2, recipe.getRecipeOutput().copy());
        }
    }

    protected void shrinkCorrectOne(int inventoryIndex, MortarRecipe recipe){
        if (recipe.getIngredients().get(0).test(getStackInSlot(inventoryIndex))){
            decrStackSize(inventoryIndex, recipe.getCounts()[0]);
        }else if (recipe.getIngredients().get(1).test(getStackInSlot(inventoryIndex))){
            decrStackSize(inventoryIndex, recipe.getCounts()[1]);
        }
    }

    protected void startProcessing(MortarRecipe recipe){
        this.maxCounter = recipe.getWorkingTime();
    }

    protected boolean canProcess(@Nonnull MortarRecipe recipe){
        if (getStackInSlot(2).isEmpty())
            return true;
        ItemStack output = getStackInSlot(2);
        if (ItemStack.areItemsEqual(output, recipe.getRecipeOutput()) && output.getCount() + recipe.getRecipeOutput().getCount() <= output.getMaxStackSize())
            return true;
        return false;
    }

    @Nullable
    protected MortarRecipe getRecipe(){
        return this.world.getRecipeManager().getRecipe(RecipeInit.MORTAR_RECIPE, this, this.world).orElse(null);
    }


    public void unlockRecipes(PlayerEntity player) {
        List<IRecipe<?>> list = this.grantStoredRecipeExperience(player.world, player.getPositionVec());
        player.unlockRecipes(list);
        this.recipes.clear();
    }

    public List<IRecipe<?>> grantStoredRecipeExperience(World world, Vector3d pos) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
            world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(World world, Vector3d pos, int craftedAmount, float experience) {
        int i = MathHelper.floor((float)craftedAmount * experience);
        float f = MathHelper.frac((float)craftedAmount * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        while(i > 0) {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            world.addEntity(new ExperienceOrbEntity(world, pos.x, pos.y, pos.z, j));
        }

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "mortar_controller", 0, this::predicate));
    }


    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
        return new MortarContainer(id, playerInv, this);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return 0;
    }

    @Override
    protected String setName() {
        return "mortar";
    }

    /**
     * only use client sided in order to sync progress bar
     */
    public void setCounterPercentage(int counterPercentage) {
        this.counterPercentage = counterPercentage;
    }

    public int getCounterPercentage() {
        return counterPercentage;
    }
}
