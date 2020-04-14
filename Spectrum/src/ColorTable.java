import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class representing the lookup tables for going from Munsell to RGB colors.
 *
 * @author Ryan McCubbin
 *
 */
public class ColorTable
{

	private static HashMap<String, Color> mToRGB = new HashMap<>();
	private static HashMap<Color, MunsellColor> rgbToM = new HashMap<>();

	// The number that RGB values in the rgbToM table are rounded to
	// private static double ROUND = 17;

	/**
	 * A method for use in rounding RGB colors to the table. Tells whether a
	 * specific RGB color has a mapping to Munsell in the table
	 *
	 * @param c The color to determine if there is a mapping of
	 * @return Whether a mapping of c exists
	 */
	private static boolean isInRGBToMunsell(Color c)
	{

		if (c == null)
		{
			return false;
		}
		MunsellColor m = rgbToM.get(c);

		return m != null;

	}

	/**
	 * Loads the .csv files into a HashMap, to be called in main.
	 *
	 * @throws FileNotFoundException if either of the tables don't exist
	 * @throws IOException           unspecified IO exception
	 */
	public static void loadTable() throws FileNotFoundException, IOException
	{

		// The indexes of the values we care about in the lines of the mToRGB table
		final int M_TO_RGB_KEY_INDEX = 0;
		final int M_TO_RGB_RED_INDEX = 6;
		final int M_TO_RGB_GREEN_INDEX = 7;
		final int M_TO_RGB_BLUE_INDEX = 8;

		// The indexes of the values we care about in the lines of the rgbToM table
		final int RGB_TO_M_RED_INDEX = 0;
		final int RGB_TO_M_GREEN_INDEX = 1;
		final int RGB_TO_M_BLUE_INDEX = 2;
		final int RGB_TO_M_HUE_INDEX = 3;
		final int RGB_TO_M_VALUE_INDEX = 4;
		final int RGB_TO_M_CHROMA_INDEX = 5;

		String munsell2RGBStr = "Munsell2RGB.csv";
		String rgb2MunsellStr = "RGB2Munsell.csv";

		// Create BufferedReaders so we can read tables line by line
		BufferedReader munsellToRGB = new BufferedReader(new FileReader(munsell2RGBStr));
		BufferedReader rgbToMunsell = new BufferedReader(new FileReader(rgb2MunsellStr));

		// The first line of each table is a header that should be skipped
		munsellToRGB.readLine();
		rgbToMunsell.readLine();

		// Parse MunsellToRGB table
		String line = "";
		while ((line = munsellToRGB.readLine()) != null)
		{

			String[] data = line.split(","); // Split line into individual strings

			String key = data[M_TO_RGB_KEY_INDEX]; // The key in the HashMap

			int red = Integer.parseInt(data[M_TO_RGB_RED_INDEX]);
			int green = Integer.parseInt(data[M_TO_RGB_GREEN_INDEX]);
			int blue = Integer.parseInt(data[M_TO_RGB_BLUE_INDEX]);

			Color rgb = new Color(red, green, blue); // The value in the HashMap

			mToRGB.put(key, rgb);

		}
		
		line = "";

		// Parse RGBToMunsell table
		while ((line = rgbToMunsell.readLine()) != null)
		{

			String[] data = line.split(",");

			int red = Integer.parseInt(data[RGB_TO_M_RED_INDEX]);
			int green = Integer.parseInt(data[RGB_TO_M_GREEN_INDEX]);
			int blue = Integer.parseInt(data[RGB_TO_M_BLUE_INDEX]);

			Hue hueNum = new Hue(data[RGB_TO_M_HUE_INDEX]);
			double chroma = Double.parseDouble(data[RGB_TO_M_CHROMA_INDEX]);
			double value = Double.parseDouble(data[RGB_TO_M_VALUE_INDEX]);

			Color rgb = new Color(red, green, blue); // The key in the HashMap
			MunsellColor munsell = new MunsellColor(hueNum, chroma, value); // The value

			rgbToM.put(rgb, munsell);

		}

		munsellToRGB.close();
		rgbToMunsell.close();

	}

	/**
	 * Gets the RGB representation of a MunsellColor.
	 *
	 * @param c The Munsell color to get the RGB color for
	 * @return The RGB representation
	 */
	public static Color munsellToRGB(MunsellColor c)
	{

		// Since some chroma don't exist in the table, this will modify a particular
		// MunsellColor to give an RGB color that is on the table.
		String key = c.getKey();
		Color ret = null;
		boolean hasColor = mToRGB.containsKey(key);
		
		if (hasColor)
		{

			ret = mToRGB.get(c.getKey());
		} else if (c.getChroma() > 2)
		{

			ret = munsellToRGB(new MunsellColor(c.getHue(), c.getChroma() - 2, c.getValue()));
		} else
		{
			
			ret = munsellToRGB(new MunsellColor("10.0RP", 0, c.getValue()));
			
		}

		return ret;

	}

	/**
	 * Gets the MunsellColor representation of an RGB color.
	 *
	 * @param c The RGB color to get the Munsell color for
	 * @return The Munsell Color that this RGB color represents
	 */

	public static MunsellColor rgbToMunsell(Color c)
	{

		MunsellColor ret = null;

		// Check for a couple easy colors, like black and white

		if (c.equals(Color.black))
		{

			ret = new MunsellColor("10RP", 0, 0);

		} else if (c.equals(Color.white))
		{

			ret = new MunsellColor("10RP", 0, 10);

		} else
		{

			int numToRoundTo = 17;

			int red = (int) (numToRoundTo * Math.round(c.getRed() / ((double) numToRoundTo)));
			int blue = (int) (numToRoundTo * Math.round(c.getBlue() / ((double) numToRoundTo)));
			int green = (int) (numToRoundTo * Math.round(c.getGreen() / ((double) numToRoundTo)));
			Color retKey = roundColorToTable(new Color(red, green, blue), numToRoundTo);

			ret = rgbToM.get(retKey);

		}
		return ret;

	}

	/**
	 * A recursive helper method to round RGB colors to the nearest color on the
	 * table. It seems that only high values of greens or blues are cut off in the
	 * table, so this will figure out the best way to round blues and greens down to
	 * the required level to be found in the table.
	 *
	 * @param c            The color to round
	 * @param numToRoundTo The number to subtract each color component by
	 * @return The rounded color
	 */
	private static Color roundColorToTable(Color c, int numToRoundTo)
	{

		Color ret = null;

		if (isInRGBToMunsell(c))
		{

			ret = c;
		} else
		{

			// Hold c's red, green, and blue components
			int red = c.getRed();
			int green = c.getGreen();
			int blue = c.getBlue();

			// Hold whether red, green, and blue are able to be subtracted from
			boolean validRed = red <= 255 - numToRoundTo;
			boolean validGreen = green <= 255 - numToRoundTo;
			boolean validBlue = blue >= numToRoundTo;

			// The possible closest colors to this one
			Color redMinus = validRed ? new Color(red + numToRoundTo, green, blue) : null;
			Color greenMinus = validGreen ? new Color(red, green + numToRoundTo, blue) : null;
			Color blueMinus = validBlue ? new Color(red, green, blue - numToRoundTo) : null;
			Color redGreenMinus = validRed && validGreen ? new Color(red + numToRoundTo, 
					green + numToRoundTo, blue) : null;
			Color redBlueMinus = validRed && validBlue ? new Color(red + numToRoundTo, green, 
					blue - numToRoundTo) : null;
			Color greenBlueMinus = validGreen && validBlue ? new Color(red, green + numToRoundTo, 
					blue - numToRoundTo) : null;

			// Run through each possible closest color. If one is in the table, then use
			// that color.
			// If none are in the table, call this method on this color, with each component
			// rounded
			// down one step.
			if (isInRGBToMunsell(redMinus))
			{
				ret = redMinus;
			} else if (isInRGBToMunsell(greenMinus))
			{
				ret = greenMinus;
			} else if (isInRGBToMunsell(blueMinus))
			{
				ret = blueMinus;
			} else if (isInRGBToMunsell(redGreenMinus))
			{
				ret = redGreenMinus;
			} else if (isInRGBToMunsell(redBlueMinus))
			{
				ret = redBlueMinus;
			} else if (isInRGBToMunsell(greenBlueMinus))
			{
				ret = greenBlueMinus;
			} else
			{

				int newRed = red;
				int newBlue = blue;
				int newGreen = green;
				if (validRed)
				{
					red += numToRoundTo;
				}
				if (validGreen)
				{
					green += numToRoundTo;
				}

				ret = roundColorToTable(new Color(newRed, newBlue, newGreen), numToRoundTo);
			}

		}

		return ret;

	}

}
