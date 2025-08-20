plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"
val lombokVersion = "1.18.32"

application {
    mainClass.set("Game") // Assuming your main class is in org.example package
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Also include for tests if needed
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.slf4j:slf4j-api:2.0.13")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")

    implementation("com.github.bhlangonijr:chesslib:1.3.4")
}

tasks.test {
    val pathToResources = file("${project.rootDir}/src/main/resources/pieces")
    jvmArgs("-Dfile.encoding=UTF-8","-Dproject.root=" + project.rootDir.absolutePath, "-Dresources.path=" + pathToResources.absolutePath)
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    standardOutput = System.out
    val pathToResources = file("${project.rootDir}/src/main/resources/pieces")
    jvmArgs("-Dfile.encoding=UTF-8","-Dproject.root=" + project.rootDir.absolutePath, "-Dresources.path=" + pathToResources.absolutePath)
}