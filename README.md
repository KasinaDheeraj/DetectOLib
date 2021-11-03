# DetectOLibrary
[![Release](https://jitpack.io/v/jitpack/android-example.svg)](https://jitpack.io/#jitpack/android-example)

## Build with jitpack

`Step 1:` Add the JitPack repository to your build file to root build.gradle:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
`Step 2:` Add the dependency

	dependencies {
	        implementation 'com.github.KasinaDheeraj:DetectOLib:<$latestReleaseVersion>'
	}
  
 `Step 3:` To use the library for detecting text from image and search results in Google and Youtube.
 
 ```
 Intent intent =new Intent(context,DOLActivity.class);
 startActivity(intent);
 ```
 *``` Note: Photo should be maintained in portrait mode for detection,apply suitable rotations if necessary. ```*
