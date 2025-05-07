package com.github.myname.mymod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MyModConstants.MODID)
public class MyMod {
    public MyMod(FMLJavaModLoadingContext context) {
        MyModCommon.init();
    }
}
