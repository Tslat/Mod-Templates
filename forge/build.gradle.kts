plugins {
    id("project-setup")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.forgegradle)
    alias(libs.plugins.forge.jarjar)
    alias(libs.plugins.forge.at)
}

val modId:          String by project
val modDisplayName: String by project

jarJar.register() {
    archiveClassifier.set("")
}

minecraft {
    mappings("parchment", "${libs.versions.parchment.minecraft.get()}-${libs.versions.parchment.asProvider().get()}")

    runs {
        configureEach {
            workingDir.convention(layout.projectDirectory.dir("runs/${name}"))
            systemProperty("forge.logging.console.level", "debug")
        }

        register("client") {
            args("--username", "Dev")
            args("-mixin.config=${modId}.mixins.json")
        }

        register("client2") {
            args("--username", "Dev2")
            args("-mixin.config=${modId}.mixins.json")
        }

        register("server") {
            args("-mixin.config=${modId}.mixins.json")
        }
    }
}

repositories {
    maven(minecraft.mavenizer)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter {
            includeGroupAndSubgroups("org.spongepowered")
        }
    }
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(minecraft.dependency(libs.forge))
    compileOnly(project(":common")) {
        accessTransformers.configure(this) {
            config.set(rootProject.file("common/src/main/resources/META-INF/accesstransformer.cfg"))
        }
    }

    annotationProcessor(libs.forge.eventbusvalidator)

    // Mod Dependencies below
    //implementation(fg.deobf(modDeps.geckolib.forge))

}

tasks.named<Jar>("jar").configure {
    archiveClassifier.set("slim")
}

tasks.named<DefaultTask>("assemble").configure {
    dependsOn("jarJar")
}

// Must have your Modrinth API Key as an environment variable under 'MODRINTH_TOKEN'
//modrinth {
//    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"
//    projectId.set(properties["modrinthProjectId"] as String)
//    versionNumber.set(project.version.toString())
//    versionName = "Forge ${libs.versions.minecraft.asProvider().get()}"
//    uploadFile.set(tasks.named<Jar>("jar"))
//    gameVersions.set(listOf(libs.versions.minecraft.asProvider().get()))
//    loaders.set(listOf("forge"))
//
//    if (rootProject.file("CHANGELOG.md").exists())
//        changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))
//
//    // Comment out below to enable publishing properly
//    debugMode = true
//    // See below for other properties and info
//    // https://github.com/modrinth/minotaur#available-properties
//}
//
//// Must have your CurseForge API Key as an environment variable under 'CURSEFORGE_TOKEN'
//tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
//    group = "publishing"
//    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"
//
//    val mainFile = upload(properties["curseforgeProjectId"], tasks.named<Jar>("jar"))
//    mainFile.displayName = "$modDisplayName Forge ${libs.versions.minecraft.asProvider().get()} ${project.version}"
//    mainFile.releaseType = "release"
//    mainFile.addModLoader("Forge")
//    mainFile.addGameVersion(libs.versions.minecraft.asProvider().get())
//    mainFile.addJavaVersion("Java ${libs.versions.java}")
//
//    if (rootProject.file("CHANGELOG.md").exists())
//        mainFile.changelog = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)
//
//    // Comment out below to enable publishing properly
//    debugMode = true
//    // See below for other properties and info
//    // https://github.com/Darkhax/CurseForgeGradle#available-properties
//}

publishing {
    publishing {
        publications {
            create<MavenPublication>(modId) {
                from(components["jarJar"])
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