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

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		
		int W=getFormat();
		
		int sumR=0;
		int sumG=0;
		int sumB=0;
		
		for(int x=0;x<img.getWidth()-W;x++) {

			for(int y=0;y<img.getHeight()-W;y++) {
				
				for(int i=0;i<W;i++) {

					for(int j=0;j<W;j++) {
						
						
						Color sampledColor=new Color(img.getRGB(x+i, y+j));
						
						sumR+=mask[i][j]*sampledColor.getRed();
						sumG+=mask[i][j]*sampledColor.getGreen();
						sumB+=mask[i][j]*sampledColor.getBlue();
						
					}
				}
				
				int sumOfCoEff=sumOfMaskCoEfficent();
				try {
					img.setRGB(x+W/2, y+W/2,new Color(sumR/sumOfCoEff,sumG/sumOfCoEff,sumB/sumOfCoEff).getRGB());
				} catch (Exception e) {
					System.out.println("out of bound");
				}
				
				sumR=0;
				sumG=0;
				sumB=0;
			}
		}
		
		return img;
	}
}
