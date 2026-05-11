package io.github.myname.mymod;

import io.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricPlatform implements PlatformHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        T registeredItem = Registry.register(BuiltInRegistries.ITEM, ModConstants.id(id), item.get());

        return () -> registeredItem;
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType.Builder<T>> entityType) {
        final EntityType<T> registeredEntity = Registry.register(BuiltInRegistries.ENTITY_TYPE, ModConstants.id(id), entityType.get().build(id));

        return () -> registeredEntity;
    }
}
