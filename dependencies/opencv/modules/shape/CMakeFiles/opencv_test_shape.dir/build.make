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
include modules/shape/CMakeFiles/opencv_test_shape.dir/depend.make

# Include the progress variables for this target.
include modules/shape/CMakeFiles/opencv_test_shape.dir/progress.make

# Include the compile flags for this target's objects.
include modules/shape/CMakeFiles/opencv_test_shape.dir/flags.make

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o: modules/shape/CMakeFiles/opencv_test_shape.dir/flags.make
modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o: ../modules/shape/test/test_main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o -c /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_main.cpp

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_main.cpp > CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.i

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_main.cpp -o CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.s

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o: modules/shape/CMakeFiles/opencv_test_shape.dir/flags.make
modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o: ../modules/shape/test/test_shape.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o -c /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_shape.cpp

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_shape.cpp > CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.i

modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv-3.4.8/modules/shape/test/test_shape.cpp -o CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.s

# Object files for target opencv_test_shape
opencv_test_shape_OBJECTS = \
"CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o" \
"CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o"

# External object files for target opencv_test_shape
opencv_test_shape_EXTERNAL_OBJECTS =

bin/opencv_test_shape: modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_main.cpp.o
bin/opencv_test_shape: modules/shape/CMakeFiles/opencv_test_shape.dir/test/test_shape.cpp.o
bin/opencv_test_shape: modules/shape/CMakeFiles/opencv_test_shape.dir/build.make
bin/opencv_test_shape: lib/libopencv_ts.a
bin/opencv_test_shape: lib/libopencv_shape.3.4.8.dylib
bin/opencv_test_shape: lib/libopencv_highgui.3.4.8.dylib
bin/opencv_test_shape: lib/libopencv_video.3.4.8.dylib
bin/opencv_test_shape: 3rdparty/lib/libippiw.a
bin/opencv_test_shape: 3rdparty/ippicv/ippicv_mac/icv/lib/intel64/libippicv.a
bin/opencv_test_shape: lib/libopencv_videoio.3.4.8.dylib
bin/opencv_test_shape: lib/libopencv_imgcodecs.3.4.8.dylib
bin/opencv_test_shape: lib/libopencv_imgproc.3.4.8.dylib
bin/opencv_test_shape: lib/libopencv_core.3.4.8.dylib
bin/opencv_test_shape: modules/shape/CMakeFiles/opencv_test_shape.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable ../../bin/opencv_test_shape"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/opencv_test_shape.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
modules/shape/CMakeFiles/opencv_test_shape.dir/build: bin/opencv_test_shape

.PHONY : modules/shape/CMakeFiles/opencv_test_shape.dir/build

modules/shape/CMakeFiles/opencv_test_shape.dir/clean:
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape && $(CMAKE_COMMAND) -P CMakeFiles/opencv_test_shape.dir/cmake_clean.cmake
.PHONY : modules/shape/CMakeFiles/opencv_test_shape.dir/clean

modules/shape/CMakeFiles/opencv_test_shape.dir/depend:
	cd /Users/sofiya/opencv/opencv-3.4.8/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/sofiya/opencv/opencv-3.4.8 /Users/sofiya/opencv/opencv-3.4.8/modules/shape /Users/sofiya/opencv/opencv-3.4.8/build /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape /Users/sofiya/opencv/opencv-3.4.8/build/modules/shape/CMakeFiles/opencv_test_shape.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : modules/shape/CMakeFiles/opencv_test_shape.dir/depend

