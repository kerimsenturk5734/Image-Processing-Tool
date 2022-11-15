package Filters;

public class Filter {
	
	private int[][] mask;
	
	public Filter(int width,int height) {
	
		mask=new int[width][height];
	}

	public int getWidth() {
		return mask[0].length;
	}

	public int getHeight() {
		return mask[1].length;
	}

	public int[][] getMask() {
		return mask;
	}
	
	protected void setMask(int[][] mask) {
		this.mask=mask;
	}
	
}
