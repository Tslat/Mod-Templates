package com.github.myname.mymod;

import net.fabricmc.api.ModInitializer;

public class MyMod implements ModInitializer {
    @Override
    public void onInitialize() {
        MyModConstants.init();
        MyModCommon.init();
    }
}
