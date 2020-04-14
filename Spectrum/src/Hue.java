
/**
 * A class representing a Munsell Hue.
 *
 * @author Ryan McCubbin
 *
 */
public class Hue
{
	
	private double hue;

	private String suffix;

	

	

	/**
	 * Constructor for creating a new hue.
	 *
	 * @param h The hue to make this object
	 */
	public Hue(double h)
	{
		if (h >= 0)
		{
			this.hue = h % 100;
		} else
		{
			this.hue = 100 - ((-h) % 100); // Figure out how to get this to work with negatives
		}

		suffix = getSuffix(hue);
	}

	/**
	 * Creates the hue from a String instead of a double. For use when creating the
	 * tables.
	 *
	 * @param h The hue string that represents this hue
	 */
	public Hue(String h)
	{

		double prefix = 0;
		String strSuffix = null;
		try
		{
			prefix = Double.parseDouble(h.substring(0, h.length() - 1));
		} catch (NumberFormatException e)
		{

			try
			{
				prefix = Double.parseDouble(h.substring(0, h.length() - 2));
			} catch (NumberFormatException f)
			{
				System.err.println("Hue received String with invalid format");
				System.exit(-1);
			}
			strSuffix = h.substring(h.length() - 2);

		}

		if (strSuffix == null)
		{
			strSuffix = h.substring(h.length() - 1);
		}

		suffix = strSuffix;

		hue = getHueNumber(prefix, suffix);
		// TODO: Figure out how to turn this into the actual float later.

	}
	
	/**
	 * Used in the String constructor. Gets the double representation of a hue given
	 * the prefix and suffix.
	 *
	 * @param prefix The double prefix
	 * @param suffix The string suffix
	 * @return The float number for this hue
	 */
	public static double getHueNumber(double prefix, String suffix)
	{

		double modifier = 0;
		switch (suffix)
		{
			case "R":
				modifier = 0;
				break;
			case "YR":
				modifier = 10;
				break;
			case "Y":
				modifier = 20;
				break;
			case "GY":
				modifier = 30;
				break;
			case "G":
				modifier = 40;
				break;
			case "BG":
				modifier = 50;
				break;
			case "B":
				modifier = 60;
				break;
			case "PB":
				modifier = 70;
				break;
			case "P":
				modifier = 80;
				break;
			case "RP":
				modifier = 90;
				break;
			default:
				System.err.println("Invalid hue suffix in Hue");
				System.exit(-1);
		}

		double ret = prefix + modifier;
		return ret == 100 ? 0 : ret;

	}

	/**
	 * Returns this hue as a double.
	 *
	 * @return The double representation of this hue
	 */
	public double getHue()
	{

		return hue;

	}

	/**
	 * Get a radians representation of this hue, for use in mixing colors.
	 *
	 * @return A radian representation of this hue
	 */
	public double getRadians()
	{

		return (hue * Math.PI) / 50;

	}

	/**
	 * Gets the letter(s) in the suffix for a particular hue.
	 *
	 * @param h The hue number to get the suffix for
	 * @return The suffix for h, null on invalid input
	 */
	private String getSuffix(double h)
	{

		String ret = "";
		int key = (int) (h / 10);

		if (h % 10 == 0)
		{
			key = (key + 9) % 10;
		}
		switch (key)
		{
			case 0:
				ret = "R";
				break;
			case 1:
				ret = "YR";
				break;
			case 2:
				ret = "Y";
				break;
			case 3:
				ret = "GY";
				break;
			case 4:
				ret = "G";
				break;
			case 5:
				ret = "BG";
				break;
			case 6:
				ret = "B";
				break;
			case 7:
				ret = "PB";
				break;
			case 8:
				ret = "P";
				break;
			case 9:
				ret = "RP";
				break;
			default:
				break;
		}

		return ret;

	}

	/**
	 * Gives the String representation of this hue.
	 *
	 * @return The String representation of this hue
	 */
	@Override
	public String toString()
	{

		double num = hue % 10;

		// Technically the interval for the prefix is (0,10] and not [0, 10), so this
		// should take
		// care of that.
		if (num == 0)
		{
			num = 10;
		}

		String prefix = String.format("%.1f", num);

		return prefix + suffix;

	}

}
