package Geometric;

import java.awt.image.BufferedImage;

public class Geo {
	
	public static BufferedImage invertX(BufferedImage img) {
		int w=img.getWidth();
		int h=img.getHeight();
		
		BufferedImage imgInvert=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		for(int x=0;x<w;x++) {
			
			for(int y=0;y<h;y++) {
				
				int invertVal=img.getRGB(w-x-1, y);
				
				imgInvert.setRGB(x, y, invertVal);
			}
		}
		
		return imgInvert;
	}
	
	public static BufferedImage invertY(BufferedImage img) {
		int w=img.getWidth();
		int h=img.getHeight();
		
		BufferedImage imgInvert=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		for(int x=0;x<w;x++) {
			
			for(int y=0;y<h;y++) {
				
				int invertVal=img.getRGB(x, h-y-1);
				
				imgInvert.setRGB(x, y, invertVal);
			}
		}
		
		return imgInvert;
	}
	
	
}
