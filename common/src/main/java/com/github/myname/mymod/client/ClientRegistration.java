package com.github.myname.mymod.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

/// Platform-agnostic client registration
public final class ClientRegistration {
    /// Register [EntityRenderers][EntityRenderer] and [BlockEntityRenderers][BlockEntityRenderer]
    @SuppressWarnings("rawtypes")
    public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> entityRenderers,
                                         BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> blockEntityRenderers) {
        //entityRenderers.accept(ModEntities.EXAMPLE.get(), ExampleRenderer::new);
    }
}
