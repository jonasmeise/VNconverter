package legarchive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import type.*;
import type.LegArchive.DataPosition;
import type.LegArchive.LegArchive;
import extra.KPM;
import extra.ConsoleLog;


//Based on .legarchive file format:
//https://github.com/MyLegGuy/Higurashi-Vita/wiki/.legArchive-format

public class LegExtractor {
	
	public String filePath = "C:\\Users\\Jonas\\Downloads\\Yume Miru Kusuri VHD [VNDS Clone]\\Yume Miru Kusuri\\SEArchive.legarchive"; //the archive
	private KPM myKPM = new KPM(); //for pattern matching
	private ConsoleLog myLog = new ConsoleLog(); //for logging purposes
	private int archiveOffset = 0; //where the actual archive begins
	
	public static void main(String[] args) {
		LegExtractor myExtractor = new LegExtractor();
		
		myExtractor.run();
	}
	
	public void run() {
		byte[] rawData = readFromFile(filePath);
		LegArchive myLeg = parseStructureIntoLeg(rawData);
		writeDataFromTable(rawData, myLeg);
	}
	
	public byte[] readFromFile(String filePath) {
		myLog.log("Start to read...");
		byte[] returnArray = null;
		Path path = Paths.get(filePath);
		
		try {
			returnArray = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public LegArchive parseStructureIntoLeg(byte[] rawData) {
		LegArchive parsedArchive = new LegArchive("archive001");
		
		//find Table
		int tablePos = indexOfByteArray(rawData, "LEGARCHTBL");
		myLog.log("Found table at POS #" + tablePos + ".");
		
		if(tablePos > -1) { //sanity check
			int amountOfFiles = bytesToInt(rawData, tablePos + 10, 4);
			myLog.log(amountOfFiles + " will be read.");
			
			//read file data
			
			int fileCounter = 1;
			int readerPos = tablePos + 10 + 4;
			String newChar = "";
			
			while(fileCounter <= amountOfFiles ) {
				DataPosition currentData = new DataPosition();
				//read data info
				
				String fileName = "";
				
				while(rawData[readerPos] != 0) {
					try {
						 newChar = new String(new byte[]{rawData[readerPos]}, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					fileName = fileName + newChar;
					readerPos++;
				}
				
				//find Position
				readerPos++;
				int dataPos = bytesToInt(rawData, readerPos, 8);
				readerPos = readerPos + 8;
				
				//find Length
				int dataLength = bytesToInt(rawData, readerPos, 4);
				readerPos = readerPos + 4;
				
				myLog.log("Found file #" + fileCounter + " '" + fileName + "' at POS #" + dataPos + " with a length of " + dataLength/1024 + "kb.");
				
				currentData.setDataName(fileName.toString());
				currentData.setStart(dataPos);
				currentData.setLength(dataLength);
				
				parsedArchive.addTable(currentData);
				
				fileCounter++;
			}
		}
		
		return parsedArchive;
	}
	
	public void writeDataFromTable(byte[] rawData, LegArchive legArchive) {
		for(DataPosition dp : legArchive.getTable()) {
			int dataStart = dp.getStart();
			
			File targetFile = new File(dp.getDataName());
			File parent = targetFile.getParentFile();
			
			if (!parent.exists() && !parent.mkdirs()) {
			    throw new IllegalStateException("Couldn't create dir: " + parent);
			}
			
			try (FileOutputStream fos = new FileOutputStream(targetFile, true)) {
				for(int currentPos=0;currentPos < dp.getLength();currentPos++) {
					fos.write(rawData[dataStart + currentPos]);
				}
			    //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			myLog.log("Wrote file '" + dp.getDataName() + "'.");
		}
	}
	
	public int indexOfByteArray(byte[] data, String toFind) {
		return myKPM.indexOf(data, toFind.getBytes());
	}
	
	public int bytesToInt(byte[] byteArray, int start, int lengthOfInt) { //convert byte arrays to int
		byte[] newArray = new byte[lengthOfInt];
		
		for(int i=0;i<lengthOfInt;i++) {
			newArray[i] = byteArray[start+i];
		}
		
		ByteBuffer bb = ByteBuffer.wrap(newArray);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		
		return bb.getInt();
	}
}
