package io.github.myname.mymod.platform;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/// Generic interface for each platform to extend, for platform-agnostic code.
public interface PlatformHelper {
    /// Register a new item
    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item);

    /// Register a new entity
    <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType);

    /// Create a new [CreativeModeTab.Builder] instance, patched by the modloader
    CreativeModeTab.Builder newCreativeTabBuilder();
}
