package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.effect.FreezeEffect;
import com.rena.dinosexpansion.common.effect.ParalysisEffect;
import com.rena.dinosexpansion.common.enchantments.ResilientSkin;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, DinosExpansion.MOD_ID);

    public static final RegistryObject<ParalysisEffect> PARALYSIS = EFFECTS.register("paralysis", ParalysisEffect::new);
    public static final RegistryObject<FreezeEffect> FREEZE = EFFECTS.register("freeze", FreezeEffect::new);

    public static final RegistryObject<Effect> FEAR = EFFECTS.register("fear", () -> new CustomEffect(EffectType.HARMFUL, 0));


    public static class CustomEffect extends Effect{

        public CustomEffect(EffectType typeIn, int liquidColorIn) {
            super(typeIn, liquidColorIn);
        }
    }
}
