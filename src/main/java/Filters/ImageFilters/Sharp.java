package Filters.ImageFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Filters.Filter;

public class Sharp extends Filter{

	public final static int[][] DEFAULT_KERNEL=new int[][] {
		{0,-2,0},
		{-2,11,-2},
		{0,-2,0}
	};
	
	public Sharp(int[][] mask) {
		super(mask);
	}
	
	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		
		int w = img.getWidth();
        int h = img.getHeight();
        
        //Get Coeff of kernel
		int inCoeffKernel=sumOfMaskCoEfficent();
		
	        
	    BufferedImage sharpedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        
	      //iterate kernel on image
	        for(int x=1;x<w-2;x++) {

				for(int y=1;y<h-2;y++) {
					int iteratedR=0;
					int iteratedG=0;
					int iteratedB=0;
					
					//find output kernel
					for(int i=1;i<mask.length+1;i++) {
						for(int j=1;j<mask[0].length+1;j++) {
							
							int currPixVal=img.getRGB(x+i-2, y+j-2);
							Color currPixCol=new Color(currPixVal);
							
							iteratedR+=
									mask[i-1][j-1]*currPixCol.getRed();
							iteratedG+=
									mask[i-1][j-1]*currPixCol.getGreen();
							iteratedB+=
									mask[i-1][j-1]*currPixCol.getBlue();
							}
						}
					

					//divide output kernel coeff to input kernel coeff
					int divResR=(iteratedR/inCoeffKernel);
					int divResG=(iteratedG/inCoeffKernel);
					int divResB=(iteratedB/inCoeffKernel);
					
					if (divResR > 255) divResR = 255;
					if (divResG > 255) divResG = 255;
					if (divResB > 255) divResB = 255;
					 
					if (divResR < 0) divResR = 0;
					if (divResG < 0) divResG = 0;
					if (divResB < 0) divResB = 0;
					
					//set the result center of kernel coordinate	
					sharpedImg.setRGB(x, y, new Color(divResR,divResG,divResB).getRGB());
				}
			}
	        return sharpedImg;
		
	}
}
