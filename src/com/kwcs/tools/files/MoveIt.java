package com.kwcs.tools.files;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class MoveIt {


	private String workingDir;
	private String outputDir;
	private File output;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");

	public static void main(String[] args) {

		MoveIt nm = new MoveIt(args);
		nm.start();
	}

	public MoveIt(String [] args) {
		super();

		if (args.length != 2) {
			usage();
			System.exit(-1);
		}

		workingDir = args[0];
		outputDir = args[1];

		output = new File(outputDir);
		output.mkdir();
	}


	private void usage() {

		String [] usage = {"Usage: MoveIt <source> <destination>", "",
				"Where: <source> is the directory that contains many subfolder of files",
				"       <destination> is the output directory." };

		for (int i = 0; i < usage.length; i++) {
			System.out.println(usage[i]);
		}
	}

	private void start() {

		File startDir = new File(workingDir);

		try {
			subDir(startDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void subDir(File dir) throws Exception {

		File[] dirList = dir.listFiles();

		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].isDirectory()) {
				subDir(dirList[i]);
			} else {
				moveFile(dirList[i]);
			}
		}
	}


	private void moveFile(File sourceFile) throws Exception {

		final String name = sourceFile.getName();
		final long lastMod = sourceFile.lastModified();
		String dateStr = sdf.format(new Date(lastMod));
		
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(sourceFile);
			
			// obtain the Exif directory
			ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);

			// query the tag's value
			Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

			//System.out.println(date.toString());
			dateStr = sdf.format(date);
		} catch (Exception e) {
			
		}
		

		File dateDir = new File(output, dateStr);

		File dest = new File(dateDir, name);

		if (!makeDateDir(dateDir)) {
			// check name
			long ct = 0;
			int dot = name.lastIndexOf(".");
			String first = "";
			String sec = "";
			if (dot > 0){
				first = name.substring(0, dot);
				sec = name.substring(dot);
			}

			while (dest.exists()){
				if (dot < 0){
					dest = new File(dateDir, name + ct++);
				} else {
					dest = new File(dateDir, first + ct++ +  sec);
				}
			}
		}

		sourceFile.renameTo(dest);
		System.out.println("Moving [" + sourceFile.getName() + "] to [" + dest.getAbsolutePath() + "]");
	}


	private boolean makeDateDir(File dateDir) throws Exception {


		if (dateDir.exists()){
			if (dateDir.isDirectory()) {
				return false;
			}
			throw new Exception("Output Directory is a file : " + dateDir.getAbsolutePath());
		}

		dateDir.mkdir();
		return true;
	}

/*
	private Date getJPGDateTaken(String fileName) {

        File file = new File( fileName );
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            // pick the first available ImageReader
            ImageReader reader = readers.next();
            // attach source to the reader
            reader.setInput(iis, true);
            // read metadata of first image
            IIOMetadata metadata = reader.getImageMetadata(0);
            String[] names = metadata.getMetadataFormatNames();
            int length = names.length;
            for (int i = 0; i < length; i++) {
                System.out.println( "Format name: " + names[ i ] );
                displayMetadata(metadata.getAsTree(names[i]));
            }
        }
    }
*/
}