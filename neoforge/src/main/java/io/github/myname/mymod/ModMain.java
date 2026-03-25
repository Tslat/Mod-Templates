package io.github.myname.mymod;

import io.github.myname.mymod.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

/// Initializer class for NeoForge-specific setup tasks
@Mod(ModConstants.MODID)
public final class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ModConstants.MODID);

    public ModMain(IEventBus modBus, ModContainer modContainer) {
        ITEM_REGISTRY.register(modBus);
        ENTITY_REGISTRY.register(modBus);
        modBus.<EntityAttributeCreationEvent>addListener(event -> ModEntities.registerEntityAttributes(event::put));

        ModCommon.init();
    }
}
