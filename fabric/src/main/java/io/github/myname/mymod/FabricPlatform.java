package io.github.myname.mymod;

import io.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricPlatform implements PlatformHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        T registeredItem = Registry.register(BuiltInRegistries.ITEM, ModConstants.id(id), item.get());

        return () -> registeredItem;
    }
}
