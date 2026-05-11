package io.github.myname.mymod.platform;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Generic interface for each platform to extend, for platform-agnostic code.
 */
public interface PlatformHelper {
    /**
     * Register a new item
     */
    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item);
}
