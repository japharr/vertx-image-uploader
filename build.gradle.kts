import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA

plugins {
    java
    application
    id("com.adarshr.test-logger") version "3.1.0"
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

allprojects {
    extra["vertxVersion"] = "4.2.3"
    extra["jupiterVersion"] = "5.8.2"
    extra["logbackClassicVersion"] = "1.2.10"
    extra["assertjVersion"] = "3.21.0"
    extra["restAssuredVersion"] = "4.4.0"
    extra["nettyResolverVersion"] = "4.1.72.Final"
}

subprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }

    apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "com.adarshr.test-logger")

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    testlogger {
        theme = MOCHA
        slowThreshold = 5000
        showStandardStreams = true
        showFullStackTraces = true
    }
}