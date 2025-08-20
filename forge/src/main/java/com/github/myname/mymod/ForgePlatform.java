package com.github.myname.mymod;

import com.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ForgePlatform implements PlatformHelper {

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ModMain.ITEM_REGISTRY.register(id, item);
    }
}
