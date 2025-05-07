package com.github.myname.mymod.client;

import com.github.myname.mymod.MyModConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = MyModConstants.MODID, dist = Dist.CLIENT)
public class MyModClient {
    public MyModClient(IEventBus modBus, ModContainer modContainer) {

    }
}
