# CSE 622 Project

## Setup
   1. [Install NDK, CMake and LLDB](https://developer.android.com/studio/projects/install-ndk.md)

   2. Install *OpenCV Android release* :
    * Download [OpenCV 4.1.1 Android release](https://sourceforge.net/projects/opencvlibrary/files/4.1.1/opencv-4.1.1-android-sdk.zip/download) or download latest available Android release on [OpenCV website](https://opencv.org/releases/).
    * Unzip downloaded file and put **OpenCV-android-sdk** directory on a path of your choice.

   3. Download this repository and open it as a project in Android Studio
   
   4. Link your *Android Studio* project to the *OpenCV Android SDK* you downloaded :
    * Open [gradle.properties](gradle.properties) file and edit following line with your own *OpenCV Android SDK* directory path :
    
          opencvsdk=/home/sofiya/OpenCV-android-sdk

    * Open [settings.gradle](settings.gradle) and replace the following line with your own directory path:

      	  project(':opencv').projectDir = new File('/home/sofiya/OpenCV-android-sdk/sdk')

   5. Sync Gradle and run the application on your Android Device!