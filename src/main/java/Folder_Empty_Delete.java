import java.io.*;
import java.util.*;

import java.io.*;
/*
    Data structure is as follows
    A Map with key=fileSize and value=list of filenames
*/
public class Folder_Empty_Delete {
  private static List<String> includeList = new ArrayList<String>();
  private static List<String> exclusionList = new ArrayList<String>();
  private static SortedMap<Long,List<String>> fileMap = Collections.synchronizedSortedMap(new TreeMap<Long,List<String>>());
  private static FileOutputStream fos =  null;
  /******************************************************************************************/
  public static void main(String[] args) throws Exception {
	Map hm = PropertiesLoader.loadToHashMap("input.properties");
	includeList   = (List<String>)hm.get("INCLUDE_PATH");
	exclusionList = (List<String>)hm.get("EXCLUDE_PATH");
    ArrayList<String> fileList = new ArrayList<String>();
    System.out.println("REM ----- includeList.get(0)="+includeList.get(0));

    //if((args != null) && (args.length > 0)) {
    //    folderSizeMap = loadFileLists(args);
    //} else {
    //    //folderSizeMap = loadFileLists("."); // if no arguments are given, process current directory
        fileList.addAll(getFolderList("."));
    //}


	//fileList.addAll(getFolderList(includeList.get(0)));
    System.out.println("REM ----- looping directories - start");
    System.out.println("REM ----- fileList.size()="+fileList.size());
	fos = new FileOutputStream(new File("empty_folders.bat"));
    for(int i=0;i<fileList.size();i++) {
	  if(i%1000 == 0) {
		//System.out.println(i+",");
	  }
      //System.out.println("----------------------------------------------------------------------------------------------------------------");
      String fileName = fileList.get(i);
      //System.out.println("folderName       = " + fileName);
      File file = new File(fileName);
	  if(!file.isDirectory()) { 
	    //if it is a file, go to next line item
		continue;
	  }
	  if(fileName.contains(".git")) { 
	    //if it is a git directory, go to next line item
		continue;
	  }
	  if(fileName.contains("AUDIO_TS")) { 
	    //if it is a git directory, go to next line item
		continue;
	  }
	  
      File[] children = file.listFiles();
      //System.out.println("children="+children);
	  if(children != null && children.length == 0) {
		String command = "RD \"" + file.getAbsolutePath() +"\"";
		System.out.println(command);
        //fos.write((file.getAbsolutePath()+"\r\n").getBytes());
        fos.write((command+"\r\n").getBytes());
	  }
      //System.out.print(","+i);
    }
    fos.close();
    System.out.println("REM ----- program end");
	
  }
  /******************************************************************************************/
  public static ArrayList<String> readFileList(String fileName) throws Exception {
	System.out.println("REM ----- loading sob.txt - start");
    ArrayList<String> returnArray = new ArrayList<String>();
    BufferedReader input =  new BufferedReader(new FileReader(new File(fileName)));
    try {
        String line = null; //not declared within while loop
        while (( line = input.readLine()) != null){
          returnArray.add(line);
        }
      }
      finally {
        input.close();
      }
	System.out.println("REM ----- loading sob.txt - end");
    return returnArray;
  }
  /******************************************************************************************/
  /******************************************************************************************/
    public static List<String> getFolderList(String sourcePath) {
        //System.out.println(sourcePath);
		//if(exclusionList.contains(sourcePath)) {
		//	return new ArrayList<String>();
		//}
        File dir = new File(sourcePath);
		if(!dir.exists()) {
			return new ArrayList<String>();
		}
        List<String> fileTree = new ArrayList<String>();
		try {
			for (File entry : dir.listFiles()) {
				if (entry.isFile()) {
					//Skip files. only incude folders
				} else {
					fileTree.add(entry.getAbsolutePath());
					fileTree.addAll(getFolderList(entry.getAbsolutePath()));
				}
			}
		} catch(Exception e) {
			//
		}
        return fileTree;
    }
 
  /******************************************************************************************/

  /******************************************************************************************/

  /******************************************************************************************/

  /******************************************************************************************/

  /******************************************************************************************/

  /******************************************************************************************/
}


