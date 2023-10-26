package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.DinoInventoryContainer;
import com.rena.dinosexpansion.common.container.MortarContainer;
import com.rena.dinosexpansion.common.container.OrderContainer;
import com.rena.dinosexpansion.common.container.TamingContainer;
import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, DinosExpansion.MOD_ID);

    public static final RegistryObject<ContainerType<TamingContainer>> TAMING_CONTAINER = CONTAINERS.register("taming_container", () -> IForgeContainerType.create(TamingContainer::new));
    public static final RegistryObject<ContainerType<OrderContainer>> ORDER_CONTAINER = CONTAINERS.register("order_container", () -> IForgeContainerType.create(OrderContainer::new));
    public static final RegistryObject<ContainerType<MortarContainer>> MORTAR_CONTAINER = CONTAINERS.register("mortar_container", () -> IForgeContainerType.create(MortarContainer::new));
    public static final RegistryObject<ContainerType<DinoInventoryContainer>> DINO_INVENTORY_CONTAINER = CONTAINERS.register("dino_inventory_container", () -> IForgeContainerType.create(DinoInventoryContainer::new));
}
