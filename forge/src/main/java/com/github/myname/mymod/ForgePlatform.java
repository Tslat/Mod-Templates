package com.github.myname.mymod;

import com.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/// Forge [SPI](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html) implementation for [PlatformHelper]
public final class ForgePlatform implements PlatformHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ModMain.ITEM_REGISTRY.register(id, item);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityType) {
        return ModMain.ENTITY_REGISTRY.register(id, entityType);
    }
}
