#!/bin/bash

# Clean previous build
rm -rf build/
mkdir -p build/classes

# Compile Java sources with dependencies
javac -cp "lib/*" -d build/classes src/com/kwcs/tools/files/*.java

# Create JAR with dependencies included
cd build/classes
jar -cfm ../MoveDate.jar ../../MANIFEST.MF com/
cd ../..

# Copy external JARs to build directory for distribution
cp lib/*.jar build/

echo "Build complete. JAR created at build/MoveDate.jar"