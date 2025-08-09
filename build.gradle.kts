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
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    standardOutput = System.out
    jvmArgs("-Dfile.encoding=UTF-8")
}