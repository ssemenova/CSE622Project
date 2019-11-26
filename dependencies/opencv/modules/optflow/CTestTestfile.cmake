# CMake generated Testfile for 
# Source directory: /Users/sofiya/opencv/opencv_contrib/modules/optflow
# Build directory: /Users/sofiya/opencv/opencv-3.4.8/build/modules/optflow
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(opencv_test_optflow "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_test_optflow" "--gtest_output=xml:opencv_test_optflow.xml")
set_tests_properties(opencv_test_optflow PROPERTIES  LABELS "Extra;opencv_optflow;Accuracy" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/accuracy")
add_test(opencv_perf_optflow "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_perf_optflow" "--gtest_output=xml:opencv_perf_optflow.xml")
set_tests_properties(opencv_perf_optflow PROPERTIES  LABELS "Extra;opencv_optflow;Performance" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/performance")
add_test(opencv_sanity_optflow "/Users/sofiya/opencv/opencv-3.4.8/build/bin/opencv_perf_optflow" "--gtest_output=xml:opencv_perf_optflow.xml" "--perf_min_samples=1" "--perf_force_samples=1" "--perf_verify_sanity")
set_tests_properties(opencv_sanity_optflow PROPERTIES  LABELS "Extra;opencv_optflow;Sanity" WORKING_DIRECTORY "/Users/sofiya/opencv/opencv-3.4.8/build/test-reports/sanity")
