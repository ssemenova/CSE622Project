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
include modules/xphoto/CMakeFiles/opencv_xphoto.dir/depend.make

# Include the progress variables for this target.
include modules/xphoto/CMakeFiles/opencv_xphoto.dir/progress.make

# Include the compile flags for this target's objects.
include modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/bm3d_image_denoising.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/bm3d_image_denoising.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/bm3d_image_denoising.cpp > CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/bm3d_image_denoising.cpp -o CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/dct_image_denoising.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/dct_image_denoising.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/dct_image_denoising.cpp > CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/dct_image_denoising.cpp -o CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/grayworld_white_balance.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/grayworld_white_balance.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/grayworld_white_balance.cpp > CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/grayworld_white_balance.cpp -o CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/inpainting.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/inpainting.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/inpainting.cpp > CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/inpainting.cpp -o CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/learning_based_color_balance.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/learning_based_color_balance.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/learning_based_color_balance.cpp > CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/learning_based_color_balance.cpp -o CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/simple_color_balance.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/simple_color_balance.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/simple_color_balance.cpp > CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/simple_color_balance.cpp -o CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.s

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o: modules/xphoto/CMakeFiles/opencv_xphoto.dir/flags.make
modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o: /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/tonemap.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_7) "Building CXX object modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o -c /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/tonemap.cpp

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.i"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/tonemap.cpp > CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.i

modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.s"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && /Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/sofiya/opencv/opencv_contrib/modules/xphoto/src/tonemap.cpp -o CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.s

# Object files for target opencv_xphoto
opencv_xphoto_OBJECTS = \
"CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o" \
"CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o"

# External object files for target opencv_xphoto
opencv_xphoto_EXTERNAL_OBJECTS =

lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/bm3d_image_denoising.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/dct_image_denoising.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/grayworld_white_balance.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/inpainting.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/learning_based_color_balance.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/simple_color_balance.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/src/tonemap.cpp.o
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/build.make
lib/libopencv_xphoto.3.4.8.dylib: lib/libopencv_photo.3.4.8.dylib
lib/libopencv_xphoto.3.4.8.dylib: 3rdparty/lib/libippiw.a
lib/libopencv_xphoto.3.4.8.dylib: 3rdparty/ippicv/ippicv_mac/icv/lib/intel64/libippicv.a
lib/libopencv_xphoto.3.4.8.dylib: lib/libopencv_imgproc.3.4.8.dylib
lib/libopencv_xphoto.3.4.8.dylib: lib/libopencv_core.3.4.8.dylib
lib/libopencv_xphoto.3.4.8.dylib: modules/xphoto/CMakeFiles/opencv_xphoto.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/sofiya/opencv/opencv-3.4.8/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_8) "Linking CXX shared library ../../lib/libopencv_xphoto.dylib"
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/opencv_xphoto.dir/link.txt --verbose=$(VERBOSE)
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && $(CMAKE_COMMAND) -E cmake_symlink_library ../../lib/libopencv_xphoto.3.4.8.dylib ../../lib/libopencv_xphoto.3.4.dylib ../../lib/libopencv_xphoto.dylib

lib/libopencv_xphoto.3.4.dylib: lib/libopencv_xphoto.3.4.8.dylib
	@$(CMAKE_COMMAND) -E touch_nocreate lib/libopencv_xphoto.3.4.dylib

lib/libopencv_xphoto.dylib: lib/libopencv_xphoto.3.4.8.dylib
	@$(CMAKE_COMMAND) -E touch_nocreate lib/libopencv_xphoto.dylib

# Rule to build all files generated by this target.
modules/xphoto/CMakeFiles/opencv_xphoto.dir/build: lib/libopencv_xphoto.dylib

.PHONY : modules/xphoto/CMakeFiles/opencv_xphoto.dir/build

modules/xphoto/CMakeFiles/opencv_xphoto.dir/clean:
	cd /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto && $(CMAKE_COMMAND) -P CMakeFiles/opencv_xphoto.dir/cmake_clean.cmake
.PHONY : modules/xphoto/CMakeFiles/opencv_xphoto.dir/clean

modules/xphoto/CMakeFiles/opencv_xphoto.dir/depend:
	cd /Users/sofiya/opencv/opencv-3.4.8/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/sofiya/opencv/opencv-3.4.8 /Users/sofiya/opencv/opencv_contrib/modules/xphoto /Users/sofiya/opencv/opencv-3.4.8/build /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto /Users/sofiya/opencv/opencv-3.4.8/build/modules/xphoto/CMakeFiles/opencv_xphoto.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : modules/xphoto/CMakeFiles/opencv_xphoto.dir/depend

