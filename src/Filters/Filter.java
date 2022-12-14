package Filters;


import java.awt.Color;
import java.awt.image.BufferedImage;


public class Filter implements IFilterDao{
	
	protected int[][] mask;
	
	public Filter(int format) {
	
		mask=new int[format][format];
		
		createMask();
	}
	
	public Filter(int format,int maskVal) {

		this(format);
		
		createMask(maskVal);
	}
	
	public Filter(int[][] mask) {
		createMask(mask);
		
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
		
		for(int i=0;i<this.mask.length;i++) {
			for(int j=0;j<this.mask[0].length;j++) {
				mask[i][j]=maskVal;
			}
		}
	}
	
	protected void createMask(int[][] mask) {
		//fill maskVal in the mask 
		this.mask=new int[mask.length][mask[0].length];
		
		for(int i=0;i<mask.length;i++) {
			for(int j=0;j<mask[0].length;j++) {
				this.mask[i][j]=mask[i][j];
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

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		
		
		int W= getFormat();
		
		BufferedImage imgFiltered=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < img.getWidth()-W; x++) {
			
			for (int y = 0; y < img.getHeight()-W; y++) {
				
				int sumR = 0,sumG=0,sumB=0;
				
				for (int i = 0; i< W; i++) {
					
					for (int j = 0; j < W; j++) {
						
						System.out.println(W);
						Color c=new Color(img.getRGB(x+i, y+j));
						
						//Getting all R,G,B values summary in the mask area...
						sumR+=mask[i][j]*c.getRed();
						sumG+=mask[i][j]*c.getGreen();
						sumB+=mask[i][j]*c.getBlue();
						
					}
				}
				
				//Getting average value...
				int R=sumR/(W*W);
				int G=sumG/(W*W);
				int B=sumB/(W*W);
				
				Color newCol=new Color(R,G,B);
				
				imgFiltered.setRGB(x+(W/2), y+(W/2), newCol.getRGB());
				
			}
		}
		
		return imgFiltered;
	}
	
	public static int[][] getGrayValues(BufferedImage img){
		
		int[][] arr=new int[img.getWidth()][img.getHeight()];
		
		for(int i=0;i<img.getWidth();i++){ 
			
			for(int j=0;j<img.getHeight();j++) {
				
				
				int p = img.getRGB(i, j); 
				  
                int r = (p >> 16) & 0xff; 
                int g = (p >> 8) & 0xff; 
                int b = p & 0xff; 
  
                arr[i][j]=(int)((r*0.299)+(g*0.587)+(b*0.114));
			}
		}
		
		return arr;
	}
	
	public static int toRGB(int value) {
		
		//You can use RGB(gray,gray,gray) instead of that
		
		return value * 0x00010101;
	}
}
