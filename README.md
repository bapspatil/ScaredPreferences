# ScaredPreferences

[![GitHub license](https://img.shields.io/github/license/bapspatil/ScaredPreferences.svg)](https://github.com/bapspatil/ScaredPreferences/blob/master/LICENSE)
[![Jitpack](https://jitpack.io/v/bapspatil/ScaredPreferences.svg)](https://jitpack.io/#bapspatil/ScaredPreferences)

A Kotlin library that makes using SharedPreferences less scary. 

## Download

Add this in your root `build.gradle` file (not your module `build.gradle` file):

```
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module `build.gradle`:

```
dependencies {
    implementation 'com.github.bapspatil:ScaredPreferences:1.0.0'
}
```

## Usage

The sample app included in this repo is still under development. 

In the meantime, you can take a look at how to use the library here:

Create a new class that takes in a parameter of type `ScaredPreferences`:

```
class MyPreferences(scaredPreferences: ScaredPreferences) {

    // Key used here for SharedPreferences is "userId"
    // Default value is null
    var userId: String? by scaredPreferences.delegate(null)
    
    // Key used here for SharedPreferences is "KEY_PROFILE_PIC"
    // Default value is null
    var userProfilePic: String? by scaredPreferences.delegate(null, "KEY_PROFILE_PIC")
    
}
```

Use this in your `Activity`/`Fragment` required like so:

```
class MainActivity : AppCompatActivity() {

    private lateinit var scaredPreferences: ScaredPreferences
    private lateinit var myPreferences: MyPreferences

    override fun onCreate(...) {
        ...
        
        // Get default SharedPreferences
        scaredPreferences = ScaredPreferences.Buider().withDefaultSharedPreferences(this).build()
        
        // Or, get a custom SharedPreferences
        scaredPreferences = ScaredPreferences.Builder().withSharedPreferences(myCustomSharedPreferences).build()
        
        // Create your MyPreferences with ScaredPreferences
        myPreferences = MyPreferences(scaredPreferences)
        
        // Set your MyPreferences
        myPreferences.userId = "ADSF09U23GADSG"
        myPreferences.userProfilePic = "https://github.com/bapspatil.png"
        
        // Get your MyPreferences
        Log.d("MY_USER_ID", myPreferences.userId)
        Log.d("MY_USER_PIC", myPreferences.userProfilePic)
        
    }
}
```

## Developed By

Bapusaheb Patil

<img src="https://github.com/bapspatil.png" width="20%">

https://bapspatil.com

## License

    Copyright 2019 Bapusaheb Patil

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.