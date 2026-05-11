package io.github.myname.mymod.entity;

import io.github.myname.mymod.ModConstants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/// The mod's registered entities
public final class ModEntities {
    public static void init() {}

    //public static final Supplier<EntityType<ExampleEntity>> EXAMPLE = register("example", () -> EntityType.Builder.of(ExampleEntity::new, MobCategory.MISC).sized(0.6f, 1.8f));

    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> registrar) {
        //registrar.accept(EXAMPLE.get(), ExampleEntity.createAttributes().build());
    }

    //<editor-fold defaultstate="collapsed" desc="<Registration Methods>">
    /// Register a basic [EntityType]
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        return register(id, () -> EntityType.Builder.of(factory, category).sized(width, height));
    }

    /// Register a basic [EntityType], additionally specifying a custom eye height
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, float eyeHeight) {
        return register(id, () -> EntityType.Builder.of(factory, category).sized(width, height).eyeHeight(eyeHeight));
    }

    /// Register an [EntityType]
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, UnaryOperator<EntityType.Builder<T>> modifier) {
        return register(id, () -> modifier.apply(EntityType.Builder.of(factory, category).sized(width, height)));
    }

    /// Register an [EntityType]
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, float eyeHeight, UnaryOperator<EntityType.Builder<T>> modifier) {
        return register(id, () -> modifier.apply(EntityType.Builder.of(factory, category).sized(width, height).eyeHeight(eyeHeight)));
    }

    /// Register a custom [EntityType]
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, Supplier<EntityType.Builder<T>> entityTypeBuilder) {
        return ModConstants.PLATFORM.registerEntityType(id, entityTypeBuilder);
    }
    //</editor-fold>
}
