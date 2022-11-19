package Filters.ImageFilters;

import java.awt.image.BufferedImage;

import Filters.Filter;

public class GaussFilter extends Filter {

	public GaussFilter(int format) {
		super(format);
	}


	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		return super.applyFilter(img);
	}

	@Override
	protected void createMask() {
		System.out.println("Gauss Filtresi oluþturuldu");
		//create mask and assign mask to this.mask[][]
	
		double sigma = 1;
		int W = mask.length;
		double kernel[][]=new double[W][W];
		double mean = W/2;
		double sum = 0.0; // For accumulating the kernel values
		for (int x = 0; x < W; ++x) 
		    for (int y = 0; y < W; ++y) {
		        kernel[x][y] = Math.exp( -0.5 * (Math.pow((x-mean)/sigma, 2.0) + Math.pow((y-mean)/sigma,2.0)) )
		                         / (2 * Math.PI * sigma * sigma);

		        // Accumulate the kernel values
		        sum += kernel[x][y];
		    }

		// Normalize the kernel
		for (int x = 0; x < W; ++x) 
		    for (int y = 0; y < W; ++y)
		        kernel[x][y] /= sum;
		
		
		for (int x = 0; x < W; ++x) 
		    for (int y = 0; y < W; ++y) 
		    {
		        mask[x][y]=(int) Math.round(kernel[x][y]*255);
		        System.out.println(mask[x][y]);
		    }
		

	}

}
