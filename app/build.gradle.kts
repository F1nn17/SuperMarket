plugins {
	alias(libs.plugins.android.application)
}

android {
	namespace = "com.shiromadev.supermarket"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.shiromadev.supermarket"
		minSdk = 28
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
	buildToolsVersion = "34.0.0"
	ndkVersion = "27.0.12077973"
}

dependencies {

	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.activity)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.ext.junit)
	androidTestImplementation(libs.espresso.core)
	implementation("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")

}