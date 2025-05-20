import net.fabricmc.loom.task.RemapJarTask
import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("convention-plugin")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.loom)
}

val modId: String by project

repositories {
    maven {
        name = "ParchmentMC"
        url = uri("https://maven.parchmentmc.org")
        content {
            includeGroupAndSubgroups("org.parchmentmc")
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered() {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.parchment.minecraft.get()}:${libs.versions.parchment.asProvider().get()}@zip")
    })
    modImplementation(libs.fabric)
    modImplementation(libs.fabric.api)
    compileOnly(project(":common"))
}

loom {
    file("src/main/resources/$modId.accesswidener").takeIf { it.exists() }?.also {
        accessWidenerPath.set(it)
    }?:takeIf { file("src/main/resources/$modId.accesswidener").exists() }?.run {
        throw IllegalStateException("You haven't renamed your `mymod.accesswidener` file to `$modId.accesswidener` yet!")
    }

    mixin.defaultRefmapName.set("${modId}.refmap.json")

    runs {
        named("client") {
            configName = "Fabric Client"

            client()
            ideConfigGenerated(true)
            runDir("runs/" + name)
            programArg("--username=Dev")
        }

        named("server") {
            configName = "Fabric Server"

            server()
            ideConfigGenerated(true)
            runDir("runs/" + name)
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    source(project(":common").sourceSets.getByName("main").allSource)
}

tasks.named<Jar>("sourcesJar").configure {
    from(project(":common").sourceSets.getByName("main").allSource)
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.getByName("main").allJava)
}

tasks.withType<ProcessResources>().configureEach {
   from(project(":common").sourceSets.getByName("main").resources)
    exclude("**/accesstransformer-common.cfg")
}

// Must have your Modrinth API Key as an environment variable
modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"
    projectId.set(properties["modrinthProjectId"] as String)
    versionNumber.set(project.version.toString())
    versionName = "Fabric ${libs.versions.minecraft.asProvider().get()}"
    uploadFile.set(tasks.named<RemapJarTask>("remapJar"))
    gameVersions.set(listOf(libs.versions.minecraft.asProvider().get()))
    versionType = "release"
    loaders.set(listOf("fabric"))
    dependencies {
        required.project("fabric-api")
    }

    if (rootProject.file("CHANGELOG.md").exists())
        changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))

    // Comment out below to enable publishing properly
    debugMode = true
    // See below for other properties and info
    // https://github.com/modrinth/minotaur#available-properties
}

// Must have your CurseForge API Key as an environment variable
tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(properties["curseforgeProjectId"], tasks.remapJar)
    mainFile.releaseType = "release"
    mainFile.addModLoader("Fabric")
    mainFile.addGameVersion(libs.versions.minecraft.asProvider().get())
    mainFile.addJavaVersion("Java ${libs.versions.java.get()}")

    if (rootProject.file("CHANGELOG.md").exists())
        mainFile.changelog = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

    // Comment out below to enable publishing properly
    debugMode = true
    // See below for other properties and info
    // https://github.com/Darkhax/CurseForgeGradle#available-properties
}

publishing {
    publications {
        create<MavenPublication>(modId) {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}