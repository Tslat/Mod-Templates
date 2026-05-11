package io.github.myname.mymod.client;

import io.github.myname.mymod.ModConstants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/// Initializer class for Forge-specific client setup tasks
@Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ForgeClient {
    /// Register [Entity] and [BlockEntity] renderers
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistration.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }
}