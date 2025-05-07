package com.github.myname.mymod;

import com.github.myname.mymod.platform.PlatformHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

/**
 * Mod constants class.
 * <p>
 * All the mod's shared static instances are stored here
 */
public final class MyModConstants {
    public static final String MODID = "mymod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final PlatformHelper PLATFORM = ServiceLoader.load(PlatformHelper.class).findFirst().get();

    public static void init() {}
}
