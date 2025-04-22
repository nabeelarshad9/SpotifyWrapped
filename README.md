# Spotify Wrapper 2.0

This is an Android application project designed as a wrapper for Spotify with added features, seasonal themes, and time-based functionalities.

## Features
- Custom Spotify experience with holiday-based themes (Christmas, Halloween, New Year, etc.)
- User login and account creation
- Detailed listening history view
- Notifications and alerts via Firebase
- Visual themes and animations based on time or holidays
- Personalized music wrapping experience

## Project Structure

### Root Files
- `gradlew`, `gradlew.bat`: Gradle wrapper scripts to build the project.
- `build.gradle`, `settings.gradle`, `gradle.properties`: Gradle build and project configuration files.

### App-Level
- `google-services.json`: Firebase configuration for messaging and analytics.
- `proguard-rules.pro`: Rules for code obfuscation and optimization.
- `build.gradle`: App-level build configurations.

### Main Code (`app/src/main/java/com/example/spotify_wrapper20`)
- `MainActivity.java`: Entry point of the app.
- `SpotifyFirebaseMessagingService.java`: Handles push notifications from Firebase.

#### UI Modules
- `ui/history`: Manages music listening history display and detail views.
- `ui/AccountEdit`: Allows users to edit their account settings.
- `ui/wrapped`: Handles the year-end or personalized music wrap-up.
- `ui/notifications`: Manages user notifications.
- `ui/login`: Includes login and account creation logic using `DatabaseHelper` and related classes.

### Layouts & UI Resources (`app/src/main/res`)
- `layout/`: XML files for each activity and fragment (login, history, main activity, etc.)
- `drawable/`: Images and drawable XMLs for various backgrounds and icons based on holidays.
- `anim/`: Custom animation XMLs (scale, bounce, etc.)
- `values/`: XMLs for strings, themes, dimensions, and colors.

### Navigation & XML
- `res/navigation/`: Contains the navigation graph.
- `res/xml/`: Backup and data extraction rules.
- `res/menu/`: Bottom navigation bar configuration.

## Build Instructions
1. Open the project in Android Studio.
2. Sync Gradle and make sure Firebase is properly set up.
3. Run on emulator or physical device.

## Dependencies
- Firebase
- AndroidX libraries

---
