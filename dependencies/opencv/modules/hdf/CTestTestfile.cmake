# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv_contrib/modules/hdf
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_hdf "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_hdf" "--gtest_output=xml:opencv_test_hdf.xml")
set_tests_properties(opencv_test_hdf PROPERTIES  LABELS "Extra;opencv_hdf;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
