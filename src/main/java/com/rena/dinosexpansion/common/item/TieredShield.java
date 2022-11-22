package com.rena.dinosexpansion.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TieredShield extends Item {

    private final ShieldTier shieldTier;

    public TieredShield(Properties properties, ShieldTier shieldTier) {
        super(properties.maxDamage(shieldTier.getDurability()));
        this.shieldTier = shieldTier;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    public void addShieldBashTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("tooltip.dinosexpansion.shield_bash", TextFormatting.AQUA + I18n.format(Minecraft.getInstance().gameSettings.keyBindAttack.getTranslationKey(), new Object[0]) + TextFormatting.BLUE)).mergeStyle(TextFormatting.BLUE));
    }

    public ShieldTier getShieldTier() {
        return shieldTier;
    }

    public interface ShieldTier{

        int getDurability();

        int getDamageBash();

    }
}
