package Windows;
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import java.awt.SystemColor;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.SpinnerModel;
import javax.swing.JSlider;
import javax.swing.JComboBox;

public class Menu {

	private JFrame frame;
	private JPanel panel_images;
	private ButtonGroup rdbGroup;

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
		
		rdbGroup=new ButtonGroup();
		
		//......................MAIN-PANEL.....................
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlShadow);
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
			//......................IMAGE-PANEL.....................
			panel_images = new JPanel();
			panel_images.setName("");
			panel_images.setBackground(SystemColor.controlDkShadow);
			panel_images.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_images.setBounds(10, 25, 1050, 525);
			panel.add(panel_images);
			panel_images.setLayout(null);
			
			
			JLabel lbl_img1 = new JLabel();
			lbl_img1.setBackground(Color.BLACK);
			lbl_img1.setBounds(29, 6, 512, 512);
			panel_images.add(lbl_img1);
			
			JLabel lbl_img2 = new JLabel();
			lbl_img2.setBackground(Color.BLACK);
			lbl_img2.setBounds(536, 6, 512, 512);
			panel_images.add(lbl_img2);

		
			//......................IMAGE-ACTION-PANEL.....................
			JPanel panel_image_actions = new JPanel();
			panel_image_actions.setBackground(SystemColor.controlDkShadow);
			panel_image_actions.setBorder(new CompoundBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255)), new LineBorder(new Color(0, 0, 0))), null));
			panel_image_actions.setBounds(10, 570, 1050, 146);
			panel.add(panel_image_actions);
			panel_image_actions.setLayout(null);
			
			JButton btn_histogram_in = new JButton("Histogram");
			btn_histogram_in.setBackground(new Color(255, 239, 213));
			btn_histogram_in.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_histogram_in.setBounds(159, 25, 199, 29);
			panel_image_actions.add(btn_histogram_in);
			
			JButton btn_histogram_out = new JButton("Histogram");
			btn_histogram_out.setBackground(new Color(255, 239, 213));
			btn_histogram_out.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_histogram_out.setBounds(657, 25, 199, 29);
			panel_image_actions.add(btn_histogram_out);
			
			JButton btn_import = new JButton("Import Image");
			btn_import.setForeground(Color.WHITE);
			btn_import.setBackground(new Color(255, 0, 0));
			btn_import.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_import.setBounds(159, 64, 199, 29);
			panel_image_actions.add(btn_import);
			
			JButton btn_saveas = new JButton("Save as...");
			btn_saveas.setBackground(SystemColor.textHighlight);
			btn_saveas.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_saveas.setBounds(657, 64, 199, 29);
			panel_image_actions.add(btn_saveas);
			
			JButton btn_apply = new JButton("Apply");
			btn_apply.setForeground(Color.WHITE);
			btn_apply.setBackground(Color.GREEN);
			btn_apply.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_apply.setBounds(429, 49, 158, 29);
			panel_image_actions.add(btn_apply);
			
			JButton btn_clear = new JButton("Clear All");
			btn_clear.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_clear.setBackground(Color.WHITE);
			btn_clear.setBounds(429, 86, 158, 29);
			panel_image_actions.add(btn_clear);
			
			JPanel panel_image_processes = new JPanel();
			panel_image_processes.setBackground(SystemColor.controlDkShadow);
			panel_image_processes.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			panel_image_processes.setBounds(1084, 25, 355, 691);
			panel.add(panel_image_processes);
			panel_image_processes.setLayout(null);
			
			//......................PANE-1........................
			JTabbedPane tabbedPane1 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane1.setBackground(Color.WHITE);
			tabbedPane1.setBounds(10, 0, 335, 331);
			panel_image_processes.add(tabbedPane1);
			
			//..................DOTTED PROCESS...................
			JPanel panel_dottedprocess = new JPanel();
			panel_dottedprocess.setBackground(SystemColor.controlShadow);
			tabbedPane1.addTab("Dotted", null, panel_dottedprocess, null);
			panel_dottedprocess.setLayout(null);
			
				JPanel panel_rgbtogray = new JPanel();
				panel_rgbtogray.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_rgbtogray.setBounds(8, 20, 314, 55);
				panel_dottedprocess.add(panel_rgbtogray);
				panel_rgbtogray.setLayout(null);
				
				JRadioButton rd_btn_rgbtogray = new JRadioButton("RGB to Gray");
				rdbGroup.add(rd_btn_rgbtogray);
				rd_btn_rgbtogray.setBounds(20, 15, 150, 21);
				panel_rgbtogray.add(rd_btn_rgbtogray);
				
				//.......................BRIGHTNESS.......................
				JPanel panel_setbrightness = new JPanel();
				panel_setbrightness.setLayout(null);
				panel_setbrightness.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_setbrightness.setBounds(8, 93, 314, 60);
				panel_dottedprocess.add(panel_setbrightness);
				
				JRadioButton rd_btn_setbrightness = new JRadioButton("Brightness");
				rd_btn_setbrightness.setBounds(20, 15, 101, 21);
				rdbGroup.add(rd_btn_setbrightness);
				panel_setbrightness.add(rd_btn_setbrightness);
				
				JSlider slider_brightness = new JSlider();
				slider_brightness.setValue(0);
				slider_brightness.setMinimum(-128);
				slider_brightness.setMaximum(127);
				slider_brightness.setBounds(123, 8, 178, 45);
				
				Hashtable<Integer,JLabel> position = new Hashtable();
				position.put(-128, new JLabel("-128"));
				position.put(-64, new JLabel("-64"));
				position.put(0, new JLabel("0"));
				position.put(64, new JLabel("64"));
				position.put(127, new JLabel("127"));
				slider_brightness.setPaintLabels(true);
				slider_brightness.setLabelTable(position);
				
				panel_setbrightness.add(slider_brightness);
				
				//.......................THRESHOLD.......................
				JPanel panel_threshold = new JPanel();
				panel_threshold.setLayout(null);
				panel_threshold.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_threshold.setBounds(8, 166, 314, 55);
				panel_dottedprocess.add(panel_threshold);
				
				JRadioButton rd_btn_threshold = new JRadioButton("Threshold");
				rd_btn_threshold.setBounds(20, 15, 150, 21);
				rdbGroup.add(rd_btn_threshold);
				panel_threshold.add(rd_btn_threshold);
				
				JSpinner spn_threshold = new JSpinner(new SpinnerNumberModel(0,0,255,1));
				spn_threshold.setBounds(240, 16, 66, 28);
				panel_threshold.add(spn_threshold);
				
				
				//.......................NEGATIVE.......................
				JPanel panel_negative = new JPanel();
				panel_negative.setLayout(null);
				panel_negative.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_negative.setBounds(8, 239, 314, 55);
				panel_dottedprocess.add(panel_negative);
				
				JRadioButton rd_btn_negative = new JRadioButton("Negative");
				rd_btn_negative.setBounds(20, 15, 150, 21);
				rdbGroup.add(rd_btn_negative);
				panel_negative.add(rd_btn_negative);
			
			//.......................CONTRAST-PROCESS.......................
			JPanel panel_contrastprocess = new JPanel();
			panel_contrastprocess.setBackground(SystemColor.controlShadow);
			tabbedPane1.addTab("Contrast", null, panel_contrastprocess, null);
			panel_contrastprocess.setLayout(null);
			
			    //......................SET-CONTRAST........................
				JPanel panel_setcontrast = new JPanel();
				panel_setcontrast.setLayout(null);
				panel_setcontrast.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_setcontrast.setBounds(8, 20, 314, 60);
				panel_contrastprocess.add(panel_setcontrast);
				
					JRadioButton rd_btn_setcontrast = new JRadioButton("Contrast");
					rdbGroup.add(rd_btn_setcontrast);
					rd_btn_setcontrast.setBounds(20, 15, 104, 21);
					panel_setcontrast.add(rd_btn_setcontrast);
					
					JSlider slider_contrast = new JSlider();
					slider_contrast.setValue(0);
					slider_contrast.setPaintLabels(true);
					slider_contrast.setMinimum(-128);
					slider_contrast.setMaximum(127);
					slider_contrast.setBounds(120, 8, 178, 45);
					slider_contrast.setLabelTable(position);
					slider_brightness.setPaintLabels(true);
					panel_setcontrast.add(slider_contrast);
				
				//......................STRETCH-CONTRAST........................
				JPanel panel_stretchsontrast = new JPanel();
				panel_stretchsontrast.setLayout(null);
				panel_stretchsontrast.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_stretchsontrast.setBounds(8, 93, 314, 75);
				panel_contrastprocess.add(panel_stretchsontrast);
				
					JRadioButton rd_btn_stretchsontrast = new JRadioButton("Contrast Stretch");
					rdbGroup.add(rd_btn_stretchsontrast);
					rd_btn_stretchsontrast.setBounds(20, 28, 142, 21);
					panel_stretchsontrast.add(rd_btn_stretchsontrast);
				
					JSpinner spn_lowbound = new JSpinner(new SpinnerNumberModel(0,0,255,1));
					spn_lowbound.setBounds(240, 28, 66, 28);
					panel_stretchsontrast.add(spn_lowbound);
					
					JSpinner spn_upbound = new JSpinner(new SpinnerNumberModel(0,0,255,1));
					spn_upbound.setBounds(162, 28, 66, 28);
					panel_stretchsontrast.add(spn_upbound);
					
					JLabel lbl_lowup = new JLabel("Low Bound   Up Bound");
					lbl_lowup.setFont(new Font("Monospaced", Font.PLAIN, 11));
					lbl_lowup.setBounds(166, 10, 140, 13);
					panel_stretchsontrast.add(lbl_lowup);
				
				//......................HISTOGRAM-EGUALIZATION........................
				JPanel panel_histogrameq = new JPanel();
				panel_histogrameq.setLayout(null);
				panel_histogrameq.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_histogrameq.setBounds(8, 185, 314, 55);
				panel_contrastprocess.add(panel_histogrameq);
				
					JRadioButton rd_btn_histogrameq = new JRadioButton("Histogram Equalization");
					rdbGroup.add(rd_btn_histogrameq);
					rd_btn_histogrameq.setBounds(20, 15, 234, 21);
					panel_histogrameq.add(rd_btn_histogrameq);
			
			//.......................GEOMETRIC.......................
			JPanel panel_geometric = new JPanel();
			panel_geometric.setBackground(SystemColor.controlShadow);
			tabbedPane1.addTab("Geometric", null, panel_geometric, null);
			panel_geometric.setLayout(null);
			
				JPanel panel_invert = new JPanel();
				panel_invert.setLayout(null);
				panel_invert.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_invert.setBounds(8, 10, 314, 55);
				panel_geometric.add(panel_invert);
				
					JRadioButton rd_btn_invertX = new JRadioButton("Invert-X");
					rdbGroup.add(rd_btn_invertX);
					rd_btn_invertX.setBounds(20, 15, 104, 21);
					panel_invert.add(rd_btn_invertX);
					
					JRadioButton rd_btn_invertY = new JRadioButton("Invert-Y");
					rdbGroup.add(rd_btn_invertY);
					rd_btn_invertY.setBounds(170, 15, 104, 21);
					panel_invert.add(rd_btn_invertY);
			
				//......................OFFSET........................
				JPanel panel_offset = new JPanel();
				panel_offset.setLayout(null);
				panel_offset.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_offset.setBounds(8, 83, 314, 60);
				panel_geometric.add(panel_offset);
				
					JRadioButton rd_btn_setoffset = new JRadioButton("Offset");
					rdbGroup.add(rd_btn_setoffset);
					rd_btn_setoffset.setBounds(20, 15, 101, 21);
					panel_offset.add(rd_btn_setoffset);
					
					JSpinner spn_offsetY = new JSpinner(new SpinnerNumberModel());
					spn_offsetY.setBounds(240, 22, 66, 28);
					panel_offset.add(spn_offsetY);
					
					JSpinner spn_offsetX = new JSpinner(new SpinnerNumberModel());
					spn_offsetX.setBounds(160, 22, 66, 28);
					panel_offset.add(spn_offsetX);
					
					JLabel lbl_offset = new JLabel("X          Y");
					lbl_offset.setFont(new Font("Monospaced", Font.PLAIN, 11));
					lbl_offset.setBounds(175, 4, 101, 13);
					panel_offset.add(lbl_offset);
					
				//......................ROTATE........................    
				JPanel panel_rotate= new JPanel();
				panel_rotate.setLayout(null);
				panel_rotate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_rotate.setBounds(8, 156, 314, 55);
				panel_geometric.add(panel_rotate);
				
					JRadioButton rd_btn_rotate = new JRadioButton("Rotate");
					rdbGroup.add(rd_btn_rotate);
					rd_btn_rotate.setBounds(20, 15, 150, 21);
					panel_rotate.add(rd_btn_rotate);
					
					JSpinner spn_angle = new JSpinner(new SpinnerNumberModel());
					spn_angle.setBounds(240, 16, 66, 28);
					panel_rotate.add(spn_angle);
			
				//......................SCALE........................
				JPanel panel_scale = new JPanel();
				panel_scale.setLayout(null);
				panel_scale.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_scale.setBounds(8, 229, 314, 55);
				panel_geometric.add(panel_scale);
				
					JRadioButton rd_btn_zoomin = new JRadioButton("Zoom In");
					rdbGroup.add(rd_btn_zoomin);
					rd_btn_zoomin.setBounds(20, 15, 104, 21);
					panel_scale.add(rd_btn_zoomin);
					
					JRadioButton rd_btn_zoomout = new JRadioButton("Zoom Out");
					rdbGroup.add(rd_btn_zoomout);
					rd_btn_zoomout.setBounds(181, 15, 115, 21);
					panel_scale.add(rd_btn_zoomout);
			
					
			//......................PANE-2........................
			JTabbedPane tabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane2.setBackground(Color.WHITE);
			tabbedPane2.setBounds(10, 350, 335, 331);
			panel_image_processes.add(tabbedPane2);
				
				//......................FILTERS........................
				JPanel panel_filters = new JPanel();
				panel_filters.setBackground(SystemColor.controlShadow);
				tabbedPane2.addTab("Filters", null, panel_filters, null);
				panel_filters.setLayout(null);
				
					//......................LOW-FILTERS........................
					JPanel panel_lowfilter = new JPanel();
					panel_lowfilter.setLayout(null);
					panel_lowfilter.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_lowfilter.setBounds(8, 60, 314, 60);
					panel_filters.add(panel_lowfilter);
					
						JRadioButton rd_btn_lowfilter = new JRadioButton("Low Filters");
						rdbGroup.add(rd_btn_lowfilter);
						rd_btn_lowfilter.setBounds(20, 15, 95, 21);
						panel_lowfilter.add(rd_btn_lowfilter);
						
						JComboBox<Object> cb_lowflter = new JComboBox<Object>();
						cb_lowflter.setFont(new Font("Monospaced", Font.ITALIC, 15));
						cb_lowflter.setBounds(130, 15, 168, 21);
						cb_lowflter.addItem("GAUSS_FILTER");
						cb_lowflter.addItem("MEAN_FILTER");
						cb_lowflter.addItem("MEDIAN_FILTER");
						panel_lowfilter.add(cb_lowflter);
						
					//......................HIGH-FILTERS........................
					JPanel panel_highfilter = new JPanel();
					panel_highfilter.setLayout(null);
					panel_highfilter.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_highfilter.setBounds(8, 180, 314, 60);
					panel_filters.add(panel_highfilter);
					
						JRadioButton rd_btn_highfilter = new JRadioButton("High Filters");
						rdbGroup.add(rd_btn_highfilter);
						rd_btn_highfilter.setBounds(20, 15, 96, 21);
						panel_highfilter.add(rd_btn_highfilter);
						
						JComboBox<Object> cb_highfilter = new JComboBox<Object>();
						cb_highfilter.setFont(new Font("Monospaced", Font.ITALIC, 15));
						cb_highfilter.setBounds(130, 15, 168, 21);
						cb_highfilter.addItem("LAPLACE_FILTER");
						cb_highfilter.addItem("SOBEL_FILTER");
						cb_highfilter.addItem("PREWITT_FILTER");
						panel_highfilter.add(cb_highfilter);
				
				//......................MORPHOLOGY........................
				JPanel panel_morphology = new JPanel();
				panel_morphology.setBackground(SystemColor.controlShadow);
				tabbedPane2.addTab("Morphology", null, panel_morphology, null);
				panel_morphology.setLayout(null);
				
				JPanel panel_dilate = new JPanel();
				panel_dilate.setLayout(null);
				panel_dilate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_dilate.setBounds(8, 10, 314, 55);
				panel_morphology.add(panel_dilate);
				
				JRadioButton rd_btn_dilate = new JRadioButton("Dilation");
				rdbGroup.add(rd_btn_dilate);
				rd_btn_dilate.setBounds(20, 15, 150, 21);
				panel_dilate.add(rd_btn_dilate);
				
				JPanel panel_erode = new JPanel();
				panel_erode.setLayout(null);
				panel_erode.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_erode.setBounds(8, 83, 314, 60);
				panel_morphology.add(panel_erode);
				
				JRadioButton rd_btn_eroding = new JRadioButton("Eroding");
				rdbGroup.add(rd_btn_eroding);
				rd_btn_eroding.setBounds(20, 15, 101, 21);
				panel_erode.add(rd_btn_eroding);
				
				JPanel panel_open = new JPanel();
				panel_open.setLayout(null);
				panel_open.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_open.setBounds(8, 156, 314, 55);
				panel_morphology.add(panel_open);
				
				JRadioButton rd_btn_opening = new JRadioButton("Open");
				rdbGroup.add(rd_btn_opening);
				rd_btn_opening.setBounds(20, 15, 150, 21);
				panel_open.add(rd_btn_opening);
				
				JPanel panel_close = new JPanel();
				panel_close.setLayout(null);
				panel_close.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_close.setBounds(8, 229, 314, 55);
				panel_morphology.add(panel_close);
				
				JRadioButton rd_btn_closing = new JRadioButton("Close");
				rdbGroup.add(rd_btn_closing);
				rd_btn_closing.setBounds(20, 15, 150, 21);
				panel_close.add(rd_btn_closing);
				
				//......................EXTRAS........................
				JPanel panel_extras = new JPanel();
				panel_extras.setBackground(SystemColor.controlShadow);
				tabbedPane2.addTab("Extras", null, panel_extras, null);
				panel_extras.setLayout(null);
				
			
			////////////////////////////////////////
			
		
		
		
		
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
