package images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import extra.ConsoleLog;
import extra.FileUtil;

public class MaskApply {
	
	private ConsoleLog myLog = new ConsoleLog(); //for logging purposes
	private FileUtil fu = new FileUtil();
	private String folderPath = "C:\\Users\\Jonas\\Downloads\\KillerQueen\\2change";
	
	public static void main(String[] args) {
		MaskApply myself = new MaskApply();
		myself.run();
	}
	
	public void run() {
		ArrayList<String> filesToChange = fu.getFilesInFolder(new File(folderPath), "bmp", true);
		
		for(String file : filesToChange) {
			try {
				String[] pairsAlphaMask = splitImage(file, 0.5, 0);
				applyMask(pairsAlphaMask[0], pairsAlphaMask[1], pairsAlphaMask[2]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void applyMask(String sourceFile, String maskFile, String outputFile) throws IOException { //xxx_alpha.png, xxx_mask.png
		BufferedImage image = ImageIO.read(new File(sourceFile));
		BufferedImage mask = ImageIO.read(new File(maskFile));

		
		myLog.log("Combining image '" + sourceFile + "' with mask '" + maskFile + "'.");
		
		int width = image.getWidth();
	    int height = image.getHeight();

	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    
	    for(int x = 0; x < width; x++){
	      for(int y = 0; y < height; y++){
	    	Color imageCol = new Color(image.getRGB(x, y));
	    	Color maskCol = new Color(mask.getRGB(x, y));
	    	
	    	newImage.setRGB(x, y, new Color(imageCol.getRed(), imageCol.getGreen(), imageCol.getBlue(), 255-maskCol.getBlue()).getRGB());
	      }
	    }
	    String ordinaryFileName = new File(outputFile).getName();
	    
	    String newFilePath = new File(outputFile).getParent() + "\\masked\\" + ordinaryFileName.substring(0, ordinaryFileName.length()-4) + ".png";
	    
	    File targetFile = new File(newFilePath); //check if folder already exists
		File parent = targetFile.getParentFile();
		
		if (!parent.exists() && !parent.mkdirs()) {
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}
	    
	    ImageIO.write(newImage, "png", new File(newFilePath));
	}
	
	public String[] splitImage(String sourceFile,double cutX, double cutY) throws IOException {
		String[] pairArray = new String[3];
		
		myLog.log("Splitting image '" + sourceFile + "'. @W" + cutX + "@H" + cutY);
		if(cutX>0) {
			BufferedImage source = ImageIO.read(new File(sourceFile));
			
			String newAlphaName = sourceFile.substring(0, sourceFile.length()-4) + "_image.png";
			String newMaskName = sourceFile.substring(0, sourceFile.length()-4) + "_mask.png";
			
			ImageIO.write(source.getSubimage(0, 0, (int) (cutX*source.getWidth()), source.getHeight()), "png", new File(newAlphaName));
			ImageIO.write(source.getSubimage((int) (cutX*source.getWidth()), 0, (int) (cutX*source.getWidth()), source.getHeight()), "png", new File(newMaskName));
			
			pairArray[0] = newAlphaName;
			pairArray[1] = newMaskName;
			pairArray[2] = sourceFile;
		}
		else if(cutY>0) {
			//nope
		}
		else {
			myLog.log("Missing parameters for splitImage function!");
			return null;
		}
		
		return pairArray;
	}
}
