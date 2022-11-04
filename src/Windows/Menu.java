package Windows;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.JLabel;

public class Menu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
		
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1128, 661);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(592, 34, 512, 512);
		panel.add(lblNewLabel);
		
		BufferedImage imgBufferedImage=null;
		try {
			imgBufferedImage = ImageIO.read(new File("Images/lennadark.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//////ÝÞLENMÝÞ///////
		BufferedImage img=setBrightness(imgBufferedImage,100);
		createHistogram(getPixelValues(getGrayValues(img)),"Ýþlenmiþ");

		lblNewLabel.setIcon(new ImageIcon(img));
		
		BufferedImage imgOriginal=null;
		try {
			imgOriginal = ImageIO.read(new File("Images/lennadark.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		////ORIJINAL/////
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(29, 34, 512, 512);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_1.setIcon(new ImageIcon(imgOriginal));
		createHistogram(getPixelValues(getGrayValues(imgOriginal)),"Orijinal");
		//System.out.println(imgBufferedImage.getRGB(20, 20));
	}
	
	
	public BufferedImage imgToGray(BufferedImage img){
		
		int[][] arr=new int[img.getWidth()][img.getHeight()];
		
		for(int i=0;i<img.getWidth();i++){ 
			
			for(int j=0;j<img.getHeight();j++) {
				
				Color c=new Color(img.getRGB(i, j));
				
				int p = img.getRGB(i, j); 
				  
                int a = (p >> 24) & 0xff; 
                int r = (p >> 16) & 0xff; 
                int g = (p >> 8) & 0xff; 
                int b = p & 0xff; 
  
                // calculate average 
                int avg = (r + g + b) / 3; 
  
                // replace RGB value with avg 
                p = (a << 24) | (avg << 16) | (avg << 8) | avg; 
  
                img.setRGB(i, j, p);
			}
		}
		
		return img;
	}
	
	public BufferedImage thresholdImage(BufferedImage img) {
		
		double[][] arr=getGrayValues(img);
		
		for(int i=0;i<img.getWidth();i++) {
			
			for(int j=0;j<img.getHeight();j++) {
				
				if(arr[i][j]>128) {
	               	img.setRGB(i,j,Color.WHITE.getRGB());
	             }
	            else {
	               	img.setRGB(i,j,Color.BLACK.getRGB());
	             }	
			}
		}
		
		return img;
	}
	
	
	public double[][] getGrayValues(BufferedImage img){
		
		double[][] arr=new double[img.getWidth()][img.getHeight()];
		
		for(int i=0;i<img.getWidth();i++){ 
			
			for(int j=0;j<img.getHeight();j++) {
				
				Color c=new Color(img.getRGB(i, j));
				
				int p = img.getRGB(i, j); 
				  
                int r = (p >> 16) & 0xff; 
                int g = (p >> 8) & 0xff; 
                int b = p & 0xff; 
  
                arr[i][j]=(float)((r*0.299)+(g*0.587)+(b*0.114));
			}
		}
		
		return arr;
	}
	
	
	public int toRGB(int value) {
		
		//You can use RGB(gray,gray,gray) instead of that
		
		return value * 0x00010101;
	}
	
	
	public BufferedImage stretchContrast(BufferedImage img) {
		//Get gray values of image
		double[][] arr=getGrayValues(img);
		
		//Get min and max pixel value
		int minGray=0;
		int maxGray=0;
		
		for(int i=0; i<img.getWidth(); i++) {
			
			for(int j=0; j<img.getHeight(); j++) {
				
				if(arr[i][j]>maxGray)
					maxGray=(int) arr[i][j];
				
				if(arr[i][j]<minGray)
					minGray=(int) arr[i][j];
			}
			
		}
		
		//Calculate stretched values
		
		//g(x)=255*(pixel-minGray)/(maxGray-minGray)
		
		for(int i=0; i<img.getWidth(); i++) {
			
			for(int j=0; j<img.getHeight(); j++) {
				
				int stretchedValue=255*((int)arr[i][j]-minGray)/Math.abs(maxGray-minGray);
				
				//Convert stretched gray value to RGB
				img.setRGB(i,j,toRGB(stretchedValue));
			}
			
		}
		
		return img;
	}
	
	
	public void createHistogram(double[] pixelValues,String histName) {
		
		HistogramDataset histogramDataset = new HistogramDataset();
	
		histogramDataset.addSeries("H1", pixelValues, 255, 0.0, 255);
		
		//Title and axis names
		String plotTitle = histName; 
	    String xaxis = "number";
	    String yaxis = "value"; 
	    
	    PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    
	    //Chart preferences
	    boolean show = false; 
	    boolean toolTips = false;
	    boolean urls = false; 
	     
	    //Chart creating with settings
	    JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	        		histogramDataset, orientation, show, toolTips, urls);
		
	    //Chartframe settings
	    ChartFrame frame=new ChartFrame(histName, chart);
	    frame.setSize(1920, 1080);
	    frame.setVisible(true);
	}

	
	//Returning double[] for histogram chart
 	public double[] getPixelValues(double[][] imgGray) {
		
 		//Getting height and width of img
 		int width=imgGray[0].length;
 		int height=imgGray[1].length;
 		
		double[] pixelValues=new double[width*height];
		
		//Traveling all pixels
		int index=0;
		for(int i=0;i<width;i++) {
			
			for(int j=0;j<height;j++) {
				
				pixelValues[index]=imgGray[i][j];
				index++;
			}
		}
		
		return pixelValues;
	}

 	public BufferedImage imgToNegative(BufferedImage img) {
 		//Get gray image
 		BufferedImage grayImage=imgToGray(img);
 		
 		//Iterate all pixels
 		for(int i=0;i<grayImage.getWidth();i++ ) {
 			
 			for(int j=0;j<grayImage.getHeight();j++) {
 				
 				//Create new color newColor=new Color(255-curColor.getred,255-curColor.getgreen,255-curColor.getblue)
 				Color curColor=new Color(grayImage.getRGB(i, j));
 				Color newColor=new Color(255-curColor.getRed(),255-curColor.getGreen(),255-curColor.getBlue());
 				
 		 		//Set the newColor to currentPixel
 				grayImage.setRGB(i, j, newColor.getRGB());
 			}
 		}
 		
 		return grayImage;
 	}
 	
 	public BufferedImage setBrightness(BufferedImage img,int amountOfBrightness) {
 		for(int i=0;i<img.getWidth();i++) {
 			
 			for(int j=0;j<img.getHeight();j++) {
 				int currPixelValue=img.getRGB(i, j);
 				
 				img.setRGB(i, j, currPixelValue+toRGB(amountOfBrightness));
 				
 			}
 		}
 		
 		return img;
 	}
}
