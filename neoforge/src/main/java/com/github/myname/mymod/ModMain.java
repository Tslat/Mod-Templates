package com.github.myname.mymod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ModConstants.MODID)
public class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);

    public ModMain(IEventBus modBus, ModContainer modContainer) {
        ITEM_REGISTRY.register(modBus);

        ModCommon.init();
    }
}
