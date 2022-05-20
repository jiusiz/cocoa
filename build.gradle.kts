plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    java
}

allprojects{
    group = "io.github.jiusiz"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
