# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv-3.4.8/modules/highgui
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/highgui
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_highgui "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_highgui" "--gtest_output=xml:opencv_test_highgui.xml")
set_tests_properties(opencv_test_highgui PROPERTIES  LABELS "Main;opencv_highgui;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
