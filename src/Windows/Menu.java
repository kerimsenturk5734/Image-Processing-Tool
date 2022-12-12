package Windows;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.ExtensionFileFilter;

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
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.border.CompoundBorder;
import java.awt.SystemColor;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.SpinnerModel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Menu {

	private JFrame frame;
	private JPanel panel_images;
	private ButtonGroup rdbGroup;
	private BufferedImage img_in;
	private BufferedImage img_out;
	private int serviceNumber=-1;
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
		panel.setBackground(SystemColor.textInactiveText);
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
			//......................IMAGE-PANEL.....................
			panel_images = new JPanel();
			panel_images.setName("");
			panel_images.setBackground(SystemColor.desktop);
			panel_images.setBorder(new LineBorder(new Color(153, 180, 209), 3, true));
			panel_images.setBounds(10, 25, 1050, 525);
			panel.add(panel_images);
			panel_images.setLayout(null);
			
			
			JLabel lbl_img1 = new JLabel();
			lbl_img1.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
			lbl_img1.setBackground(Color.BLACK);
			lbl_img1.setBounds(29, 25, 474, 474);
			panel_images.add(lbl_img1);
			
			JLabel lbl_img2 = new JLabel();
			lbl_img2.setBorder(new LineBorder(SystemColor.activeCaption, 2));
			lbl_img2.setBackground(Color.BLACK);
			lbl_img2.setBounds(536, 25, 474, 474);
			panel_images.add(lbl_img2);

		
			//......................IMAGE-ACTION-PANEL.....................
			JPanel panel_image_actions = new JPanel();
			panel_image_actions.setBackground(SystemColor.controlShadow);
			panel_image_actions.setBorder(new LineBorder(SystemColor.activeCaption, 3, true));
			panel_image_actions.setBounds(10, 570, 1050, 146);
			panel.add(panel_image_actions);
			panel_image_actions.setLayout(null);
			
			JButton btn_histogram_in = new JButton("Histogram");
			btn_histogram_in.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(img_in!=null)
						createHistogram(img_in, "Orijinal");
					else {
						JOptionPane.showMessageDialog(null,"Please import an image.");
					}
				}
			});
			btn_histogram_in.setBackground(new Color(255, 239, 213));
			btn_histogram_in.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_histogram_in.setBounds(159, 25, 199, 29);
			panel_image_actions.add(btn_histogram_in);
			
			JButton btn_histogram_out = new JButton("Histogram");
			btn_histogram_out.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(img_out!=null)
						createHistogram(img_out, "Ýþlenmiþ");
				}
			});
			btn_histogram_out.setBackground(new Color(255, 239, 213));
			btn_histogram_out.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_histogram_out.setBounds(657, 25, 199, 29);
			panel_image_actions.add(btn_histogram_out);
			
			JButton btn_import = new JButton("Import Image");
			btn_import.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
					
					// Get array of available formats
					String[] suffices = ImageIO.getReaderFileSuffixes();

					// Add a file filter for each one
					for (int i = 0; i < suffices.length; i++) {
					    FileFilter filter = new FileNameExtensionFilter(suffices[i] + " files", suffices[i]);
					    j.addChoosableFileFilter(filter);
					}

					// Show dialog
					j.setFileSelectionMode(JFileChooser.FILES_ONLY);
					j.setAcceptAllFileFilterUsed(false);
					int ret=j.showSaveDialog(null);
					
					
					if(ret==0) {
						
						if(j.getSelectedFile().canRead()) {
							//Then set img_in and label ico
							try {
								img_in=Geo.resizeImage(ImageIO.read(j.getSelectedFile()), 512, 512);
								lbl_img1.setIcon(new ImageIcon(img_in));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"File not readable as image.");
						}						
					}
					
				}
			});
			btn_import.setForeground(Color.WHITE);
			btn_import.setBackground(new Color(255, 0, 0));
			btn_import.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_import.setBounds(159, 64, 199, 29);
			panel_image_actions.add(btn_import);
			
			JButton btn_saveas = new JButton("Save as...");
			btn_saveas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Image img = ((ImageIcon) lbl_img2.getIcon()).getImage();
					BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);

					Graphics2D g2 = bi.createGraphics();
					g2.drawImage(img, 0, 0, null);
					g2.dispose();
					
					JFileChooser j=new JFileChooser();
					int userSelection = j.showSaveDialog(frame);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
					    try {
							ImageIO.write(bi, "jpg", new File(j.getSelectedFile().getAbsolutePath()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			btn_saveas.setBackground(SystemColor.textHighlight);
			btn_saveas.setFont(new Font("Monospaced", Font.BOLD, 15));
			btn_saveas.setBounds(657, 64, 199, 29);
			panel_image_actions.add(btn_saveas);
			
			JButton btn_apply = new JButton("Apply");
			btn_apply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(serviceNumber!=-1)
						service(serviceNumber);
				}
			});
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
				rd_btn_rgbtogray.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(rd_btn_rgbtogray.isSelected())
							serviceNumber=0;
					}
				});
				rdbGroup.add(rd_btn_rgbtogray);
				rd_btn_rgbtogray.setBounds(20, 15, 150, 21);
				panel_rgbtogray.add(rd_btn_rgbtogray);
				
				//.......................BRIGHTNESS.......................
				JPanel panel_setbrightness = new JPanel();
				panel_setbrightness.setLayout(null);
				panel_setbrightness.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_setbrightness.setBounds(8, 93, 314, 60);
				panel_dottedprocess.add(panel_setbrightness);
				
				JSlider slider_brightness = new JSlider();
				slider_brightness.setEnabled(false);
				slider_brightness.setValue(0);
				slider_brightness.setMinimum(-128);
				slider_brightness.setMaximum(127);
				slider_brightness.setBounds(123, 8, 178, 45);
				
				JRadioButton rd_btn_setbrightness = new JRadioButton("Brightness");
				rd_btn_setbrightness.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						slider_brightness.setEnabled(rd_btn_setbrightness.isSelected());
						
						if(rd_btn_setbrightness.isSelected())
							serviceNumber=1;
							
					}
				});
				rd_btn_setbrightness.setBounds(20, 15, 101, 21);
				rdbGroup.add(rd_btn_setbrightness);
				panel_setbrightness.add(rd_btn_setbrightness);
				
				
				
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
				
				JSpinner spn_threshold = new JSpinner(new SpinnerNumberModel(0,0,255,1));
				spn_threshold.setEnabled(false);
				spn_threshold.setBounds(240, 16, 66, 28);
				panel_threshold.add(spn_threshold);
				
				JRadioButton rd_btn_threshold = new JRadioButton("Threshold");
				rd_btn_threshold.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						spn_threshold.setEnabled(rd_btn_threshold.isSelected());
						
						if(rd_btn_threshold.isSelected())
							serviceNumber=2;
					}
				});
				rd_btn_threshold.setBounds(20, 15, 150, 21);
				rdbGroup.add(rd_btn_threshold);
				panel_threshold.add(rd_btn_threshold);
				
				
				
				
				//.......................NEGATIVE.......................
				JPanel panel_negative = new JPanel();
				panel_negative.setLayout(null);
				panel_negative.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_negative.setBounds(8, 239, 314, 55);
				panel_dottedprocess.add(panel_negative);
				
				JRadioButton rd_btn_negative = new JRadioButton("Negative");
				rd_btn_negative.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						if(rd_btn_negative.isSelected())
							serviceNumber=3;
					}
				});
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
				
					JSlider slider_contrast = new JSlider();
					slider_contrast.setEnabled(false);
					slider_contrast.setValue(0);
					slider_contrast.setPaintLabels(true);
					slider_contrast.setMinimum(-128);
					slider_contrast.setMaximum(127);
					slider_contrast.setBounds(120, 8, 178, 45);
					slider_contrast.setLabelTable(position);
					slider_brightness.setPaintLabels(true);
					panel_setcontrast.add(slider_contrast);
					
					JRadioButton rd_btn_setcontrast = new JRadioButton("Contrast");
					rd_btn_setcontrast.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							slider_contrast.setEnabled(rd_btn_setcontrast.isSelected());
							
							if(rd_btn_setcontrast.isSelected())
								serviceNumber=4;
						}
					});
					rdbGroup.add(rd_btn_setcontrast);
					rd_btn_setcontrast.setBounds(20, 15, 104, 21);
					panel_setcontrast.add(rd_btn_setcontrast);
					
					
				
				//......................STRETCH-CONTRAST........................
				JPanel panel_stretchsontrast = new JPanel();
				panel_stretchsontrast.setLayout(null);
				panel_stretchsontrast.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_stretchsontrast.setBounds(8, 93, 314, 75);
				panel_contrastprocess.add(panel_stretchsontrast);
				
					JSpinner spn_lowbound = new JSpinner(new SpinnerNumberModel(0,0,255,1));
					spn_lowbound.setEnabled(false);
					spn_lowbound.setBounds(240, 28, 66, 28);
					panel_stretchsontrast.add(spn_lowbound);
					
					JSpinner spn_upbound = new JSpinner(new SpinnerNumberModel(0,0,255,1));
					spn_upbound.setEnabled(false);
					spn_upbound.setBounds(162, 28, 66, 28);
					panel_stretchsontrast.add(spn_upbound);
					
					JRadioButton rd_btn_stretchsontrast = new JRadioButton("Contrast Stretch");
					rd_btn_stretchsontrast.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							spn_lowbound.setEnabled(rd_btn_stretchsontrast.isSelected());
							spn_upbound.setEnabled(rd_btn_stretchsontrast.isSelected());
							
							if(rd_btn_stretchsontrast.isSelected())
								serviceNumber=5;
						}
					});
					rdbGroup.add(rd_btn_stretchsontrast);
					rd_btn_stretchsontrast.setBounds(20, 28, 142, 21);
					panel_stretchsontrast.add(rd_btn_stretchsontrast);
				
					
					
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
					rd_btn_histogrameq.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							if(rd_btn_histogrameq.isSelected())
								serviceNumber=6;
						}
					});
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
					rd_btn_invertX.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							if(rd_btn_invertX.isSelected())
								serviceNumber=7;
						}
					});
					rdbGroup.add(rd_btn_invertX);
					rd_btn_invertX.setBounds(20, 15, 104, 21);
					panel_invert.add(rd_btn_invertX);
					
					JRadioButton rd_btn_invertY = new JRadioButton("Invert-Y");
					rd_btn_invertY.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							if(rd_btn_invertY.isSelected())
								serviceNumber=8;
						}
					});
					rdbGroup.add(rd_btn_invertY);
					rd_btn_invertY.setBounds(170, 15, 104, 21);
					panel_invert.add(rd_btn_invertY);
			
				//......................OFFSET........................
				JPanel panel_offset = new JPanel();
				panel_offset.setLayout(null);
				panel_offset.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_offset.setBounds(8, 83, 314, 60);
				panel_geometric.add(panel_offset);
				

					JSpinner spn_offsetY = new JSpinner(new SpinnerNumberModel());
					spn_offsetY.setEnabled(false);
					spn_offsetY.setBounds(240, 22, 66, 28);
					panel_offset.add(spn_offsetY);
					
					JSpinner spn_offsetX = new JSpinner(new SpinnerNumberModel());
					spn_offsetX.setEnabled(false);
					spn_offsetX.setBounds(160, 22, 66, 28);
					panel_offset.add(spn_offsetX);
					
					JRadioButton rd_btn_setoffset = new JRadioButton("Offset");
					rd_btn_setoffset.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							spn_offsetX.setEnabled(rd_btn_setoffset.isSelected());
							spn_offsetY.setEnabled(spn_offsetX.isEnabled());
							
							if(rd_btn_setoffset.isSelected())
								serviceNumber=9;
						}
					});
					rdbGroup.add(rd_btn_setoffset);
					rd_btn_setoffset.setBounds(20, 15, 101, 21);
					panel_offset.add(rd_btn_setoffset);
					
					
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
				
					JSpinner spn_angle = new JSpinner(new SpinnerNumberModel());
					spn_angle.setEnabled(false);
					spn_angle.setBounds(240, 16, 66, 28);
					panel_rotate.add(spn_angle);
				
					JRadioButton rd_btn_rotate = new JRadioButton("Rotate");
					rd_btn_rotate.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							spn_angle.setEnabled(rd_btn_rotate.isSelected());
							
							if(rd_btn_rotate.isSelected())
								serviceNumber=10;
						}
					});
					rdbGroup.add(rd_btn_rotate);
					rd_btn_rotate.setBounds(20, 15, 150, 21);
					panel_rotate.add(rd_btn_rotate);
					
					
			
				//......................SCALE........................
				JPanel panel_scale = new JPanel();
				panel_scale.setLayout(null);
				panel_scale.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_scale.setBounds(8, 229, 314, 55);
				panel_geometric.add(panel_scale);
				
					JRadioButton rd_btn_zoomin = new JRadioButton("Zoom In");
					rd_btn_zoomin.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							if(rd_btn_zoomin.isSelected())
								serviceNumber=11;
						}
					});
					rdbGroup.add(rd_btn_zoomin);
					rd_btn_zoomin.setBounds(20, 15, 104, 21);
					panel_scale.add(rd_btn_zoomin);
					
					JRadioButton rd_btn_zoomout = new JRadioButton("Zoom Out");
					rd_btn_zoomout.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							
							if(rd_btn_zoomout.isSelected())
								serviceNumber=12;
						}
					});
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
						
						
						JComboBox<Object> cb_lowflter = new JComboBox<Object>();
						cb_lowflter.setEnabled(false);
						cb_lowflter.setFont(new Font("Monospaced", Font.ITALIC, 15));
						cb_lowflter.setBounds(130, 15, 168, 21);
						cb_lowflter.addItem("GAUSS_FILTER");
						cb_lowflter.addItem("MEAN_FILTER");
						cb_lowflter.addItem("MEDIAN_FILTER");
						panel_lowfilter.add(cb_lowflter);
					
						JRadioButton rd_btn_lowfilter = new JRadioButton("Low Filters");
						rd_btn_lowfilter.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								
								cb_lowflter.setEnabled(rd_btn_lowfilter.isSelected());
								
								if(rd_btn_lowfilter.isSelected()) {
									switch (cb_lowflter.getSelectedIndex()) {
										case 0:
											serviceNumber=13;
										case 1:
											serviceNumber=14;
										case 2:
											serviceNumber=15;
									}
									
								}
									
							}
						});
						rdbGroup.add(rd_btn_lowfilter);
						rd_btn_lowfilter.setBounds(20, 15, 95, 21);
						panel_lowfilter.add(rd_btn_lowfilter);
						
						
						
					//......................HIGH-FILTERS........................
					JPanel panel_highfilter = new JPanel();
					panel_highfilter.setLayout(null);
					panel_highfilter.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_highfilter.setBounds(8, 180, 314, 60);
					panel_filters.add(panel_highfilter);
					
					
						JComboBox<Object> cb_highfilter = new JComboBox<Object>();
						cb_highfilter.setEnabled(false);
						cb_highfilter.setFont(new Font("Monospaced", Font.ITALIC, 15));
						cb_highfilter.setBounds(130, 15, 168, 21);
						cb_highfilter.addItem("LAPLACE_FILTER");
						cb_highfilter.addItem("SOBEL_FILTER");
						cb_highfilter.addItem("PREWITT_FILTER");
						panel_highfilter.add(cb_highfilter);
					
						JRadioButton rd_btn_highfilter = new JRadioButton("High Filters");
						rd_btn_highfilter.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								cb_highfilter.setEnabled(rd_btn_highfilter.isSelected());
								
								if(rd_btn_highfilter.isSelected()) {
									switch (cb_highfilter.getSelectedIndex()) {
										case 0:
											serviceNumber=16;
										case 1:
											serviceNumber=17;
										case 2:
											serviceNumber=18;
									}
									
								}
							}
						});
						rdbGroup.add(rd_btn_highfilter);
						rd_btn_highfilter.setBounds(20, 15, 96, 21);
						panel_highfilter.add(rd_btn_highfilter);
						
						
				
				//......................MORPHOLOGY........................
				JPanel panel_morphology = new JPanel();
				panel_morphology.setBackground(SystemColor.controlShadow);
				tabbedPane2.addTab("Morphology", null, panel_morphology, null);
				panel_morphology.setLayout(null);
				
					//.....................DILATE.........................
					JPanel panel_dilate = new JPanel();
					panel_dilate.setLayout(null);
					panel_dilate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_dilate.setBounds(8, 10, 314, 55);
					panel_morphology.add(panel_dilate);
					
						JRadioButton rd_btn_dilate = new JRadioButton("Dilation");
						rd_btn_dilate.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								if(rd_btn_dilate.isSelected())
									serviceNumber=19;
							}
						});
						rdbGroup.add(rd_btn_dilate);
						rd_btn_dilate.setBounds(20, 15, 150, 21);
						panel_dilate.add(rd_btn_dilate);
					
					//.....................ERODE.........................
					JPanel panel_erode = new JPanel();
					panel_erode.setLayout(null);
					panel_erode.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_erode.setBounds(8, 83, 314, 60);
					panel_morphology.add(panel_erode);
					
						JRadioButton rd_btn_eroding = new JRadioButton("Eroding");
						rd_btn_eroding.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								if(rd_btn_eroding.isSelected())
									serviceNumber=20;
							}
						});
						rdbGroup.add(rd_btn_eroding);
						rd_btn_eroding.setBounds(20, 15, 101, 21);
						panel_erode.add(rd_btn_eroding);
					
					//.....................OPEN.........................
					JPanel panel_open = new JPanel();
					panel_open.setLayout(null);
					panel_open.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_open.setBounds(8, 156, 314, 55);
					panel_morphology.add(panel_open);
					
						JRadioButton rd_btn_opening = new JRadioButton("Open");
						rd_btn_opening.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								if(rd_btn_opening.isSelected())
									serviceNumber=20;
							}
						});
						rdbGroup.add(rd_btn_opening);
						rd_btn_opening.setBounds(20, 15, 150, 21);
						panel_open.add(rd_btn_opening);
					
					//.....................CLOSE.........................
					JPanel panel_close = new JPanel();
					panel_close.setLayout(null);
					panel_close.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_close.setBounds(8, 229, 314, 55);
					panel_morphology.add(panel_close);
					
						JRadioButton rd_btn_closing = new JRadioButton("Close");
						rd_btn_closing.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {
								if(rd_btn_opening.isSelected())
									serviceNumber=21;
							}
						});
						rdbGroup.add(rd_btn_closing);
						rd_btn_closing.setBounds(20, 15, 150, 21);
						panel_close.add(rd_btn_closing);
				
				//......................EXTRAS........................
				JPanel panel_extras = new JPanel();
				panel_extras.setBackground(SystemColor.controlShadow);
				tabbedPane2.addTab("Extras", null, panel_extras, null);
				panel_extras.setLayout(null);
					
					//.....................PERSPECTIVE-TRANSFORM.........................
					JPanel panel_perspectivetransform = new JPanel();
					panel_perspectivetransform.setLayout(null);
					panel_perspectivetransform.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_perspectivetransform.setBounds(8, 10, 314, 219);
					panel_extras.add(panel_perspectivetransform);
						JPanel panel_coordinates = new JPanel();
						panel_coordinates.setEnabled(false);
						panel_coordinates.setBounds(8, 39, 298, 173);
						panel_perspectivetransform.add(panel_coordinates);
						panel_coordinates.setLayout(null);
						
						JRadioButton rd_btn_perspectivetransform = new JRadioButton("Perspective Transform");
						rd_btn_perspectivetransform.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent e) {

								if(rd_btn_perspectivetransform.isEnabled())
									serviceNumber=22;
							}
						});
						rdbGroup.add(rd_btn_perspectivetransform);
						rd_btn_perspectivetransform.setBounds(20, 15, 166, 21);
						panel_perspectivetransform.add(rd_btn_perspectivetransform);
						
						JLabel lblNewLabel = new JLabel("");
						lblNewLabel.setBackground(Color.RED);
						lblNewLabel.setBounds(153, 61, 11, 151);
						panel_perspectivetransform.add(lblNewLabel);
						
						
						
							JLabel lbl_xy1 = new JLabel("x1:                 y1:");
							lbl_xy1.setBackground(Color.YELLOW);
							lbl_xy1.setBounds(15, 0, 99, 13);
							panel_coordinates.add(lbl_xy1);
							
							JSpinner spn_x1 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_x1.setBounds(0, 15, 60, 20);
							panel_coordinates.add(spn_x1);
							
							JSpinner spn_y1 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_y1.setBounds(67, 15, 60, 20);
							panel_coordinates.add(spn_y1);
							
							JLabel lbl_xy2 = new JLabel("x2:                 y2:");
							lbl_xy2.setBounds(15, 45, 99, 13);
							panel_coordinates.add(lbl_xy2);
							
							JSpinner spn_x2 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_x2.setBounds(0, 60, 60, 20);
							panel_coordinates.add(spn_x2);
							
							JSpinner spn_y2 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_y2.setBounds(67, 60, 60, 20);
							panel_coordinates.add(spn_y2);
							
							JLabel lbl_xy3 = new JLabel("x3:                 y3:");
							lbl_xy3.setBounds(15, 90, 99, 13);
							panel_coordinates.add(lbl_xy3);
							
							JSpinner spn_x3 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_x3.setBounds(0, 105, 60, 20);
							panel_coordinates.add(spn_x3);
							
							JSpinner spn_y3 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_y3.setBounds(67, 105, 60, 20);
							panel_coordinates.add(spn_y3);
							
							JLabel lbl_xy4 = new JLabel("x4:                 y4:");
							lbl_xy4.setBounds(15, 134, 99, 13);
							panel_coordinates.add(lbl_xy4);
							
							JSpinner spn_x4 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_x4.setBounds(0, 149, 60, 20);
							panel_coordinates.add(spn_x4);							
							
							JSpinner spn_y4 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_y4.setBounds(67, 149, 60, 20);
							panel_coordinates.add(spn_y4);
							
							JLabel lbl_XY1 = new JLabel("X1':                 Y1':");
							lbl_XY1.setBounds(186, 0, 99, 13);
							panel_coordinates.add(lbl_XY1);
							
							JSpinner spn_X1 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_X1.setBounds(171, 15, 60, 20);
							panel_coordinates.add(spn_X1);
							
							JSpinner spn_Y1 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_Y1.setBounds(238, 15, 60, 20);
							panel_coordinates.add(spn_Y1);
							
							JLabel lbl_XY2 = new JLabel("X2':                Y2':");
							lbl_XY2.setBounds(186, 45, 99, 13);
							panel_coordinates.add(lbl_XY2);
							
							JSpinner spn_X2 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_X2.setBounds(171, 60, 60, 20);
							panel_coordinates.add(spn_X2);
	
							JSpinner spn_Y2 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_Y2.setBounds(238, 60, 60, 20);
							panel_coordinates.add(spn_Y2);
							
							JLabel lbl_XY3 = new JLabel("X3':                 Y3':");
							lbl_XY3.setBounds(186, 90, 99, 13);
							panel_coordinates.add(lbl_XY3);
							
							JSpinner spn_X3 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_X3.setBounds(171, 105, 60, 20);
							panel_coordinates.add(spn_X3);

							JSpinner spn_Y3 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_Y3.setBounds(238, 105, 60, 20);
							panel_coordinates.add(spn_Y3);
							
							JLabel lbl_XY4 = new JLabel("X4':                 X4':");
							lbl_XY4.setBounds(186, 134, 99, 13);
							panel_coordinates.add(lbl_XY4);
							
							JSpinner spn_Y4 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_Y4.setBounds(238, 149, 60, 20);
							panel_coordinates.add(spn_Y4);
							
							JSpinner spn_X4 = new JSpinner(new SpinnerNumberModel(0, 0, 474, 1));
							spn_X4.setBounds(171, 149, 60, 20);
							panel_coordinates.add(spn_X4);
							
						
							
							
					//.....................SHARPENING-BY-CONVOLUTION.........................
					JPanel panel_sharpeningbyconv = new JPanel();
					panel_sharpeningbyconv.setLayout(null);
					panel_sharpeningbyconv.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_sharpeningbyconv.setBounds(8, 239, 314, 55);
					panel_extras.add(panel_sharpeningbyconv);
					
					JRadioButton rd_btn_sharpeningbyconv = new JRadioButton("Sharpening by Convolution");
					rdbGroup.add(rd_btn_sharpeningbyconv);
					rd_btn_sharpeningbyconv.setBounds(20, 15, 210, 21);
					panel_sharpeningbyconv.add(rd_btn_sharpeningbyconv);
					
			
			////////////////////////////////////////
			
		
		
		
		
		//////////RUNTIME///////////////////////
		
		
		////ORIJINAL/////		
		
		
		
		
		//////ÝÞLENMÝÞ///////
		try {
			img_out = ImageIO.read(new File("Images/lenna.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int[][] mask=new int[][] {{1,1,1},{1,-4,1},{0,1,0}};
		FilterManager filter=new FilterManager(new Sharp(Sharp.DEFAULT_KERNEL));
		BufferedImage img=Geo.setOffSet(img_out,40,100);
		
		lbl_img2.setIcon(new ImageIcon(img));
		lbl_img2.setBounds(lbl_img2.getBounds().x, lbl_img2.getBounds().y, img.getWidth(), img.getHeight());

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
	
	public void createHistogram(BufferedImage img,String histName) {
		
		HistogramDataset histogramDataset = new HistogramDataset();
		
		double[] pixelValues=getPixelValues(getGrayValues(img));
		histogramDataset.addSeries(histName, pixelValues, 255, 0.0, 255);
		
		//Title and axis names
		String plotTitle = histName; 
	    String xaxis = "number";
	    String yaxis = "value"; 
	    
	    PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    
	    //Chart preferences
	    boolean show = true; 
	    boolean toolTips = true;
	    boolean urls = true; 
	     
	    //Chart creating with settings
	    JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	        		histogramDataset, orientation, show, toolTips, urls);
		
	    //Chartframe settings
	    ChartFrame frame=new ChartFrame(histName, chart);
	    frame.setBounds(100,100,600,500);
	    frame.setVisible(true);
	    frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				Menu.this.frame.setEnabled(false);
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method 
				Menu.this.frame.setEnabled(true);		
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});;
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
 	
 	
 	public void service(int whichService) {
 		
 			//Create a case every image process
			switch (whichService) {
			
			case 0: {

			}
			case 1: {

			}
			case 2: {

			}
			case 3: {

			}
			case 4: {

			}
			case 5: {

			}
			case 6: {
				
			}
			case 7: {

			}
			case 8: {

			}
			case 9: {

			}
			case 10: {

			}
			case 11: {

			}
			case 12: {

			}
			case 13: {

			}
			case 14: {

			}
			case 15: {

			}
			
			}
 	}
}
