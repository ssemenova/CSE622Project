# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv-3.4.8/modules/viz
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/viz
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_viz "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_viz.app/Contents/MacOS/opencv_test_viz" "--gtest_output=xml:opencv_test_viz.xml")
set_tests_properties(opencv_test_viz PROPERTIES  LABELS "Main;opencv_viz;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
