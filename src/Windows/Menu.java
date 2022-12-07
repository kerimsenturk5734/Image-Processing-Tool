package Windows;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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

import Filters.Filter;
import Filters.FilterManager;
import Filters.IFilterDao;
import Filters.IFilterService;
import Filters.ImageFilters.GaussFilter;
import Filters.ImageFilters.HighFilter;
import Filters.ImageFilters.LaplaceFilter;
import Filters.ImageFilters.MedianFilter;
import Filters.ImageFilters.Sharp;
import Geometric.Geo;
import Morphology.Morp;

import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class Menu {

	private JFrame frame;
	private JPanel panel_images;

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
		frame.setBounds(0, 0, 1463, 779);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/////////PANELS///////////////////////
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panel_images = new JPanel();
		panel_images.setName("asda");
		panel_images.setBackground(Color.ORANGE);
		panel_images.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_images.setBounds(10, 25, 1050, 525);
		panel.add(panel_images);
		panel_images.setLayout(null);
		
		JPanel panel_image_actions = new JPanel();
		panel_image_actions.setBackground(Color.YELLOW);
		panel_image_actions.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_image_actions.setBounds(10, 570, 1050, 146);
		panel.add(panel_image_actions);
		panel_image_actions.setLayout(null);
		
		JPanel panel_image_processes = new JPanel();
		panel_image_processes.setBackground(Color.GREEN);
		panel_image_processes.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		panel_image_processes.setBounds(1084, 25, 355, 691);
		panel.add(panel_image_processes);
		panel_image_processes.setLayout(null);
			
		
		////////////////////////////////////////
		
		/////////LABELS////////////////////////
		JLabel lbl_img1 = new JLabel();
		lbl_img1.setBackground(Color.BLACK);
		lbl_img1.setBounds(14, 6, 512, 512);
		panel_images.add(lbl_img1);
		
		JLabel lbl_img2 = new JLabel();
		lbl_img2.setBackground(Color.BLACK);
		lbl_img2.setBounds(536, 6, 512, 512);
		panel_images.add(lbl_img2);
		/////////////////////////////////////////
		
		
		
		//////////RUNTIME///////////////////////
		BufferedImage imgBufferedImage=null;
		try {
			imgBufferedImage = ImageIO.read(new File("Images/lenna.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		////ORIJINAL/////		
		BufferedImage imgOriginal=null;
		try {
			imgOriginal = ImageIO.read(new File("Images/lenna.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createHistogram(getPixelValues(getGrayValues(imgOriginal)),"Orijinal");
		lbl_img1.setIcon(new ImageIcon(imgOriginal));
		
		
		//////ÝÞLENMÝÞ///////
		int[][] mask=new int[][] {{1,1,1},{1,-4,1},{0,1,0}};
		FilterManager filter=new FilterManager(new Sharp(Sharp.DEFAULT_KERNEL));
		BufferedImage img=filter.applyFilter(imgBufferedImage);
		
		createHistogram(getPixelValues(getGrayValues(img)),"Ýþlenmiþ");
		lbl_img2.setIcon(new ImageIcon(img));

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
	
	
	public BufferedImage stretchContrast(BufferedImage img,int contrastBound) {
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
		
		//g(x)=contrastBound*(pixel-minGray)/(maxGray-minGray)
		
		for(int i=0; i<img.getWidth(); i++) {
			
			for(int j=0; j<img.getHeight(); j++) {
				
				int stretchedValue=contrastBound*((int)arr[i][j]-minGray)/Math.abs(maxGray-minGray);
				
				//Convert stretched gray value to RGB
				img.setRGB(i,j,toRGB(stretchedValue));
			}
			
		}
		
		return img;
	}
	
	public BufferedImage histogramEq(BufferedImage src,float eqFactor) {
		
		BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
		
		WritableRaster wr = src.getRaster();
		WritableRaster er = nImg.getRaster();
		
		int totpix= wr.getWidth()*wr.getHeight();
		int[] histogram = new int[256];
		
		for (int x = 0; x < wr.getWidth(); x++) {
			for (int y = 0; y < wr.getHeight(); y++) {
			   histogram[wr.getSample(x, y, 0)]++;
			}
		}
		
		int[] chistogram = new int[256];
		chistogram[0] = histogram[0];
		
		for(int i=1;i<256;i++){
			chistogram[i] = chistogram[i-1] + histogram[i];
		}
		
		float[] arr = new float[256];
		
		for(int i=0;i<256;i++){
			arr[i] =  (float)((chistogram[i]*eqFactor)/(float)totpix);
		}
		
		for (int x = 0; x < wr.getWidth(); x++) {
			for (int y = 0; y < wr.getHeight(); y++) {
			   int nVal = (int) arr[wr.getSample(x, y, 0)];
			   er.setSample(x, y, 0, nVal);
			}
		}
		
		nImg.setData(er);
		
		return nImg;
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
	    frame.setSize(600, 500);
	    frame.setVisible(true);
	}

	
	//Returning double[] for histogram chart
 	public double[] getPixelValues(double[][] imgGray) {
		
 		//Getting height and width of img
 		int width=imgGray[0].length-1;
 		int height=imgGray[1].length;
 		
		double[] pixelValues=new double[width*height];
		
		//Traveling all pixels
		int index=0;
		for(int i=0;i<width;i++) {
			
			for(int j=0;j<height;j++) {
				
				try {
					pixelValues[index]=imgGray[i][j];
					index++;
				} catch (Exception e) {
					new IndexOutOfBoundsException("out of bound image");
					index++;
				}
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
