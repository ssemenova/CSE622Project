## Why Calibration?

This directory is important if you want to calibrate the camera for any camera/images that are not in the provided zip file. If you're just using the zip file, you can ignore this all.

It requires that you physically print out [apriltags](https://github.com/AprilRobotics/apriltag-imgs/tree/master/tagStandard41h12) (see ["Choosing a tag family"](https://github.com/AprilRobotics/apriltag/wiki/AprilTag-User-Guide#choosing-a-tag-family) to figure out which ones are the right ones for you) and place them in your environment.

## Setup and run

1. Clone [this](https://github.com/AprilRobotics/apriltag.git) repository.
2. ```
   cd apriltag
   cmake .
   sudo make install
   ```
3. Copy ```libapriltag.so.3.1.0``` from wherever it was installed (for me, it was in ```/usr/local/lib/```) to ```/lib/```, and create a simlink:
   ```
   cp /usr/local/lib/libapriltag.so.3.1.0 /lib/libapriltag.so.3.1.0
   ln -s /lib/libapriltag.so.3.1.0 /lib/libapriltag.so.3
   ```
4. Run the python script: ```python calibrate.py```