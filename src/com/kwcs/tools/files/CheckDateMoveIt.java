/**
 * 
 */
package com.kwcs.tools.files;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kris
 *
 */
public class CheckDateMoveIt {

    private String workingDir;
    // private String outputDir;
    // private File output;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");

    private int counter = 0;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CheckDateMoveIt nm = new CheckDateMoveIt(args);
        nm.start();
    }

    /**
     * 
     */
    public CheckDateMoveIt(String [] args) {
        super();

        if (args.length != 1) {
            usage();
            System.exit(-1);
        }
        
        workingDir = args[0];
    }

    
    private void usage() {

        String [] usage = {"Usage: CheckDateMoveIt <source>", "", 
                "Where: <source> is the directory that contains many subfolder of files" };
        
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
        System.out.println("Total : " + counter);
    }

    private void subDir(File dir) throws Exception {

        File[] dirList = dir.listFiles();
        
        for (int i = 0; i < dirList.length; i++) {
            if (dirList[i].isDirectory()) {
                subFiles(dirList[i]);
            } 
        }       
    }
    
    private void subFiles(File dir) throws Exception {

        File[] dirList = dir.listFiles();
        
        for (int i = 0; i < dirList.length; i++) {
            if (!dirList[i].isDirectory()) {            
                checkFile(dirList[i]);
            }
        }       
    }

    private void checkFile(File sourceFile) throws Exception {
        
        final String name = sourceFile.getName();
        final long lastMod = sourceFile.lastModified();
        
        final String dateStr = sdf.format(new Date(lastMod));

        // final sourceFile.getParent();
        
        if (name.endsWith("JPG") || name.endsWith("jpg")) {
            if (!sourceFile.getAbsolutePath().contains(dateStr)) {
                System.out.println("Name:\t" + sourceFile.getCanonicalPath() +  " Date :" + dateStr);
                counter++;
            }
        }
        
        /*
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
        */
    }

    
    /*
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
    */
}
