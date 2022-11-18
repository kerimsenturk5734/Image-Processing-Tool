package Filters.ImageFilters;

import java.awt.image.BufferedImage;

import Filters.Filter;
import Filters.IFilterDao;


public class GaussFilter extends Filter implements IFilterDao{

	public GaussFilter(int width, int height) {
		super(width, height);
	}


	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createMask() {
		System.out.println("Gauss Filtresi oluþturuldu");
		//create mask and assign mask to this.mask[][]
		
	}

}
