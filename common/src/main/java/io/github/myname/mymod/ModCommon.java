package io.github.myname.mymod;

import io.github.myname.mymod.entity.ModEntities;
import io.github.myname.mymod.item.ModItems;

public final class ModCommon {
    public static void init() {
        doRegistrations();
    }

    public static void doRegistrations() {
        ModItems.init();
        ModEntities.init();
    }
}
