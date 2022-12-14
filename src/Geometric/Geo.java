package Geometric;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Jama.Matrix;

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
	

	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    
	    return resizedImage;
	}

	public static BufferedImage transformPerspective
		(BufferedImage img,int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4,
				int X1,int Y1,int X2,int Y2,int X3,int Y3,int X4,int Y4){
		
		//[C]=[B].[A]
		double[][] c=new double[][] {
			{X1},
			{Y1},
			{X2},
			{Y2},
			{X3},
			{Y3},
			{X4},
			{Y4}
		};
		
		double[][] b=new double[][] {
			{x1,y1,1,0,0,0,-x1*X1,-y1*X1},
			{0,0,0,x1,y1,1,-x1*Y1,-x1*Y1},
			{x2,y2,1,0,0,0,-x2*X2,-y2*X2},
			{0,0,0,x2,y2,1,-x2*Y2,-x2*Y2},
			{x3,y3,1,0,0,0,-x3*X3,-y3*X3},
			{0,0,0,x3,y3,1,-x3*Y3,-x3*Y3},
			{x4,y4,1,0,0,0,-x4*X4,-y4*X4},
			{0,0,0,x4,y4,1,-x4*Y4,-x4*Y4}
		};
		
		
		Matrix C=new Matrix(c);
		Matrix B=new Matrix(b);
		
		//[A]=[B]'[C]
		Matrix A=B.inverse().times(C);
		
		BufferedImage outImg=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<img.getWidth();x++) {
			
			for(int y=0;y<img.getHeight();y++) {
				
				int z=(int)(A.get(6, 0)*x+A.get(7, 0)*y+1);
				//System.out.println(A.get(6, 0)+"|"+x+"|"+A.get(7,0)+"|"+y);
				//System.out.println(A.get(0, 0)+"|"+x+"|"+A.get(1,0)+"|"+y+"|"+A.get(2, 0)+"|"+z);
				int outX=(int)((A.get(0, 0)*x+A.get(1,0)*y+A.get(2, 0))/z);
				int outY=(int)((A.get(3, 0)*x+A.get(4,0)*y+A.get(5, 0))/z);
				
				//Set img pixel
				//System.out.println(outX+" "+outY+"\n");
				try {
					outImg.setRGB(outX, outY, img.getRGB(x, y));
				} catch (Exception e) {
					// TODO: handle exception
				}
	
			}
			
		}
		
		for (double[] ds : A.getArray()) {
			for (double d : ds) {
				System.out.println(d);
			}
		}
		
		return outImg;
	
	}

	
}
