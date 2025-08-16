package com.kwcs.tools.files;

import java.io.File;

public class CorrectExt {

	
	private String workingDir = "C:\\Progcode\\Temp";

	
	public static void main(String[] args) {
		
		CorrectExt nm = new CorrectExt(args);
		nm.start();
	}
	
	public CorrectExt(String[] args) {

		if (args.length != 1) {
            usage();
			System.exit(-1);
		}
		
		workingDir = args[0];		
	}
	
		
	private void usage() {
        // TODO Auto-generated method stub
        
    }

    private void start() {

		try {
			subDir(new File(workingDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void subDir(File dir) throws Exception {

		File[] a = dir.listFiles();
		
		for (int i = 0; i < a.length; i++) {
			if (a[i].isDirectory()) {
				subDir(a[i]);
			} else {
				moveFile(a[i]);
			}
		}		
	}
	

	private void moveFile(File sourceFile) throws Exception {

		File dir = sourceFile.getParentFile();
		
		final String name = sourceFile.getName();
		File dest;
		
		for (int i = 0; i < 10; i++) {
			if (name.endsWith("" + i)) {

				int dot = name.lastIndexOf(".");
				int diff = name.length() - dot;
				
				if (dot >= 0 && diff > 3 && diff < 7) {

					String part1 = name.substring(0, dot);
					String part2 = name.substring(dot, dot + 4);

					dest = sourceFile;

					int ct = 0;
					while (dest.exists()) {
						dest = new File(dir, part1 + ct++ + part2);
					}

					sourceFile.renameTo(dest);
					System.out.println("Moving [" + sourceFile.getName()
							+ "] to [" + dest.getName() + "]");
				}
			}
		}
	}
}
