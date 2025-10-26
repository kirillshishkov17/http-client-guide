plugins {
    id("java")
}

group = "org.shishkov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("tools.jackson.core:jackson-databind:3.0.1")
    implementation("com.squareup.okhttp3:okhttp:5.2.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}