
/**
 * A class representing the harmonious colors of a certain color.
 *
 * @author Mohammed Al-Harazi
 *
 */

public class HarmoniousColors
{
	private double chroma;

	private double hue;

	private double value;

	/**
	 * Constructor that takes in a MunsellColor and converts it to its RGB values
	 * @param munsell Harmonious colors will be calculated according to this munsell
	 *                color
	 */
	public HarmoniousColors(MunsellColor munsell)
	{
		this.hue = munsell.getHue().getHue();
		this.chroma = munsell.getChroma();
		this.value = munsell.getValue();

	}

	/**
	 * @param distance The distance between this color and the analogous colors
	 * @return analogous MunsellColor array containing the provide munsellColor's
	 *         analogous colors.
	 */
	public MunsellColor[] getAnalogousColors(double distance)
	{

		MunsellColor[] analogous = new MunsellColor[2];
		double h = this.getHue();

		analogous[0] = new MunsellColor(((this.getHue() + distance) % 100), this.getChroma(), 
				this.getValue());

		if ((h - distance) < 0)
		{
			analogous[1] = new MunsellColor((100 + (h - distance)), this.getChroma(), 
					this.getValue());
		} else
		{
			analogous[1] = new MunsellColor((this.getHue() - distance), this.getChroma(), 
					this.getValue());
		}

		return analogous;
	}

	/**
	 * Gets the chroma value for the provided Munsell Color.
	 *
	 * @return chroma double representing the chroma of the Munsell Color
	 */
	public double getChroma()
	{

		return chroma;
	}

	/**
	 * Calculates the complementary color of the Munsell Color
	 * @return comp MunsellColor object of the complementary color
	 */
	public MunsellColor getComplementary()
	{
		MunsellColor comp = new MunsellColor(((this.getHue() + 50) % 100), this.getChroma(), 
				this.getValue());

		return comp;

	}

	/**
	 * Gets the hue value for the provided Munsell Color.
	 *
	 * @return hue double representing the hue of the Munsell Color
	 */
	public double getHue()
	{
		return hue;
	}

	/**
	 * Computes a matrix of colors with the same hue as this color.
	 *
	 * @return 9*11 matrix of MunsellColor containing colors with the same hue.
	 * @author Abdulshaid Alibekov
	 */
	public MunsellColor[][] getSameHueColors()
	{
		MunsellColor[][] result = new MunsellColor[9][11];
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 11; j++)
			{
				result[8 - i][j] = new MunsellColor(hue, j * 2, i + 1);
				if (j > 0 && (ColorTable.munsellToRGB(result[8 - i][j]).equals(
						ColorTable.munsellToRGB(result[8 - i][j - 1])) 
						|| result[8 - i][j - 1].getValue() == 10))
				{
					result[8 - i][j] = new MunsellColor(1, 1, 10);
				}
			}
		}
		return result;
	}

	/**
	 * Take in a Munsell Color object and return its two split complementary colors.
	 *
	 * @param distance The distance between
	 * @return split MunsellColor array containing the provided MunsellColor' split
	 *         complementary colors
	 */

	public MunsellColor[] getSplitComp(double distance)
	{
		MunsellColor[] split = new MunsellColor[2];

		HarmoniousColors h = new HarmoniousColors(this.getComplementary());

		split[0] = h.getAnalogousColors(distance)[0];
		split[1] = h.getAnalogousColors(distance)[1];

		return split;
	}

	/**
	 * Computes a Munsell color's 4 square colors and returns them in an array.
	 *
	 * @return square Munsell Color array containing the square colors
	 */
	public MunsellColor[] getSquareColors()
	{
		MunsellColor[] square = new MunsellColor[4];

		square[0] = new MunsellColor(this.getHue(), this.getChroma(), this.getValue());
		square[1] = new MunsellColor(((this.getHue() + 25) % 100), this.getChroma(), 
				this.getValue());
		square[2] = new MunsellColor(((this.getHue() + 50) % 100), this.getChroma(), 
				this.getValue());
		square[3] = new MunsellColor(((this.getHue() + 75) % 100), this.getChroma(), 
				this.getValue());

		return square;

	}

	/**
	 * Computes the tetrad colors from the given munsell Colors and returns an array
	 * of the 4 colors.
	 *
	 * @param c the color to get the tetrad colors of
	 * @return tetra array of 4 tetrad M
	 */
	public MunsellColor[] getTetradColors(MunsellColor c)
	{
		MunsellColor[] tetra = new MunsellColor[4];
		HarmoniousColors h = new HarmoniousColors(c);

		tetra[0] = new MunsellColor(this.getHue(), this.getChroma(), this.getValue());
		tetra[1] = this.getComplementary();

		tetra[2] = c;
		tetra[3] = h.getComplementary();

		return tetra;

	}

	/**
	 * Takes in a Munsell Color object and returns its three triad colors in a
	 * MunsellColor array.
	 *
	 * @return traid a MunsellColor array containing the three triad colors
	 */
	public MunsellColor[] getTriadColors()
	{
		MunsellColor[] triad = new MunsellColor[3];

		triad[0] = new MunsellColor(this.getHue(), this.getChroma(), this.getValue());

		triad[1] = new MunsellColor(((this.getHue() + 33.3) % 100), this.getChroma(), 
				this.getValue());

		triad[2] = new MunsellColor(((this.getHue() + 66) % 100), this.getChroma(), 
				this.getValue());

		return triad;
	}

	/**
	 * Gets the value for the provided Munsell Color.
	 *
	 * @return value double representing the value of the Munsell Color
	 *
	 */
	public double getValue()
	{
		return value;
	}

}
