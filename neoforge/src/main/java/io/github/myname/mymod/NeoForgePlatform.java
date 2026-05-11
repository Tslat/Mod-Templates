package io.github.myname.mymod;

import io.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class NeoForgePlatform implements PlatformHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ModMain.ITEM_REGISTRY.register(id, item);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType.Builder<T>> entityType) {
        return ModMain.ENTITY_TYPE_REGISTRY.register(id, () -> entityType.get().build(id));
    }
}
