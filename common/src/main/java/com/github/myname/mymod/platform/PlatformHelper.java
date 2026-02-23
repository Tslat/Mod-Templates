package com.github.myname.mymod.platform;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/// Generic interface for each platform to extend, for platform-agnostic code.
public interface PlatformHelper {
    /// Register a new item
    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item);

    /// Register a new entity
    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityType);
}
