package Filters.ImageFilters;

import java.awt.image.BufferedImage;

import Filters.Filter;

public class HighFilter extends Filter{

	/*public static int[][] LAPLACE_KERNEL=new int[][]
			{{0,1,0},
			{1,-4,1},
			{0,1,0}};*/
    private int[][] maskY;
    
	
	public static int[][] SOBEL_KERNEL=new int[][]
							{{-1,-2,-1},
							{0,0,0},
							{1,2,1}};
					
	public static int[][] PREWITT_KERNEL=new int[][]
							{{-1,-1,-1},
							{0,0,0},
							{1,1,1}};
			
	
	
	public HighFilter(int[][] mask) {
		super(mask);
		createMask();
	}
	
	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		 int w = img.getWidth();
	     int h = img.getHeight();
	       
	     int pixel_x,pixel_y;
	    
	     int [][] grayScale=getGrayValues(img);
	     
	        for (int x=1; x < w-2; x++) {
	            for (int y=1; y < h-2; y++) {
	            	 pixel_x = (mask[0][0] * grayScale[x-1][y-1]) + (mask[0][1] * grayScale[x][y-1]) + (mask[0][2] * grayScale[x+1][y-1]) +
	                         (mask[1][0] * grayScale[x-1][y])   + (mask[1][1] * grayScale[x][y])   + (mask[1][2] * grayScale[x+1][y]) +
	                         (mask[2][0] * grayScale[x-1][y+1]) + (mask[2][1] * grayScale[x][y+1]) + (mask[2][2] * grayScale[x+1][y+1]);
	            	 
	                 pixel_y = (maskY[0][0] * grayScale[x-1][y-1]) + (maskY[0][1] * grayScale[x][y-1]) + (maskY[0][2] * grayScale[x+1][y-1]) +
	                         (maskY[1][0] * grayScale[x-1][y])   + (maskY[1][1] * grayScale[x][y])   + (maskY[1][2] * grayScale[x+1][y]) +
	                         (maskY[2][0] * grayScale[x-1][y+1]) + (maskY[2][1] * grayScale[x][y+1]) + (maskY[2][2] * grayScale[x+1][y+1]);
	            	 
	                 int val = (int)Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));
	                
	                 
	                 img.setRGB(x, y, toRGB(val));
	                 
	            }
	        }
	       
	       return img;
	}
	
	@Override
	protected void createMask() {
		maskY=new int[mask.length][mask.length];
		
		for(int x=0;x<mask.length;x++) {
			for(int y=0;y<mask.length;y++) {
				maskY[x][y]=mask[y][x];
			}
		}
	}
	
}
