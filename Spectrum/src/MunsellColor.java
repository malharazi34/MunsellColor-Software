
/**
 * A class representing a single color in the Munsell Color Space.
 *
 * @author Ryan McCubbin
 *
 */
public class MunsellColor
{

	private double chroma;
	private Hue hue;

	private boolean isBlack;
	private boolean isNeutral;

	private boolean isWhite;

	private double value;

	/**
	 * Explicit Value Constructor.
	 *
	 * @param hue    A hue value for this color
	 * @param chroma The chroma of this color
	 * @param value  The value of this color
	 */
	public MunsellColor(double hue, double chroma, double value)
	{

		this.value = value;
		isWhite = value == 10;
		isBlack = value == 0;
		isNeutral = isBlack || isWhite || chroma == 0;

		this.hue = isNeutral ? new Hue(0) : new Hue(hue);
		this.chroma = isNeutral ? 0 : chroma;

	}

	/**
	 * Explicit Value Constructor.
	 *
	 * @param hue    A Hue object for this color
	 * @param chroma The chroma of this color
	 * @param value  The value of this color
	 */
	public MunsellColor(Hue hue, double chroma, double value)
	{

		this.value = value;
		isWhite = value == 10;
		isBlack = value == 0;
		isNeutral = isBlack || isWhite || chroma == 0;

		this.hue = isNeutral ? new Hue(0) : hue;
		this.chroma = isNeutral ? 0 : chroma;

	}

	/**
	 * Constructor making use of the String constructor in hue.
	 *
	 * @param hue    The hue's String representation
	 * @param chroma The color's chroma
	 * @param value  The color's value
	 */
	public MunsellColor(String hue, double chroma, double value)
	{

		this.value = value;
		isWhite = value == 10;
		isBlack = value == 0;
		isNeutral = isBlack || isWhite || chroma == 0;

		this.hue = isNeutral ? new Hue(0) : new Hue(hue);
		this.chroma = isNeutral ? 0 : chroma;

	}

	@Override
	public boolean equals(Object other)
	{

		return ((MunsellColor) other).toString().equals(this.toString());

	}
	
	/**
	 * Tells whether the other color is equal to this one within the tolerances defined by the three
	 * doubles.
	 * @param other The other color to test this against
	 * @param hueTol The tolerance in hue
	 * @param valueTol The tolerance in value
	 * @param chromaTol The tolerance in chroma
	 * @return Whether the other color is within tolerance of this color
	 */
	public boolean equals(MunsellColor other, double hueTol, double valueTol, double chromaTol)
	{
		
		boolean hueEqual = this.getHue().getHue() >= other.getHue().getHue() - hueTol 
				&& this.getHue().getHue() <= other.getHue().getHue() + hueTol;
		boolean chromaEqual = this.getChroma() >= other.getChroma() - chromaTol && this.getChroma()
				<= other.getChroma() + chromaTol;
		boolean valueEqual = this.getValue() >= other.getValue() - valueTol && this.getValue() 
				<= other.getValue() + valueTol;
		
		return hueEqual && chromaEqual && valueEqual;
		
	}

	/**
	 * This color's chroma.
	 *
	 * @return The chroma
	 */
	public double getChroma()
	{

		return chroma;

	}

	/**
	 * This color's hue.
	 *
	 * @return The hue
	 */
	public Hue getHue()
	{

		return hue;

	}

	/**
	 * Gives the Key for the closest Munsell color to this MunsellColor object in
	 * the Munsell to RGB color table.
	 *
	 * @return The key for the Munsell to RGB color table
	 */
	public String getKey()
	{

		int keyValue = (int) Math.round(value);

		// The chroma, rounded to the nearest even number
		int keyChroma = (int) (2 * Math.round(chroma / 2));

		// The hue, rounded to the nearest 2.5
		double keyHue = 2.5 * Math.round(hue.getHue() / 2.5);
		Hue keyHueObject = keyChroma == 0 ? new Hue(0) : new Hue(keyHue);

		return keyHueObject.toString() + "-" + keyValue + "-" + keyChroma;

	}

	/**
	 * This color's value.
	 *
	 * @return The value
	 */
	public double getValue()
	{

		return value;

	}
	
	/**
	 * Mixes colors 1 and 2 according to weights 1 and 2. This algoritm defines the
	 * mix of two colors of equal weights as the Cartesian midpoint of the two
	 * colors on the Munsell 3D color object. Adding in varying weights then weights
	 * that point along the line connecting the two points where the ratio of the
	 * distances from the resulting color to the input colors is the reciprocal of
	 * the ratio of the weights. Ex) If color 1 has a weight of 2, and color 2 has a
	 * weight of 3, then the resulting color is the point on the line such that the
	 * ratio of the distance between the resulting color and color 1 to the distance
	 * between the resulting color and color 2 is the weight of color 2 divided by
	 * the weight of color 1.
	 *
	 * @param m1 The first color to mix
	 * @param m2 The second color to mix
	 * @param w1 The weight of the first color
	 * @param w2 The weight of the second color
	 * @return The resulting mixture of the two colors
	 */
	public static MunsellColor mixColors(MunsellColor m1, MunsellColor m2, double w1, double w2)
	{

		double c1 = w1 * m1.getChroma();
		double c2 = w2 * m2.getChroma();
		double h1 = m1.getHue().getRadians();
		double h2 = m2.getHue().getRadians();
		double v1 = w1 * m1.getValue();
		double v2 = w2 * m2.getValue();

		double tempChroma = (c1 * c1) + (c2 * c2) + (2 * c1 * c2 * Math.cos(h1 - h2));
		double tempHueNum = (c1 * Math.sin(h1) + c2 * Math.sin(h2));
		double tempHueDenom = (c1 * Math.cos(h1) + c2 * Math.cos(h2));
		double tempHue = tempHueNum / tempHueDenom;

		double arcTanTemp = Math.atan(tempHue);

		// For future use:
		// arctan only returns a value between -pi/2 and pi/2, but we need to return a
		// hue between
		// -pi/2 and 3pi/2 (0 and 2pi, just shifted some). To fix this, if the
		// denominator in arctan
		// is negative, we need to shift the -pi/2 to pi/2 spectrum to one between pi/2
		// and 3pi/2,
		// so we just need to add pi the result of the arctan.

		if (tempHueDenom < 0)
		{
			arcTanTemp += Math.PI;
		}

		double newChroma = Math.sqrt(tempChroma) / (w1 + w2);

		double newHue = arcTanTemp * 50 / Math.PI;
		double newValue = (v1 + v2) / (w1 + w2);

		return new MunsellColor(newHue, newChroma, newValue);

	}
	
	/**
	 * Calculates the linear distance between two colors by mapping them onto a cylindrical
	 * coordinate space with hue as the theta coordinate, chroma as the r, and value as the z. The
	 * formula for the distance between two colors in cylindrical space is given by 
	 * d^2 = r1^2 + r2^2 - 2 * r1 * r2 * cos(t1 - t2) + (z2 - z1)^2, where d is the distance and the
	 * t's are the angles.
	 * @param m1 The first MunsellColor
	 * @param m2 The second MunsellColor
	 * @return The distance between the two
	 */
	public static double distance(MunsellColor m1, MunsellColor m2)
	{
		double r1 = m1.getChroma();
		double r2 = m2.getChroma();
		double t1 = m1.getHue().getRadians();
		double t2 = m2.getHue().getRadians();
		double z1 = m1.getValue();
		double z2 = m2.getValue();
		double zDiff = z2 - z1;
		
		
		double d2 = r1*r1 + r2*r2 - 2*r1*r2*Math.cos(t1 - t2) + zDiff * zDiff;
		
		double d = Math.sqrt(d2);
		
		return d;
		
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/**
	 * Gets the String representation of this color.
	 *
	 * @return The String representing this color
	 */
	@Override
	public String toString()
	{

		if (chroma == 0.0)
		{
			return "N" + String.format("%d", Math.round(value));
		}
		String valueString = String.format("%.1f", value);
		String chromaString = String.format("%.1f", chroma);
		return hue.toString() + " " + valueString + "/" + chromaString;

	}

}
