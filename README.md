# MvvmWithFlow
A sample of a clean architecture for offline-first using MVVM with Flow

## Offline First Architecture
There may be many meanings tied to the "offline-first" artchitecture. The definition here means the app always loads from the cache "first" for the fast performance. Once the data from the cache is displayed, it updates again the difference (if any) using the data from the server. 

<b>Result:</b><br>
If getting information from the server is slow, app still loads the screen instantly from cache instead of showing the "loading" screen.
Also, if the device is in offline, the app will still work as long as there is a cache data (the page was loaded before).


<!-- Libraries -->
## Libraries Used

#### Navigation Component
https://developer.android.com/guide/navigation/navigation-getting-started

#### ROOM (DB)
https://developer.android.com/topic/libraries/architecture/room

#### Timber
https://github.com/JakeWharton/timber

#### Retrofit
https://github.com/square/retrofit


<!-- Useful links -->
## Useful Links

https://medium.com/androiddevelopers/livedata-with-coroutines-and-flow-part-i-reactive-uis-b20f676d25d7

Explain different operators with example: zip, combine, flatMapConcat, flatMapMerge, etc.
https://kotlinlang.org/docs/reference/coroutines/flow.html


<!-- LICENSE -->
## License

Distributed under the MIT License. 
