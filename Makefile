JAVA_FILES = $(wildcard src/com/kwcs/tools/files/*.java)
CLASS_FILES = $(JAVA_FILES:src/%.java=build/classes/%.class)
JAR_FILE = build/MoveDate.jar
LIBS = lib/metadata-extractor-2.6.2.jar:lib/xmpcore-5.1.2.jar

.PHONY: all clean jar

all: jar

build/classes:
	mkdir -p build/classes

$(CLASS_FILES): build/classes $(JAVA_FILES)
	javac -cp "$(LIBS)" -d build/classes $(JAVA_FILES)

jar: $(JAR_FILE)

$(JAR_FILE): $(CLASS_FILES) MANIFEST.MF
	cd build/classes && jar -cfm ../MoveDate.jar ../../MANIFEST.MF com/
	cp lib/*.jar build/

clean:
	rm -rf build/

run: jar
	java -jar $(JAR_FILE)