package vnds;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import extra.ConsoleLog;
import extra.FileUtil;

public class Editor {
	private String folderPath = "C:\\Users\\Jonas\\Downloads\\Yume Miru Kusuri VHD [VNDS Clone]\\Yume Miru Kusuri\\script";
	private FileUtil fu = new FileUtil();
	private ConsoleLog myLog;
	
	public static void main(String[] args) {
		Editor myEditor = new Editor();
		
		myEditor.run();
	}
	
	public void run() {
		myLog = new ConsoleLog();
		String[] replacementArray = {"voice/", "", "music/", "", "se/", ""}; 
		replaceInFolder(folderPath, replacementArray, null);
	}
	
	//Replacement array: {pattern_old1, pattern_new1, pattern_old2, pattern_new2, ...}
	//Exception array: Filenames that should not be affected
	
	public void replaceInFolder(String folderPath, String[] replacementArray, String[] exceptionArray) {
		if(replacementArray.length % 2 != 0) {
			myLog.log("Array with replacement arrays has the wrong format! It has to have an even length!");
		}
		else {		
			List<String> allFiles = fu.getFilesInFolder(folderPath, "", true);
			
			for(String currentFilePath : allFiles) {
				boolean cancel = false;
				
				myLog.log("Reading from file '" + currentFilePath + "'.");
				String currentContent = fu.readFromFile(currentFilePath);
				
				if(exceptionArray!=null) { 
					if(exceptionArray.length != 0) {
						for(int x=0;x<exceptionArray.length;x++) {
							if(currentFilePath.endsWith(exceptionArray[x])) {
								cancel = true;
							}
						}
					}
				}

				if(!cancel) {
					for(int i=0;i<replacementArray.length;i=i+2) {
						currentContent = currentContent.replace(replacementArray[i], replacementArray[i+1]);
					}
					
					try {
						Files.write(Paths.get(currentFilePath), currentContent.getBytes(StandardCharsets.UTF_8));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			myLog.log(allFiles.size() + " files edited!");
		}
	}
	
	
}
