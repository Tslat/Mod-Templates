package io.github.myname.mymod;

import io.github.myname.mymod.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

/**
 * Mod constants class.
 * <p>
 * All the mod's shared static instances are stored here
 */
public final class ModConstants {
    public static final String MODID = "mymod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    private static final ResourceLocation BASE_ID = ResourceLocation.fromNamespaceAndPath(MODID, "");

    public static final PlatformHelper PLATFORM = ServiceLoader.load(PlatformHelper.class).findFirst().orElseThrow();

    public static void init() {}

    /**
     * Create a new {@link ResourceLocation} with this mod's namespace
     */
    public static ResourceLocation id(String path) {
        return BASE_ID.withPath(path);
    }
}
