package com.kwcs.tools.files;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		final String dateStr = sdf.format(new Date(lastMod));

		File dateDir = new File(output, dateStr);

		File dest = new File(dateDir, name);
		
		if (!makeDateDir(dateDir)) {
			// check name
			long ct = 0;
			int dot = name.lastIndexOf(".");
			String first = "";
			String sec = "";
			if (dot < 0){
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
}
