/*
 * Copyright (C) 2022-2022 jiusiz.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    api("net.mamoe:mirai-core:2.11.0")
    api("com.google.code.findbugs:annotations:3.0.1") // 消除警告

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    // 忽略doc错误
    options.quiet()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
}

val neName: String by project
val nePassword: String by project

publishing {
    publications {
        create<MavenPublication>("-cocoa-") {
            groupId = "${project.group}"
            artifactId = project.name
            this.version = "${project.version}"

            from(components["java"])
            pom {
                name.set(project.name)
                description.set("cocoa -- annotation style QQ robot framework")
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
