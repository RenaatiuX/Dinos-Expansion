package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.tileentity.CampanileShellTileEntity;
import com.rena.dinosexpansion.common.tileentity.ModSignTileEntity;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DinosExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<MortarTileEntity>> MORTAR = TES.register("mortar", () -> TileEntityType.Builder.create(MortarTileEntity::new, BlockInit.MORTAR.get()).build(null));
    public static final RegistryObject<TileEntityType<CampanileShellTileEntity>> CAMPANILE_SHELL = TES.register("campanile_shell", () -> TileEntityType.Builder.create(CampanileShellTileEntity::new, BlockInit.CAMPANILE_SHELL_UNCOMMON.get(), BlockInit.CAMPANILE_SHELL_UNCOMMON.get()).build(null));
    public static final RegistryObject<TileEntityType<ModSignTileEntity>> MOD_SIGN_TE = TES.register("sign", () -> TileEntityType.Builder.create(ModSignTileEntity::new, BlockInit.CRATEAGUS_SIGN.get(), BlockInit.CRATEAGUS_WALL_SIGN.get()).build(null));
}
