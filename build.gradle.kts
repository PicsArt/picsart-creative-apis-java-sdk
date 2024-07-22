import java.util.Properties

plugins {
    id("java-library")
    id("maven-publish")
    id("org.cadixdev.licenser") version "0.6.1"
    id("com.diffplug.spotless") version "6.25.0"
}
group = "com.picsart"
version = "1.0"

repositories {
    mavenCentral()
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

spotless {
    java {
        googleJavaFormat("1.22.0")
    }
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2023.0.6"))
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    implementation("org.apache.bval:bval-jsr:3.0.0")

    api("io.projectreactor:reactor-core:3.6.6")
    implementation("io.projectreactor.netty:reactor-netty-http")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")

    implementation("org.slf4j:slf4j-api:2.0.13")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.apache.tika:tika-core:2.9.2")
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.checkerframework:checker:3.42.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "Picsart Creative APIs SDK",
            "Implementation-Version" to version
        )
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

tasks.register("writeVersion") {
    val versionFile = file(layout.buildDirectory.dir("resources/main/version.properties"))
    doLast {
        versionFile.parentFile.mkdirs()
        versionFile.writer().use { writer ->
            val properties = Properties()
            properties["version"] = project.version.toString()
            properties.store(writer, null)
        }
    }
}

tasks.named("processResources") {
    dependsOn("writeVersion")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar)
            artifact(javadocJar)
        }
    }
    repositories {
        mavenLocal()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val integrationTest: SourceSet = sourceSets.create("integrationTest") {
    java {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        srcDir("src/integration/java")
    }
    resources.srcDir("src/integration/resources")
}

configurations[integrationTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

val secretPropertiesFile = file("secret.properties")
val secretProperties = Properties()

if (secretPropertiesFile.exists()) {
    secretPropertiesFile.reader().use { reader ->
        secretProperties.load(reader)
    }
}

val apiKeyFromSecret: String? = secretProperties.getProperty("apiKey")
val apiKeyFromSystem: String? = System.getProperty("apiKey")


val integrationTestTask = tasks.register<Test>("integrationTest") {
    val apiKey = apiKeyFromSecret ?: apiKeyFromSystem ?: throw IllegalArgumentException("API key not found in secret.properties or system properties")
    systemProperty("API_KEY", apiKey)
    group = "verification"
    useJUnitPlatform()
    testClassesDirs = integrationTest.output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath

    shouldRunAfter("test")
}

tasks.check {
    dependsOn(integrationTestTask)
}

val examplesSourceSet = sourceSets.create("examples") {
    java.srcDir("examples")
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
}

configurations[examplesSourceSet.compileClasspathConfigurationName].extendsFrom(configurations["compileClasspath"])
configurations[examplesSourceSet.runtimeClasspathConfigurationName].extendsFrom(configurations["runtimeClasspath"])

val compileExamples = tasks.register<JavaCompile>("compileExamples") {
    description = "Compiles the example source code"
    source = examplesSourceSet.allJava
    classpath = examplesSourceSet.compileClasspath
    destinationDirectory.set(file(layout.buildDirectory.dir("classes/java/examples")))
}

tasks.named("compileJava") {
    finalizedBy(compileExamples)
}

tasks.withType<JavaExec> {
    dependsOn(compileExamples)
    classpath = sourceSets["main"].runtimeClasspath + sourceSets["examples"].runtimeClasspath
}
