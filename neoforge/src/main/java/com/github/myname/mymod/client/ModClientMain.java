package com.github.myname.mymod.client;

import com.github.myname.mymod.ModConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = ModConstants.MODID, dist = Dist.CLIENT)
public class ModClientMain {
    public ModClientMain(IEventBus modBus, ModContainer modContainer) {

    }
}
