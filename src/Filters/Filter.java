package Filters;

public class Filter {
	
	protected int[][] mask;
	
	public Filter(int width,int height) {
	
		if(width!=height)
			throw new IllegalArgumentException("Mask must be square matrix form.");
		
		mask=new int[width][height];
		
		createMask();
	}
	
	public Filter(int width,int height,int maskVal) {

		this(width,height);
		
		createMask(maskVal);
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
	
	protected void createMask() {
		//fill ones in the mask as default
		
		for(int i=0;i<mask.length;i++) {
			for(int j=0;j<mask[0].length;j++) {
				mask[i][j]=1;
			}
		}
	}
	
	protected void createMask(int maskVal) {
		//fill maskVal in the mask 
		
		for(int i=0;i<mask.length;i++) {
			for(int j=0;j<mask[0].length;j++) {
				mask[i][j]=maskVal;
			}
		}
	}
}
