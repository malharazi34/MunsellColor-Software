import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A class for the analysis of pixel colors in an image.
 * 
 * @author Gianna Casolara, Ryan McCubbin
 *
 */
public class ImageAnalysisPanel extends ImagePanel
{
	private static final long serialVersionUID = 1L;
	JPanel eastPanel;
	private BufferedImage bI;
	private Graphics g;
	private ColorPanel output;
	private GridBagConstraints con;

	/**
	 * Main code for analyzing an image.
	 */
	ImageAnalysisPanel()
	{
		super();
		southPanel.add(posterizeButton);
		posterizeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (imagePath == null) return;
//				JFrame frame = new JFrame();
//				frame.setSize(900, 700);
//				
//				
//				frame.getContentPane().setLayout(new FlowLayout());
//				frame.getContentPane().add(new JLabel(new ImageIcon(ImageDriver.resize(
//						posterize(), 1))));
//				
//				frame.pack();
//				frame.setVisible(true);
				centerPanel.setVisible(false);
				bI = posterize();
				ImageIcon icon = new ImageIcon(bI);
				label.setIcon(resizeImage(icon));
				centerPanel.setVisible(true);
				
			}
		});
		output = new ColorPanel(new MunsellColor(0, 0, 0));

		eastPanel = new JPanel();
		eastPanel.setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.weightx = 1;
		con.weighty = 1;
		con.fill = GridBagConstraints.BOTH;
		eastPanel.add(output, con);

		add(eastPanel, BorderLayout.EAST);

		label.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int x = e.getX() - label.getX();
				int y = e.getY() - label.getY();
				Color color;
				try
				{
					color = new Color(bI.getRGB(x, y));
				} catch (ArrayIndexOutOfBoundsException ex) 
				{
					return;
				}

				MunsellColor mColor = ColorTable.rgbToMunsell(color);

				eastPanel.setVisible(false);
				eastPanel.remove(output);
				output = new ColorPanel(mColor);
				eastPanel.add(output, con);
				eastPanel.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{

			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{

			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
			}
		});
	}
	
	
	@Override
	public ImageIcon resizeImage(String imagePath)
	{

		ImageIcon ret = super.resizeImage(imagePath);
		bI = new BufferedImage(ret.getIconWidth(), ret.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		g = bI.createGraphics();
		ret.paintIcon(null, g, 0, 0);
		g.dispose();
		return ret;

	}
	
	/**
	 * Postrerizes an image given a path.
	 * @return The resulting image.
	 */
	public BufferedImage posterize()
	{
		File in = new File(imagePath);
		BufferedImage imagePrepost;
		try 
		{
			imagePrepost = ImageIO.read(in);
		} catch(IOException e)
		{
			System.out.println("File not found");
			return null;
			
		}

		BufferedImage newImage = new BufferedImage(
		    imagePrepost.getWidth(), imagePrepost.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		int[][] pixels;
		pixels = ImageDriver.getPixels(imagePrepost);
		ArrayList<MunsellColor> palette = PalettePanel.getPalette();
		if (palette == null) 
		{
			return null;
		}
		int[] paletteColors = new int[palette.size()];
		
		for (int i = 0; i < palette.size(); i++)
		{
			Color temp = ColorTable.munsellToRGB(palette.get(i));
			paletteColors[i] = temp.getRGB();
		}
		double minDist = 500.0;
		// pci - palette color index - index of the closest palette color
		int pci = -1;
		for (int i = 0; i < imagePrepost.getHeight(); i++)
		{
			for (int j = 0; j < imagePrepost.getWidth(); j++)
			{
				for (int p = 0; p < paletteColors.length; p++)
				{
					if (ImageDriver.getDistance(pixels[i][j], paletteColors[p]) < minDist)
					{
						minDist = ImageDriver.getDistance(pixels[i][j], paletteColors[p]);
						pci = p;
					}
				}
				newImage.setRGB(j,  i,  paletteColors[pci]);
				minDist = 500;
			}
		}
		return newImage;
	}
	
	/**
	 * Method used to resize image before loading it in.
	 * 
	 * @param image The image to resize
	 * @return The resized ImageIcon
	 */
	public ImageIcon resizeImage(ImageIcon image)
	{

		ImageIcon myImage = image;
		Image img = myImage.getImage();
		
		
		int imageHeight = img.getHeight(null);
		int imageWidth = img.getWidth(null);
		int labelWidth = label.getWidth();
		int labelHeight = label.getHeight();
		
		int actualWidth = label.getWidth();
		int actualHeight = label.getHeight();
		
		double imageRatio = ((double) imageWidth) / imageHeight;
		
		int scaledHeight = (int) Math.round(labelWidth / imageRatio);
		int scaledWidth = (int) Math.round(imageRatio * labelHeight);
		
		if (scaledHeight > labelHeight) 
		{
			
			actualWidth = scaledWidth;
			actualHeight = (int)Math.round(scaledWidth / imageRatio);
			
		} else if (scaledWidth > labelWidth) 
		{
			
			actualHeight = scaledHeight;
			actualWidth = (int)Math.round(actualHeight * imageRatio);
			
		}
		
		Image newImg = img.getScaledInstance(actualWidth, actualHeight,
				Image.SCALE_SMOOTH);
		ImageIcon image1 = new ImageIcon(newImg);
		return image1;
	}
	
	
	

}