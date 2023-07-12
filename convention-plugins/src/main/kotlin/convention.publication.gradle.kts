import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import java.util.*

plugins {
    `maven-publish`
    signing
}

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile: File = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = "8B457C70"
    ext["signing.password"] = "Neoflex"
    ext["signing.secretKeyRingFile"] =
        "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"
    ext["ossrhUsername"] = "vladimirpapin"
    ext["ossrhPassword"] = "#v@WQ9cvceqk"
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = ext[name]?.toString()
val ossrhRepositoryUrl = if (version.toString().endsWith("SNAPSHOT")) {
    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
} else {
    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
}

publishing {
    // Configure maven central repository
    repositories {
        maven(ossrhRepositoryUrl) {
            name = "OSSRH"
            metadataSources {
                gradleMetadata()
            }
            credentials {
                username = "vladimirpapin"
// username = getExtraString("ossrhUsername")
// password = getExtraString("ossrhPassword")
                password = "#v@WQ9cvceqk"
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {
        group = "io.github.VladimirSergeevichFedorov"
        version = "1.0.7"
        artifactId = "TryLibrary"
        // Stub javadoc.jar artifact
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("liba test neo")
            description.set("test neo (ios + and) test")
            url.set("https://github.com/VladimirSergeevichFedorov/TryLibrary")

            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("VladimirSergeevichFedorov")
                    name.set("Vladimir")
                    email.set("viky2010@rambler.ru")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/VladimirSergeevichFedorov/TryLibrary.git")
                developerConnection.set("scm:git:ssh://github.com/VladimirSergeevichFedorov/TryLibrary.git")
                url.set("http://github.com/VladimirSergeevichFedorov/TryLibrary")
            }
        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}