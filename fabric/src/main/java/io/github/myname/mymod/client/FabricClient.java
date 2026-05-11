package io.github.myname.mymod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistration.registerRenderers(EntityRendererRegistry::register, BlockEntityRenderers::register);
    }
}
