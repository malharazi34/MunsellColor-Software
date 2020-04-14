import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The Image Panel for the main JFrame of the GUI. Allows the user
 * to load in an image.
 * 
 * @author Gianna Casolara
 *
 */

public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected JButton button;
	protected JButton posterizeButton;
	protected JButton saveButton;
	protected JLabel label;
	protected JPanel southPanel;
	protected JPanel centerPanel;
	protected ImageIcon image;
	protected String imagePath;
	
	/**
	 * Image Panel Constructor.
	 */
	public ImagePanel()
	{

		this.setLayout(new BorderLayout());

		southPanel = new JPanel();
		button = new JButton("Browse");
		southPanel.add(button);
		posterizeButton = new JButton("Posterize");
		saveButton = new JButton("Save");
		southPanel.add(saveButton);
		
		
		centerPanel = new JPanel();
		label = new JLabel();
		centerPanel.add(label);

		add(southPanel, BorderLayout.SOUTH);
		add(label, BorderLayout.CENTER);
		

		button.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{

				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));

				// filter the files to only files that are images
				FileNameExtensionFilter filter = 
						new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);

				// if the user clicks on save while choosing a file
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = file.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					imagePath = path;
					label.setHorizontalAlignment(SwingConstants.LEFT);
					image = resizeImage(path);
					label.setIcon(image);
				}
				// if the user clicks on cancel while choosing a file

				else if (result == JFileChooser.CANCEL_OPTION)
				{
					System.out.println("No File Select");
				}
			}
		});
		
		saveButton.addActionListener(e -> {
			
			String name = JOptionPane.showInputDialog("Save Image As: ");
			
			BufferedImage bufImage = new BufferedImage(image.getIconWidth(), 
					image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			System.out.println(image.getIconWidth());
			System.out.println(image.getIconHeight());
			
			Graphics2D big = bufImage.createGraphics();
			big.drawImage(image.getImage(), 0, 0, null);
			big.dispose();
			
			try
			{
				ImageIO.write(bufImage, "png", new File(name + ".png"));
			} catch (IOException e1)
			{
				JOptionPane.showMessageDialog(this, "Could not save image");
			}
			
		});

		// setLayout(null);
		setVisible(true);
		
		
	}

	/**
	 * Method used to resize image before loading it in.
	 * 
	 * @param path string
	 * @return image
	 */
	public ImageIcon resizeImage(String path)
	{

		ImageIcon myImage = new ImageIcon(path);
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
	
	/**
	 * Gets the image icon loaded on this image panel.
	 * @return The image icon on this image panel
	 */
	public ImageIcon getImageIcon() 
	{
		return image;
		
	}
	
	
}
