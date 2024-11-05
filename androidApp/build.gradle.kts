import java.util.Properties

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    kotlin("kapt")
    id(libs.plugins.hilt.android.get().pluginId)
    id("template.coroutines")
    alias(libs.plugins.compose.compiler)
}
fun getLocalProperty(key: String): String? {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    return properties.getProperty(key)
}

val apiKeyHeader: String = getLocalProperty("apiKeyHeader") ?: ""
val apiKey: String = getLocalProperty("apiKey") ?: ""
val userEmail: String = getLocalProperty("userEmail") ?: ""
val userPassword: String = getLocalProperty("userPassword") ?: ""

android {
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.vlamik.news"

        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.vlamik.news.TestRunner"
        vectorDrawables { useSupportLibrary = true }
        buildConfigField("String", "API_KEY_HEADER", "\"$apiKeyHeader\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "USER_EMAIL", "\"$userEmail\"")
        buildConfigField("String", "USER_PASSWORD", "\"$userPassword\"")


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    hilt { enableAggregatingTask = true }

    buildFeatures { compose = true }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        )
    }

    kotlin {
        jvmToolchain(17)
    }


    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/META-INF/LICENSE*")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.vlamik.news"
}

dependencies {
    // Project Dependencies
    implementation(project(":core:domain"))
    implementation(project(":core:commons"))

    // Dependencies
    // Compose
    implementation(libs.bundles.compose)

    // Hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(project(":core:data"))
    kapt(libs.hilt.compiler)

    // Ktor Engine
    implementation(libs.ktor.core)
    implementation(libs.ktor.engine.android)

    // SplashScreen
    implementation(libs.splashscreen)

    // Datastore
    implementation(libs.datastore.android)

    // Debug Dependencies
    debugImplementation(libs.bundles.compose.debug)

    // Android Test Dependencies
    androidTestImplementation(libs.hilt.test)
    kaptAndroidTest(libs.hilt.test.compiler)

    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.bundles.compose.test)
    androidTestImplementation(libs.ktor.engine.mock)
    androidTestImplementation(project(":core:data-test"))

    // Test Dependencies
    testImplementation(libs.bundles.test.core)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android)
}
