package com.github.myname.mymod;

import com.github.myname.mymod.entity.ModEntities;
import com.github.myname.mymod.item.ModItems;

/// Platform-agnostic common-code setup
public final class ModCommon {
    /// Call out to all common-code platform-agnostic mod initialization tasks
    public static void init() {
        doRegistrations();
    }

    /// Call out to the various common-code platform-agnostic registration tasks
    public static void doRegistrations() {
        ModItems.init();
        ModEntities.init();
    }
}
