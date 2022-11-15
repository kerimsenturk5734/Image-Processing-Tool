package Filters;

import java.awt.image.BufferedImage;

public class FilterManager implements IFilterService {
	
	IFilterDao filterDao;
	
	public FilterManager(IFilterDao filterDao) {
		this.filterDao=filterDao;
	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		return filterDao.applyFilter(img);
	}

}
