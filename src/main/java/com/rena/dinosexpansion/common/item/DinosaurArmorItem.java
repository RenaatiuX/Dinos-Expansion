package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DinosaurArmorItem extends Item {

    private final int armorValue;
    private final float toughnessValue;
    private final ResourceLocation location;

    public DinosaurArmorItem(int armorValue, float toughnessValue, String tierArmor, Properties properties) {
        this(armorValue, toughnessValue, new ResourceLocation(DinosExpansion.MOD_ID + "textures/entity/dinosaur/armor/armor_" + tierArmor + ".png"), properties);
    }

    public DinosaurArmorItem(int armorValue, float toughnessValue, ResourceLocation texture, Properties properties) {
        super(properties);
        this.armorValue = armorValue;
        this.toughnessValue = toughnessValue;
        this.location = texture;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getArmorTexture() {
        return location;
    }

    public int getArmorValue() {
        return armorValue;
    }

    public float getToughnessValue() {
        return toughnessValue;
    }
}
