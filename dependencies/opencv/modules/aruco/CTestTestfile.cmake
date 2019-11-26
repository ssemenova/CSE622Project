# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv_contrib/modules/aruco
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/aruco
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_aruco "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_aruco" "--gtest_output=xml:opencv_test_aruco.xml")
set_tests_properties(opencv_test_aruco PROPERTIES  LABELS "Extra;opencv_aruco;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
