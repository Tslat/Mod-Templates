package com.github.myname.mymod.item;

import com.github.myname.mymod.ModConstants;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/// The mod's registered items
public final class ModItems {
    public static void init() {}

    //public static final Supplier<Item> EXAMPLE_ITEM = register("example", () -> new Item(new Item.Properties()));
    //public static final Supplier<Item> ANOTHER_EXAMPLE_ITEM = register("example", Item::new, properties -> properties.stacksTo(64));

    //<editor-fold defaultstate="collapsed" desc="<Registration Methods>">
    /// Register a basic [Item]
    private static Supplier<Item> registerBasic(String id) {
        return register(id, () -> new Item(new Item.Properties()));
    }

    /// Register a basic [Item] with custom [Item.Properties]
    private static Supplier<Item> registerBasic(String id, Item.Properties properties) {
        return register(id, () -> new Item(properties));
    }

    /// Register an item
    private static <T extends Item> Supplier<T> register(String id, Function<Item.Properties, T> itemFactory) {
        return register(id, () -> itemFactory.apply(new Item.Properties()));
    }

    /// Register an item
    private static <T extends Item> Supplier<T> register(String id, Function<Item.Properties, T> itemFactory, UnaryOperator<Item.Properties> properties) {
        return register(id, () -> itemFactory.apply(properties.apply(new Item.Properties())));
    }

    /// Register an item
    private static <T extends Item> Supplier<T> register(String id, Supplier<T> item) {
        return ModConstants.PLATFORM.registerItem(id, item);
    }
    //</editor-fold>
}
