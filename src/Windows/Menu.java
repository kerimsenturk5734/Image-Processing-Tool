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

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1128, 661);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(249, 50, 512, 512);
		panel.add(lblNewLabel);
		
		
		BufferedImage imgBufferedImage=null;
		try {
			imgBufferedImage = ImageIO.read(new File("Images/lenna.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		lblNewLabel.setIcon(new ImageIcon(thresholdImage(imgBufferedImage)));
		System.out.println(imgBufferedImage.getRGB(20, 20));
	}
	
	
	public BufferedImage imgToGray(BufferedImage img){
		
		int[][] arr=new int[img.getWidth()][img.getHeight()];
		
		for(int i=0;i<img.getHeight();i++){ 
			
			for(int j=0;j<img.getWidth();j++) {
				
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
		
		float[][] arr=getGrayValues(img);
		
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
	
	
	public float[][] getGrayValues(BufferedImage img){
		
		float[][] arr=new float[img.getWidth()][img.getHeight()];
		
		for(int i=0;i<img.getHeight();i++){ 
			
			for(int j=0;j<img.getWidth();j++) {
				
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
	
	
	public BufferedImage setContrast() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
