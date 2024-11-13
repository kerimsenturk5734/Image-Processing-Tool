package Filters.ImageFilters;

import java.awt.image.BufferedImage;

import Filters.Filter;

public class LaplaceFilter extends Filter{

	private static int[][] LAPLACE_KERNEL=new int[][]
			{{0,1,0},
			{1,-4,1},
			{0,1,0}};
			
			
	public LaplaceFilter() {
		super(LAPLACE_KERNEL);
	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {

		BufferedImage imgLaplace=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		int [][] grayScale=getGrayValues(img);
		
		int w=img.getWidth();
		int h=img.getHeight();
		
		int sum=0;
		
		for(int x=1;x<w-2;x++) {

			for(int y=1;y<h-2;y++) {
				
				sum = (mask[0][0] * grayScale[x-1][y-1]) + (mask[0][1] * grayScale[x][y-1]) + (mask[0][2] * grayScale[x+1][y-1]) +
                        (mask[1][0] * grayScale[x-1][y])   + (mask[1][1] * grayScale[x][y])   + (mask[1][2] * grayScale[x+1][y]) +
                        (mask[2][0] * grayScale[x-1][y+1]) + (mask[2][1] * grayScale[x][y+1]) + (mask[2][2] * grayScale[x+1][y+1]);				

				if(sum<0)
					sum=0;
				else if(sum>255)
					sum=255;
				
				imgLaplace.setRGB(x, y, toRGB(sum));
				
			}
		}
		
		return imgLaplace;
	}	

}
