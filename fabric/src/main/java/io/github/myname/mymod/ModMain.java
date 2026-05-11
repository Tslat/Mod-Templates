package io.github.myname.mymod;

import io.github.myname.mymod.entity.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModMain implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConstants.init();
        ModCommon.init();

        ModEntities.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
    }
}
