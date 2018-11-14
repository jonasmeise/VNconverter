package extra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtil {
	private ConsoleLog myLog = new ConsoleLog(); //for logging purposes
	
	public ArrayList<String> getFilesInFolder(File folder, String fileType, boolean includeSubfolders) {
		ArrayList<String> returnList = new ArrayList<String>();
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory() && includeSubfolders) {
	            returnList.addAll(getFilesInFolder(fileEntry, fileType, true));
	        } else {
	        	if(fileType.isEmpty() || (!fileType.isEmpty() & fileEntry.getAbsolutePath().endsWith(fileType))) {
	        		returnList.add(fileEntry.getAbsolutePath());
	        		myLog.log("Added '" + fileEntry.getAbsolutePath() + "' to the list!");
	        	}
	        }
	    }
	    
	    myLog.log("--------");
	    myLog.log("Found " + returnList.size() + " files in folder '" + folder.getPath() + "'.");
	    
		return returnList;
	}
	
	public ArrayList<String> getFilesInFolder(String folder, String fileType, boolean includeSubfolders) {
		return getFilesInFolder(new File(folder), fileType, includeSubfolders);
	}
	
	public ArrayList<String> readFromFileArrayList(String filePath) {
		ArrayList<String> list = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if(!line.isEmpty() && line!="") {
					//System.out.println(line);
					list.add(line);
				}
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> readFromFileArrayList(File filePath) {
		return readFromFileArrayList(filePath.getAbsolutePath());
	}
	
	public String readFromFile(String filePath) {
		Path path = Paths.get(filePath);
		Charset charset = StandardCharsets.UTF_8;

		String content = "";
		try {
			content = new String(Files.readAllBytes(path), charset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	public String readFromFile(File filePath) {
		return readFromFile(filePath.getAbsolutePath());
	}
	
	public FileWriter createWriter(String path) throws IOException {
		File file = new File(path);
		FileWriter fr = new FileWriter(file, true);
		return fr;
	}
}
