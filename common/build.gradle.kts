plugins {
    id("convention-plugin")

    alias(libs.plugins.moddevgradle)
}

val modId: String by project

if (modId != "mymod" && file("src/main/resources/mymod.mixins.json").exists())
    throw IllegalStateException("You haven't renamed your `mymod.mixins.json` file to $modId.mixins.json yet!")

neoForge {
    neoFormVersion = libs.versions.neoform.get()
    validateAccessTransformers = true
    accessTransformers.files.setFrom("src/main/resources/META-INF/accesstransformer-common.cfg")

    parchment.minecraftVersion.set(libs.versions.parchment.minecraft.get())
    parchment.mappingsVersion.set(libs.versions.parchment.asProvider().get())
}

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.mixinextras.common)
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