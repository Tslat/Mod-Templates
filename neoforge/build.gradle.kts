import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("project-setup")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.moddevgradle)
}

val modId: String by project

neoForge {
    version = libs.versions.neoforge.asProvider().get()

    project(":common").file("src/main/resources/META-INF/accesstransformer-common.cfg").takeIf { it.exists() }?.let {
        accessTransformers.files.setFrom(it)
        validateAccessTransformers = true
    }

    parchment.minecraftVersion.set(libs.versions.parchment.minecraft.get())
    parchment.mappingsVersion.set(libs.versions.parchment.asProvider().get())

    runs {
        mods.create(modId).sourceSet(project.sourceSets.main.get())

        configureEach {
            logLevel = org.slf4j.event.Level.DEBUG
        }

        create("client") {
            client()
        }

        create("server") {
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

tasks.withType<Test>().configureEach {
    enabled = false;
}

tasks.named<JavaCompile>("compileJava").configure {
    source(project(":common").sourceSets.main.get().allSource)
}

tasks.named<Jar>("sourcesJar").configure {
    from(project(":common").sourceSets.main.get().allSource)
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.main.get().allJava)
}

tasks.withType<ProcessResources>().configureEach {
    from(project(":common").sourceSets.main.get().resources)
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