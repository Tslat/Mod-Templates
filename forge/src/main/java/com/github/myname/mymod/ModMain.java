package com.github.myname.mymod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(ModConstants.MODID)
public class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);

    public ModMain(FMLJavaModLoadingContext context) {
        final BusGroup busGroup = context.getModBusGroup();

        ITEM_REGISTRY.register(busGroup);

        ModCommon.init();
    }
}
