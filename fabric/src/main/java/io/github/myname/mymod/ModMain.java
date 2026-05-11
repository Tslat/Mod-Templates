package io.github.myname.mymod;

import net.fabricmc.api.ModInitializer;

public class ModMain implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConstants.init();
        ModCommon.init();
    }
}
