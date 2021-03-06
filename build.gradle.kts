import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    maven
    kotlin("jvm") version "1.3.50"
}

group = "com.github.azurlane-api"
version = "1.5.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.kittinunf.fuel:fuel:2.2.1")
    implementation("com.google.code.gson:gson:2.8.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
