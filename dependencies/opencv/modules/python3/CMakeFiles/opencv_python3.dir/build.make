# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.13

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/local/Cellar/cmake/3.13.3/bin/cmake

# The command to remove a file.
RM = /usr/local/Cellar/cmake/3.13.3/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/sofiya/opencv/opencv-3.4.8

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/sofiya/opencv/opencv-3.4.8/build

# Include any dependencies generated for this target.
include modules/python3/CMakeFiles/opencv_python3.dir/depend.make

# Include the progress variables for this target.
include modules/python3/CMakeFiles/opencv_python3.dir/progress.make

# Include the compile flags for this target's objects.
include modules/python3/CMakeFiles/opencv_python3.dir/flags.make

modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o: modules/python3/CMakeFiles/opencv_python3.dir/flags.make
modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o: ../modules/python/src2/cv2.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o -c /Users/sofiya/opencv/opencv-3.4.8/modules/python/src2/cv2.cpp

modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv-3.4.8/modules/python/src2/cv2.cpp > CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.i

modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv-3.4.8/modules/python/src2/cv2.cpp -o CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.s

# Object files for target opencv_python3
opencv_python3_OBJECTS = \
"CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o"

# External object files for target opencv_python3
opencv_python3_EXTERNAL_OBJECTS =

lib/python3/cv2.cpython-36m-darwin.so: modules/python3/CMakeFiles/opencv_python3.dir/__/src2/cv2.cpp.o
lib/python3/cv2.cpython-36m-darwin.so: modules/python3/CMakeFiles/opencv_python3.dir/build.make
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_hdf.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_reg.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_surface_matching.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_xphoto.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_freetype.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_fuzzy.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_hfs.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_img_hash.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_line_descriptor.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_saliency.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_rgbd.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_structured_light.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_superres.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_tracking.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_videostab.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_xobjdetect.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_aruco.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_bgsegm.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_bioinspired.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_ccalib.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_dpm.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_face.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_optflow.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_stitching.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_phase_unwrapping.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_viz.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_plot.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_datasets.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_text.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_dnn.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_highgui.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_videoio.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_photo.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_objdetect.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_ximgproc.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_imgcodecs.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_xfeatures2d.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_ml.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_shape.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_video.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_calib3d.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_features2d.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_flann.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_imgproc.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: lib/libopencv_core.3.4.8.dylib
lib/python3/cv2.cpython-36m-darwin.so: modules/python3/CMakeFiles/opencv_python3.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX shared module ../../lib/python3/cv2.cpython-36m-darwin.so"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/opencv_python3.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
modules/python3/CMakeFiles/opencv_python3.dir/build: lib/python3/cv2.cpython-36m-darwin.so

.PHONY : modules/python3/CMakeFiles/opencv_python3.dir/build

modules/python3/CMakeFiles/opencv_python3.dir/clean:
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 && $(CMAKE_COMMAND) -P CMakeFiles/opencv_python3.dir/cmake_clean.cmake
.PHONY : modules/python3/CMakeFiles/opencv_python3.dir/clean

modules/python3/CMakeFiles/opencv_python3.dir/depend:
	cd /Users/sofiya/opencv/opencv-3.4.8/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/sofiya/opencv/opencv-3.4.8 /Users/sofiya/opencv/opencv-3.4.8/modules/python/python3 /Users/sofiya/opencv/opencv-3.4.8/build /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3 /Users/sofiya/opencv/opencv-3.4.8/build/modules/python3/CMakeFiles/opencv_python3.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : modules/python3/CMakeFiles/opencv_python3.dir/depend
