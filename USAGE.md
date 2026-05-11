# Usage Instructions

## Adding Dependencies
1. Add your dependency version in the [`libs.versions.toml`](gradle/libs.versions.toml) file.<br>
    See the existing `geckolib` version for an example
2. Add your repository in the [`repositories.gradle.kts`](buildSrc/src/main/kotlin/repositories.gradle.kts) file<br>
    Examples have been provided for your use
3. Add the dependency artifact to the `dependencies.versions.toml` file, under the `libraries` section.<br>
    See the existing `geckolib` artifact entry for an example
4. Add the dependency to the build.gradle.kts file in each module in the dependencies section:<br>
    [`common`](common/build.gradle.kts)<br>
    [`fabric`](common/build.gradle.kts)<br>
    [`forge`](common/build.gradle.kts)<br>
    [`neoforge`](common/build.gradle.kts)<br>
    See the existing `geckolib` dependency entry for an example
5. Refresh Gradle (the circular arrows icon in the Gradle tasks panel)

## Registering Objects
1. Use the builtin java services to register your objects in [`common`](common/src/main/java)<br>
    See: `PlatformHelper`<br>
    See: `FabricPlatform`<br>
    See: `ForgePlatform`<br>
    See: `NeoforgePlatform`<br>
2. If needed, add a new platform registration method to `PlatformHelper`, then implement it on each platform class
3. Ensure you init the registry class, like in `ModCommon#doRegistrations`.

See the existing `ModItems` or `ModEntities` classes for an example

