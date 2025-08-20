package com.github.myname.mymod;

import com.github.myname.mymod.item.ModItems;

public final class ModCommon {
    public static void init() {
        doRegistrations();
    }

    public static void doRegistrations() {
        ModItems.init();
    }
}
