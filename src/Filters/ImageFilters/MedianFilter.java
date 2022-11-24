package Filters.ImageFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import java.util.Collections;


import Filters.Filter;

public class MedianFilter extends Filter{

	public MedianFilter(int format) {
		super(format);
		
		if(format%2==0)
			throw new IllegalArgumentException("Format must be odd value in Median Filter");
		
	}

	
	@Override
	public BufferedImage applyFilter(BufferedImage img) {

		int W=getFormat();
		
		ArrayList<Integer> sumRGB=new ArrayList<>();
		
		
		for(int x=0;x<img.getWidth()-W;x++) {

			for(int y=0;y<img.getHeight()-W;y++) {
				
				int medianRGB=0;
				
				for(int i=0;i<W;i++) {

					for(int j=0;j<W;j++) 
						sumRGB.add(img.getRGB(x+i, y+j));
						
						
						
					//Sorting... RGB lists
					Collections.sort(sumRGB);
					
					
					//Get median of RGB Lists
					
					if(W%2!=0)
						medianRGB=sumRGB.get(sumRGB.size()/2);
					else 
						medianRGB=sumRGB.get(sumRGB.size()/2)+(sumRGB.get(sumRGB.size()/2)-1)/2;
				}
				
				//Setting... the median value
				int sumOfCoEff=sumOfMaskCoEfficent();
				try {
					img.setRGB(x+W/2, y+W/2,medianRGB);
				} catch (Exception e) {
					System.out.println("out of bound");
				}
				
				//Cleaning arrays
				sumRGB.clear();
			}
		}
		
		return img;
	}
}
