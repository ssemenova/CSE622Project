# CSE 622 Project

## Setup

### Mobile app
   1. [Install NDK, CMake and LLDB](https://developer.android.com/studio/projects/install-ndk.md)

   2. Install *OpenCV Android release* :
   
      * Download [OpenCV 4.1.1 Android release](https://sourceforge.net/projects/opencvlibrary/files/4.1.1/opencv-4.1.1-android-sdk.zip/download) or download latest available Android release on [OpenCV website](https://opencv.org/releases/).
      * Unzip downloaded file and put **OpenCV-android-sdk** directory on a path of your choice.

   3. Download this repository and open the ```mobile``` directory as a project in Android Studio
   
   4. Link your *Android Studio* project to the *OpenCV Android SDK* you downloaded :
      * Open [mobile/gradle.properties](mobile/gradle.properties) file and edit following line with your own *OpenCV Android SDK* directory path :
    
          ```opencvsdk=/home/sofiya/OpenCV-android-sdk```

      * Open [mobile/settings.gradle](mobile/settings.gradle) and replace the following line with your own directory path:

      	  ```project(':opencv').projectDir = new File('/home/sofiya/OpenCV-android-sdk/sdk')```

   5. Sync Gradle and run the application on your Android Device!


### Server

#### Build opencv from source
1. Download the opencv source code for **version 3** [here](https://opencv.org/releases.html) and the source code for the contrib repository **for the same version** [here](https://github.com/opencv/opencv_contrib) and move it onto your server
2. On the server, run:
    ~~~
    $ cd path-to-wherever-you-downloaded-opencv/opencv
    $ mkdir build
    $ cd build
    $ cmake14 -D CMAKE_BUILD_TYPE=RELEASE -D OPENCV_ENABLE_NONFREE=ON -D -DENABLE_PRECOMPILED_HEADERS=OFF OPENCV_EXTRA_MODULES_PATH=path-to-where-you-downloaded-opencv_contrib/modules ..
    $ make -j7
    ~~~