plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")

    implementation("io.github.dj-raven:swing-datetime-picker:2.1.2")
    implementation("com.formdev:flatlaf:3.6")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("com.mysql:mysql-connector-j:9.3.0")
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.smartcity.LaunchApp")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}
