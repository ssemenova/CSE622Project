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
include modules/hdf/CMakeFiles/opencv_test_hdf.dir/depend.make

# Include the progress variables for this target.
include modules/hdf/CMakeFiles/opencv_test_hdf.dir/progress.make

# Include the compile flags for this target's objects.
include modules/hdf/CMakeFiles/opencv_test_hdf.dir/flags.make

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o: modules/hdf/CMakeFiles/opencv_test_hdf.dir/flags.make
modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_hdf5.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_hdf5.cpp

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_hdf5.cpp > CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.i

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_hdf5.cpp -o CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.s

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o: modules/hdf/CMakeFiles/opencv_test_hdf.dir/flags.make
modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_main.cpp

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_main.cpp > CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.i

modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/hdf/test/test_main.cpp -o CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.s

# Object files for target opencv_test_hdf
opencv_test_hdf_OBJECTS = \
"CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o" \
"CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o"

# External object files for target opencv_test_hdf
opencv_test_hdf_EXTERNAL_OBJECTS =

bin/opencv_test_hdf: modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_hdf5.cpp.o
bin/opencv_test_hdf: modules/hdf/CMakeFiles/opencv_test_hdf.dir/test/test_main.cpp.o
bin/opencv_test_hdf: modules/hdf/CMakeFiles/opencv_test_hdf.dir/build.make
bin/opencv_test_hdf: lib/libopencv_ts.a
bin/opencv_test_hdf: lib/libopencv_hdf.3.4.8.dylib
bin/opencv_test_hdf: lib/libopencv_highgui.3.4.8.dylib
bin/opencv_test_hdf: 3rdparty/lib/libippiw.a
bin/opencv_test_hdf: 3rdparty/ippicv/ippicv_mac/icv/lib/intel64/libippicv.a
bin/opencv_test_hdf: lib/libopencv_videoio.3.4.8.dylib
bin/opencv_test_hdf: lib/libopencv_imgcodecs.3.4.8.dylib
bin/opencv_test_hdf: lib/libopencv_imgproc.3.4.8.dylib
bin/opencv_test_hdf: lib/libopencv_core.3.4.8.dylib
bin/opencv_test_hdf: modules/hdf/CMakeFiles/opencv_test_hdf.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable ../../bin/opencv_test_hdf"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/opencv_test_hdf.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
modules/hdf/CMakeFiles/opencv_test_hdf.dir/build: bin/opencv_test_hdf

.PHONY : modules/hdf/CMakeFiles/opencv_test_hdf.dir/build

modules/hdf/CMakeFiles/opencv_test_hdf.dir/clean:
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf && $(CMAKE_COMMAND) -P CMakeFiles/opencv_test_hdf.dir/cmake_clean.cmake
.PHONY : modules/hdf/CMakeFiles/opencv_test_hdf.dir/clean

modules/hdf/CMakeFiles/opencv_test_hdf.dir/depend:
	cd /Users/sofiya/opencv/opencv-3.4.8/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/sofiya/opencv/opencv-3.4.8 /Users/sofiya/opencv/opencv_contrib/modules/hdf /Users/sofiya/opencv/opencv-3.4.8/build /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf /Users/sofiya/opencv/opencv-3.4.8/build/modules/hdf/CMakeFiles/opencv_test_hdf.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : modules/hdf/CMakeFiles/opencv_test_hdf.dir/depend
