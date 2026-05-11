package io.github.myname.mymod;

import io.github.myname.mymod.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

/// Initializer class for Forge-specific setup tasks
@Mod(ModConstants.MODID)
public final class ModMain {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM, ModConstants.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ModConstants.MODID);

    public ModMain(FMLJavaModLoadingContext context) {
        final BusGroup busGroup = context.getModBusGroup();

        ITEM_REGISTRY.register(busGroup);
        ENTITY_TYPE_REGISTRY.register(busGroup);

        EntityAttributeCreationEvent.BUS.addListener(event -> ModEntities.registerEntityAttributes(event::put));
        ModCommon.init();
    }
}
