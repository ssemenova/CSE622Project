# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv_contrib/modules/structured_light
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/structured_light
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_structured_light "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_structured_light" "--gtest_output=xml:opencv_test_structured_light.xml")
set_tests_properties(opencv_test_structured_light PROPERTIES  LABELS "Extra;opencv_structured_light;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
