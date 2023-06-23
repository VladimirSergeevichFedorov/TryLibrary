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
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
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

//publishing {
//    repositories {
//        maven(ossrhRepositoryUrl) {
//            name = "ossrh"
//            credentials(PasswordCredentials::class)
//        }
//    }
//}
publishing {
    // Configure maven central repository
    repositories {
        maven(ossrhRepositoryUrl) {
            name = "OSSRH"
//            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//            metadataSources {
//                mavenPom()
//                artifact()
//            }
            metadataSources {
                gradleMetadata()
            }
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {
        group = "io.github.VladimirSergeevichFedorov"
        version = "1.0.3"
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
//    connection.set("https://github.com/VladimirSergeevichFedorov/TryLibrary.git")
//    url.set("https://github.com/VladimirSergeevichFedorov/TryLibrary")
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
tasks.withType<GenerateModuleMetadata> {
    // The value 'enforced-platform' is provided in the validation
    // error message you got
    suppressedValidationErrors.add("enforced-platform")
}