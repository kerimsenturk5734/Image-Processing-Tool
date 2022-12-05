package Morphology;

import java.awt.image.BufferedImage;
import java.util.Collections;

import Filters.Filter;

public class Morp {
	
	public static int[][] DEFAULT_KERNEL=new int[][] {
		{255,255,255},{255,255,255},{255,255,255}
	};
	
	public static BufferedImage dilate(BufferedImage img,int[][] kernel) {
		 	int w = img.getWidth();
	        int h = img.getHeight();
	        
	        int[][] grayScale=Filter.getGrayValues(img);
	        
	        BufferedImage dilatedImg = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
	        
	        for(int x=1;x<w-2;x++) {

				for(int y=1;y<h-2;y++) {
				
					for(int i=0;i<kernel.length;i++) {

						for(int j=0;j<kernel[0].length;j++) {
							//karþýlaþtýrma iþlemini yap
							if(kernel[i][j]==grayScale[x+i-1][y+i-1]) {
								dilatedImg.setRGB(x, y, Filter.toRGB(255));
								i=kernel.length;
								break;
								}
							}
						}		
				}
			}
	        return dilatedImg;
	   }

	public static BufferedImage erode(BufferedImage img,int[][] kernel) {
		int w = img.getWidth();
        int h = img.getHeight();
        
        int[][] grayScale=Filter.getGrayValues(img);
        
        BufferedImage erotedImg = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
        
        for(int x=1;x<w-2;x++) {

			for(int y=1;y<h-2;y++) {
			
				boolean confirm=true;
				
				for(int i=0;i<kernel.length;i++) {

					for(int j=0;j<kernel[0].length;j++) {
						//karþýlaþtýrma iþlemini yap
						if(kernel[i][j]!=grayScale[x+i-1][y+i-1]) {
							confirm=false;
							i=kernel.length;
							break;
						}
					}		
			}
				//When masking finished
				if(confirm)
					erotedImg.setRGB(x, y, Filter.toRGB(255));
				else 
					erotedImg.setRGB(x, y, Filter.toRGB(0));
				
		}

   }

        return erotedImg;
	}
	
	public static BufferedImage open(BufferedImage img,int[][] kernel) {
		return erode(dilate(img, kernel),kernel);
	}

	public static BufferedImage close(BufferedImage img,int[][] kernel) {
		return dilate(erode(img, kernel),kernel);
	}

}
