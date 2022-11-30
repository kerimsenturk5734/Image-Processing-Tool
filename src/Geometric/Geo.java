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
	
	public static BufferedImage rotate( BufferedImage img, double degrees ) {
	    int width  = img.getWidth();
	    int height = img.getHeight();
	    
	    BufferedImage   newImage = new BufferedImage( width, height, img.getType() );

	    double angle = Math.toRadians( degrees );
	    double sin = Math.sin(angle);
	    double cos = Math.cos(angle);
	    double x0 = 0.5 * (width  - 1);     // point to rotate about
	    double y0 = 0.5 * (height - 1);     // center of image

	    // rotation
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            double a = x - x0;
	            double b = y - y0;
	            int xx = (int) (+a * cos - b * sin + x0);
	            int yy = (int) (+a * sin + b * cos + y0);

	            if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
	                newImage.setRGB(x, y, img.getRGB(xx, yy));
	            }
	        }
	    }
	   return newImage;
	}

	public static BufferedImage setOffSet(BufferedImage img,int xSet,int ySet) {
		int w=img.getWidth();
		int h=img.getHeight();
		
		BufferedImage imgOffSeted=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		for(int x=0;x<w;x++) {
			
			for(int y=0;y<h;y++) {
				
				int offSetVal=0;
				
				offSetVal=img.getRGB(x,y);
				
				int xx=x+xSet;
				int yy=y+ySet;
				
				if(!(xx>=w || (yy>=h) || (xx<0) || (yy<0))){
					imgOffSeted.setRGB(x+xSet, y+ySet, offSetVal);
				}
				
			}
		}
		
		return imgOffSeted;
	}

	public static BufferedImage reScale(BufferedImage img,double scaleFactor) {
		
		return null;
	}
}
