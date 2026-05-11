package io.github.myname.mymod;

import io.github.myname.mymod.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(ModConstants.MODID)
public class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ModConstants.MODID);

    public ModMain(FMLJavaModLoadingContext context) {
        final IEventBus modBus = context.getModEventBus();

        ITEM_REGISTRY.register(modBus);
        ENTITY_TYPE_REGISTRY.register(modBus);

        ModCommon.init();
        modBus.addListener(EventPriority.NORMAL, false, EntityAttributeCreationEvent.class, event -> ModEntities.registerEntityAttributes(event::put));
    }
}
