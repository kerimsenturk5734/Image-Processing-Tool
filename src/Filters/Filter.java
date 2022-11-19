package Filters;

public class Filter {
	
	protected int[][] mask;
	
	public Filter(int format) {
	
		mask=new int[format][format];
		
		createMask();
	}
	
	public Filter(int format,int maskVal) {

		this(format);
		
		createMask(maskVal);
	}

	public int getFormat() {
		return mask[0].length;
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
	
	public int sumOfMaskCoEfficent() {
		int sum=0;
		for(int i=0;i<mask.length;i++) {
			for(int j=0;j<mask[0].length;j++) {
				sum+=mask[i][j];
			}
		}
		
		return sum;
	}
}
