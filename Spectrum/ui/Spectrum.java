import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame; 

/**
 * The main class for Spectrum. Hopefully, this shouldn't have to be edited all
 * that much.
 *
 * @author Ryan McCubbin, Gianna Casolara, Mohammed Al-Harazi
 *
 */


public class Spectrum
{
	/**
	 * The main method.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args)
	{
		
		try
		{
			ColorTable.loadTable();
		} catch (FileNotFoundException e)
		{
			System.err.println("One or more table files could not be found");
			System.exit(-1);
			
		} catch (IOException e)
		{
			
			System.err.println("Unspecified IO Exception");
			System.err.println("Details: " + e.getMessage());
			System.exit(-1);
			
		}
		
		JFrame frame = new JFrame();

		
		try 
		{
            URL resource = frame.getClass().getResource("/spec2.png");
            BufferedImage image = ImageIO.read(resource);
            frame.setIconImage(image);
        } catch (IOException e) 
		{
            e.printStackTrace();
        }
		
		ContentPanel contentPanel = new ContentPanel();
		
		frame.setSize(1200, 700); //We can mess with this size later if we want
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(contentPanel);
		frame.setVisible(true);

	}

}
