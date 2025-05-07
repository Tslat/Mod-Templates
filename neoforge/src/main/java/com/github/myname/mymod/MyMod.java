package com.github.myname.mymod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MyModConstants.MODID)
public class MyMod {
    public MyMod(IEventBus modBus, ModContainer modContainer) {
        MyModCommon.init();
    }
}
