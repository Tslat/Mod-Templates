package com.github.myname.mymod.item;

import com.github.myname.mymod.ModConstants;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * The mod's registered items
 */
public final class ModItems {
    public static void init() {}

    //public static final Supplier<Item> EXAMPLE_ITEM = register("example", () -> new Item(new Item.Properties()));

    private static <T extends Item> Supplier<T> register(String id, Supplier<T> item) {
        return ModConstants.PLATFORM.registerItem(id, item);
    }
}
