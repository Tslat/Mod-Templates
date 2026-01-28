import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.neoforged.moddevgradle.dsl.RunModel

plugins {
    id("project-setup")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.moddevgradle)
}

val modId:          String by project
val modDisplayName: String by project

neoForge {
    version = libs.versions.neoforge.asProvider().get()

    project(":common").file("src/main/resources/META-INF/accesstransformer.cfg").takeIf { it.exists() }?.let {
        accessTransformers.files.setFrom(it)
        validateAccessTransformers = true
    }

    parchment.minecraftVersion.set(libs.versions.parchment.minecraft.get())
    parchment.mappingsVersion.set(libs.versions.parchment.asProvider().get())

    runs {
        configureEach {
            logLevel = org.slf4j.event.Level.DEBUG
        }

        mods.create(modId).sourceSet(project.sourceSets.main.get())

        runConfig(this, "client") {
            client()
            programArguments.addAll("--username", "Dev")
        }

        runConfig(this, "client2") {
            client()
            programArguments.addAll("--username", "Player")
        }

        runConfig(this, "server") {
            server()
            programArgument("--nogui")
        }
    }
}

dependencies {
    compileOnly(project(":common"))

    // Mod Dependencies below
    //implementation(modDeps.geckolib.neoforge)

}

modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"
    projectId.set(properties["modrinthProjectId"] as String)
    versionNumber.set(project.version.toString())
    versionName = "NeoForge ${libs.versions.minecraft.asProvider().get()}"
    uploadFile.set(tasks.named<Jar>("jar"))
    gameVersions.set(listOf(libs.versions.minecraft.asProvider().get()))
    loaders.set(listOf("neoforge"))

    if (rootProject.file("CHANGELOG.md").exists())
        changelog = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

    // Comment out below to enable publishing properly
    debugMode = true
    // See below for other properties and info
    // https://github.com/modrinth/minotaur#available-properties
}

tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(properties["curseforgeProjectId"], tasks.jar)
    mainFile.displayName = "$modDisplayName NeoForge ${libs.versions.minecraft.asProvider().get()} ${project.version}"
    mainFile.releaseType = "release"
    mainFile.addModLoader("NeoForge")
    mainFile.addGameVersion(libs.versions.minecraft.asProvider().get())
    mainFile.addJavaVersion("Java ${libs.versions.java}")

    if (rootProject.file("CHANGELOG.md").exists())
        mainFile.changelog = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

    // Comment out below to enable publishing properly
    debugMode = true
    // See below for other properties and info
    // https://github.com/Darkhax/CurseForgeGradle#available-properties
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

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}

// Not explicitly needed; but due to Gradle's failure to provide kotlin-dsl reified types for NamedDomainObjectContainer, you'll get a bunch of IDE errors without it
fun runConfig(container: NamedDomainObjectContainer<RunModel>, name: String, configuration: Action<RunModel>) {
    configuration.execute(container.create(name));
}