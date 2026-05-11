package io.github.myname.mymod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(ModConstants.MODID)
public class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);

    public ModMain(FMLJavaModLoadingContext context) {
        final IEventBus modBus = context.getModEventBus();

        ITEM_REGISTRY.register(modBus);

        ModCommon.init();
    }
}
