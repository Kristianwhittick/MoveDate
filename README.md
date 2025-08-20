# ⚠️ ARCHIVED

This repository is archived and has been replaced by [MediaOrganiser](https://github.com/kwcs/MediaOrganiser) - a Go implementation with improved performance and features.

---

# MoveDate

A Java utility that organizes files by date, extracting EXIF data from images when available.

## Build

```bash
bash build.sh
```

## Usage

```bash
java -jar build/MoveDate.jar <source> <destination>
```

- `<source>` - Directory containing files to organize
- `<destination>` - Output directory where files will be organized by date

## Dependencies

- metadata-extractor-2.6.2.jar
- xmpcore-5.1.2.jar