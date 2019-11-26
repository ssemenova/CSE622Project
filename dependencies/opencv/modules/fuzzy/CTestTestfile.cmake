# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv_contrib/modules/fuzzy
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/fuzzy
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_fuzzy "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_fuzzy" "--gtest_output=xml:opencv_test_fuzzy.xml")
set_tests_properties(opencv_test_fuzzy PROPERTIES  LABELS "Extra;opencv_fuzzy;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
