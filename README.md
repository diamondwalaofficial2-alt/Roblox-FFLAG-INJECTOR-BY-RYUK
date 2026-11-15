# Roblox FFlag Strapper (Android project)

A small Android Studio project with a dark gaming UI for editing, validating, importing and exporting JSON configuration files.

## Requirements
- Android Studio (current stable)
- Android SDK 35
- JDK 17
- Android phone/tablet running Android 6.0 (API 23) or newer

## Build an APK
1. Extract this ZIP.
2. Open the `RobloxFFlagStrapper` folder in Android Studio.
3. Allow Gradle sync to finish (internet is needed the first time to download Gradle/Android plugins).
4. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Android Studio creates the debug APK at `app/build/outputs/apk/debug/app-debug.apk`.
6. Transfer the APK to your Android device and install it. You may need to allow installation from that file manager.

## App features
- Add and remove flags
- Search flags
- Enable/disable inclusion in export
- Balanced, low graphics, quality and empty presets
- Import JSON using Android's document picker
- Export JSON using Android's save dialog
- Raw JSON editing and validation

## Important compatibility note
This app is a configuration-file manager, not a Roblox injector. Roblox's Android app does not offer a universal public interface for applying arbitrary FFlags from third-party apps. Exported flags are not guaranteed to be recognized or used by Roblox. The app does not access private Roblox data, require root, or bypass Android security.

## Project structure
- `app/src/main/java/.../MainActivity.kt` — Android activity and safe document import/export bridge
- `app/src/main/assets/index.html` — responsive interface and editor
- `app/src/main/AndroidManifest.xml` — app manifest

## Build an APK using only an Android phone (GitHub Actions)

1. Sign in to GitHub in your Android browser and create a **new public or private repository**.
2. Upload the contents of this project ZIP to the repository's `main` branch. Make sure `.github/workflows/build.yml` is included. If the GitHub mobile upload flow hides dot-folders, use GitHub's web interface in desktop-site mode.
3. Open the repository's **Actions** tab and select **Build Android APK**.
4. Tap **Run workflow** (or wait for the first upload-triggered build).
5. When the run finishes successfully, open that run and scroll to **Artifacts**.
6. Download `FFlagStrapper-debug-apk`, extract it, then install `app-debug.apk` on your Android device. Android may ask you to allow installs from your browser or file manager.

This workflow builds a debug APK. It does not sign a release APK for Play Store distribution. The project still only manages JSON; it does not inject flags into Roblox or guarantee Roblox Android will apply them.
