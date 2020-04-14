import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * NOT FINISHED Class for modifying an image.
 *
 * @author Abdulshaid Alibekov
 *
 */
public class ImageDriver
{

	/**
	 * Calculates the distance between two colors represented as ints.
	 *
	 * @param a - color number 1.
	 * @param b - color number 2.
	 * @return the distance between the two colors.
	 */
	public static double getDistance(int a, int b)
	{
		// get color components from color a
		double b1 = a & 0x00ff;
		double g1 = a & 0x0000ff;
		double r1 = a & 0x000000ff;
		// get color components from color b
		double b2 = b & 0x00ff;
		double g2 = b & 0x0000ff;
		double r2 = b & 0x000000ff;

		double distance = Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2) + Math.pow(r1 - r2, 2);
		distance = Math.sqrt(distance);
		return distance;

	}

	/**
	 * Gets a 2D pixel array from a buffered image.
	 *
	 * @param image The image to get the pixels from
	 * @return A 2D array where each entry is the color of the pixel in that
	 *         location in the image
	 */
	public static int[][] getPixels(BufferedImage image)
	{
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		int[][] result = new int[height][width];
		if (hasAlphaChannel)
		{
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength)
			{
				int argb = 0;
				argb += ((pixels[pixel] & 0xff) << 24); // alpha
				argb += (pixels[pixel + 1] & 0xff); // blue
				argb += ((pixels[pixel + 2] & 0xff) << 8); // green
				argb += ((pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width)
				{
					col = 0;
					row++;
				}

			}
		} else
		{
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength)
			{
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += (pixels[pixel] & 0xff); // blue
				argb += ((pixels[pixel + 1] & 0xff) << 8); // green
				argb += ((pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width)
				{
					col = 0;
					row++;
				}
			}
		}
		return result;
	}


}
