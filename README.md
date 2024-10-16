# Kotlin Multiplatform Compose Firebase App template

This is a refactored version of the official Jetbrains version taken from here: https://kmp.jetbrains.com/#templateGallery

This is a basic Kotlin Multiplatform app template for Android and iOS. It includes shared business logic and data handling, and a shared UI implementation using Compose Multiplatform.

![Screenshots of the app](images/screenshots.png)

### Technologies

The data displayed by the app is from [The Metropolitan Museum of Art Collection API](https://metmuseum.github.io/) but served via firebase firestore.

The app uses the following multiplatform dependencies in its implementation:

- [Compose Multiplatform](https://jb.gg/compose) for UI
- [Compose Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)
- [Firebase](https://firebase.google.com/) for data
- [Gitlive](https://github.com/GitLiveApp/firebase-kotlin-sdk) for firebase support in KMP
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON handling
- [Kamel](https://github.com/Kamel-Media/Kamel) for image loading
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection

> These are just some of the possible libraries to use for these tasks with Kotlin Multiplatform, and their usage here isn't a strong recommendation for these specific libraries over the available alternatives. You can find a wide variety of curated multiplatform libraries in the [kmp-awesome](https://github.com/terrakok/kmp-awesome) repository.

### Setup
There is a folder at the root called setup, in there you can either run locally or push the data to your own firebase account.

These scripts seed the data from the json file.

In your terminal navigate to the setup folder

```
cd setup/functions
```

Then install the node modules
```
npm install
```

After installing the dependancies, you will then need to make sure you are logged into firebase in the cli
```
firebase login
```

Then you need to take note of your project and 'add' that replace PROJECTID with the id of your project
```
firebase use --add PROJECTID
```

Then you should be able to run the emulators locally or deploy remotely

Local:
```
npm run serve
```
Remotely:
```
npm run deploy
```

After that you need to call the seed endpoint from the browser:
```
http://127.0.0.1:5001/PROJECTID/us-central1/seed
```
If you are not using locally, then replace 127.0.0.1:5001 with the address of the remote.

Hopefully then you should have the collections and seed data all in place.

### Running the demo

After you have successfully seeded the data into your firebase instance, then you can run the app. By default it points to your local emulator.
