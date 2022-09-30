package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.effect.FreezeEffect;
import com.rena.dinosexpansion.common.effect.ParalysisEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, DinosExpansion.MOD_ID);

    public static final RegistryObject<ParalysisEffect> PARALYSIS = EFFECTS.register("paralysis", ParalysisEffect::new);
    public static final RegistryObject<FreezeEffect> FREEZE = EFFECTS.register("freeze", FreezeEffect::new);
}
