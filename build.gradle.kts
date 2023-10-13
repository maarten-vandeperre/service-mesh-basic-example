plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

buildscript {
    dependencies {
        classpath("io.quarkus:gradle-application-plugin:${properties.get("quarkusPluginVersion")}")
    }
}

allprojects.filter { it.name != "platform" }.forEach { project ->
    project.apply(plugin = "idea")
    project.apply(plugin = "org.jetbrains.kotlin.jvm")
    project.apply(plugin = "java")

    project.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        sourceSets.getByName("main") {
            java.srcDir("src/main/java")
            java.srcDir("src/main/kotlin")
        }
        sourceSets.getByName("test") {
            java.srcDir("src/test/java")
            java.srcDir("src/test/kotlin")
        }
    }

    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            javaParameters = true
        }
    }

    project.tasks.named<Test>("test") {
        useJUnitPlatform()
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    }

    allOpen {
        annotation("jakarta.ws.rs.Path")
        annotation("jakarta.enterprise.context.ApplicationScoped")
        annotation("io.quarkus.test.junit.QuarkusTest")
    }

    project.dependencies {
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-resteasy-reactive-jsonb")
        implementation("io.quarkus:quarkus-smallrye-opentracing")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-smallrye-metrics")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.quarkus:quarkus-arc")
        implementation("io.quarkus:quarkus-resteasy-reactive")
        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
    }
}

subprojects {
    group = "com.redhat.demo.servicemesh.basic.sample"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
    }

    tasks.withType<Test> {
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        setForkEvery(100)
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }
}