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

#### (optional) Build opencv from source
*The ant file refers to the built opencv in ```dependencies```, so you don't have to do this if that method works for you.*
1. Download the opencv source code for **version 3** [here](https://opencv.org/releases.html) and the source code for the contrib repository **for the same version** [here](https://github.com/opencv/opencv_contrib) and move it onto your server
2. On the server, edit the file ```modules/features2d/misc/java/src/cpp/features2d_manual.hpp``` in the opencv source code (not the contrib source code).
Insert the following code block in line 8:
    ~~~
    #include "opencv2/features2d.hpp"
    #include "opencv2/xfeatures2d.hpp"
    #include "features2d_converters.hpp"
    ~~~
The following code block in line 121:
    ~~~
    case SIFT:
        fd = xfeatures2d::SIFT::create();
        break;
    case SURF:
        fd = xfeatures2d::SURF::create();
    ~~~
And the following code block on line 254:
    ~~~
    case SIFT:
        de = xfeatures2d::SIFT::create();
    case SURF:
        de = xfeatures2d::SURF::create();
    ~~~

3. On the server, run:
    ~~~
    $ cd path-to-wherever-you-downloaded-opencv/opencv
    $ mkdir build
    $ cd build
    $ cmake14 -D CMAKE_BUILD_TYPE=RELEASE -D OPENCV_ENABLE_NONFREE=ON -D -DENABLE_PRECOMPILED_HEADERS=OFF -DOPENCV_EXTRA_MODULES_PATH=path-to-where-you-downloaded-opencv_contrib/modules ..
    $ make -j7
    ~~~
4. Then, copy the ```build/lib/``` directory into this project's ```dependencies/opencv/``` directory, so that the final directory looks like ```dependencies/opencv/lib/```.

#### Create required image files
1. You'll need an image database that contains images that are representative of places. This is just a folder with images. In the ant file, that folder is called ```imageDatabase/``` but you can change this.
2. You might also want a ```testImages/``` folder, which ```Server.java``` can use as test examples of images to be localized.
You can also unzip both the ```imageDatabase.zip``` folder and the ```testImages.zip``` folder and use those.

#### Build
There are two options: building on the command line, or building in an IDE.

To build in an IDE:
1. If you're using intelliJ, you can link the ant file as a build file for the project using [these](https://www.jetbrains.com/help/idea/adding-build-file-to-project.html) instructions. In the instructions, where it says "Click the generated build file", instead, click on ```build.xml``` in the ```server``` directory.
2. Then, to create a configuration for the build file, in the ant tool window that you opened in the first step, right-click on ```Server > rebuild-run``` and click ```create run configuration```. Save the configuration in the window that pops up.
3. Now you should be able to click the ```build``` and ```run``` buttons (the hammer and the play button) in the toolbar to build and run the program.

To build in the command line:
1. Navigate to ```server/``` and enter ```ant``` in that directory. This will rebuild and run the program.
2. You should have jfx installed and linked in your path. If you don't, you'll get the error:
   ~~~
   [javac] .../CSE622Project/server/src/PlaceDatabase.java:1: error: package javafx.util does not exist
   [javac] import javafx.util.Pair;
   ~~~
   If this isn't already configured for you, I recommend building in an IDE, as linking together openjfx and openjdk is a little hack-y, and an IDE will come with its own packaged version of java that already contains everything you need.
