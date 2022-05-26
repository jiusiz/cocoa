plugins {
    java
    signing
    `maven-publish`
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    api(project(":cocoa-core"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    // 忽略doc错误
    options.quiet()
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val neName: String by project
val nePassword: String by project

publishing {
    publications {
        create<MavenPublication>("-starter-") {

            from(components["java"])
            pom {
                name.set(project.name)
                description.set("cocoa spring-boot starter")
                url.set("https://github.com/jiusiz/cocoa")
                licenses {
                    license {
                        name.set("GNU AGPLv3")
                        url.set("https://github.com/jiusiz/cocoa/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("jiusiz")
                        name.set("jiusiz")
                        email.set("jiusiz@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/jiusiz/cocoa.git")
                    developerConnection.set("https://github.com/jiusiz")
                    url.set("https://github.com/jiusiz/cocoa")
                }
            }
        }
        repositories {
            maven {
                name = "-sonatype-"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = neName
                    password = nePassword
                }
            }
            maven {
                name = "-sonatypeSnapshot-"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = neName
                    password = nePassword
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
