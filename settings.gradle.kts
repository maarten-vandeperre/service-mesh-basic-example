pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}
rootProject.name= "service-mesh-basic-example"

include("services:service-a")
include("services:service-a-v2")
include("services:service-b")
include("services:service-c")

rootProject.children
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .flatMap { child -> if (child.children.isEmpty()) listOf(child) else child.children }
        .forEach { subproject ->
            println("configure: " + subproject.name + ".gradle.kts")
            subproject.buildFileName = subproject.name + ".gradle.kts"
        }