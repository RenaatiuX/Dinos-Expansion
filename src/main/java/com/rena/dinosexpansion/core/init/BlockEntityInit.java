package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DinosExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<MortarTileEntity>> MORTAR = TES.register("mortar", () -> TileEntityType.Builder.create(MortarTileEntity::new, BlockInit.MORTAR.get()).build(null));
}
