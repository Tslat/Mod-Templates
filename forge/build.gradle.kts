import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.minecraftforge.gradle.userdev.tasks.JarJar
import org.gradle.internal.extensions.stdlib.capitalized

plugins {
    id("project-setup")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.forgegradle)
    alias(libs.plugins.mixin)
    alias(libs.plugins.parchmentforgegradle)
}

val modId: String by project

jarJar.enable()

minecraft {
    mappings("parchment", "${libs.versions.parchment.minecraft.get()}-${libs.versions.parchment.asProvider().get()}-${libs.versions.minecraft.asProvider().get()}")

    file("src/main/resources/META-INF/accesstransformer.cfg").takeIf { it.exists() }?.let {
        accessTransformer(it)
    }

    reobf = false
    copyIdeResources = true

    runs {
        configureEach {
            workingDirectory(project.file("runs/$name"))
            ideaModule("${rootProject.name}.${project.name}.main")
            isSingleInstance = true
            args("-mixin.config=$modId.mixins.json")

            property("forge.logging.console.level", "debug")
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${project.projectDir}/build/createSrgToMcp/output.srg")

            mods.create(modId) {
                source(sourceSets.main.get())
                source(project(":common").sourceSets.main.get())
            }
        }

        create("Forge Client") {
            client(true)
            taskName("runClient")
            args("--username", "Dev")
        }

        create("Forge Server") {
            taskName("runServer")
        }
    }
}

dependencies {
    minecraft(libs.forge)
    compileOnly(project(":common"))

    if (System.getProperty("idea.sync.active") != "true")
        annotationProcessor(variantOf(libs.mixin) { classifier("processor") })

    compileOnly(libs.mixinextras.common)
    annotationProcessor(libs.mixinextras.common)
    testCompileOnly(libs.mixinextras.common)

    runtimeOnly(libs.mixinextras.forge)
    jarJar(libs.mixinextras.forge) {
        jarJar.ranged(this, libs.versions.mixinextras.range.get())
    }

    implementation(libs.jopt.simple)

    // Mod Dependencies below
    //implementation(fg.deobf(modDeps.geckolib.forge))

}

tasks.named<Jar>("jar").configure {
    archiveClassifier.set("slim")
}

tasks.named<JarJar>("jarJar").configure {
    archiveClassifier.set("")
}

tasks.withType<JavaCompile>().configureEach {
    source(project(":common").sourceSets.main.get().allSource)
}

tasks.named<Jar>("sourcesJar").configure {
    from(project(":common").sourceSets.main.get().allSource)
}

tasks.named<DefaultTask>("assemble").configure {
    dependsOn("jarJar")
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.main.get().allJava)
}

tasks.withType<ProcessResources>().configureEach {
    from(project(":common").sourceSets.main.get().resources)
    exclude("**/accesstransformer-common.cfg")
}

mixin {
    add(sourceSets.main.get(), "$modId.refmap.json")
    config("$modId.mixins.json")
}

// Must have your Modrinth API Key as an environment variable under 'MODRINTH_TOKEN'
modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"
    projectId.set(properties["modrinthProjectId"] as String)
    versionNumber.set(project.version.toString())
    versionName = "Forge ${libs.versions.minecraft.asProvider().get()}"
    uploadFile.set(tasks.jarJar)
    gameVersions.set(listOf(libs.versions.minecraft.asProvider().get()))
    loaders.set(listOf("forge"))

    if (rootProject.file("CHANGELOG.md").exists())
        changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))

    // Comment out below to enable publishing properly
    debugMode = true
    // See below for other properties and info
    // https://github.com/modrinth/minotaur#available-properties
}

// Must have your CurseForge API Key as an environment variable under 'CURSEFORGE_TOKEN'
tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(properties["curseforgeProjectId"], tasks.jarJar)
    mainFile.releaseType = "release"
    mainFile.addModLoader("Forge")
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
                jarJar.component(this)
                artifactId = base.archivesName.get()
            }
        }
    }
}

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}

sourceSets.forEach {
    val dir = layout.buildDirectory.dir("sourcesSets/${it}.name")

    it.output.setResourcesDir(dir)
    it.java.destinationDirectory = dir
}