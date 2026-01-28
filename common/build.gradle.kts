plugins {
    id("project-setup")

    alias(libs.plugins.moddevgradle)
}

val modId: String by project

neoForge {
    neoFormVersion = libs.versions.neoform.get()

    file("src/main/resources/META-INF/accesstransformer.cfg").takeIf { it.exists() }?.let {
        accessTransformers.files.setFrom(it.path)
        validateAccessTransformers = true
    }

    parchment.minecraftVersion.set(libs.versions.parchment.minecraft.get())
    parchment.mappingsVersion.set(libs.versions.parchment.asProvider().get())
}

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.mixinextras.common)

    // Mod Dependencies below
    //implementation(modDeps.geckolib.common)

}

publishing {
    publishing {
        publications {
            create<MavenPublication>(modId) {
                from(components["java"])
                artifactId = base.archivesName.get()
            }
        }
    }
}