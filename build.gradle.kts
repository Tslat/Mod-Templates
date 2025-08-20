import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

plugins {
    alias(libs.plugins.minotaur) apply false
    alias(libs.plugins.curseforgegradle) apply false

    // Required for NeoGradle
    alias(libs.plugins.ideaext)
    id("project-setup")
    id("setup-refactoring")
}

idea.project.settings.taskTriggers.beforeSync(tasks.getByName("refactorOnInitialSetup"))