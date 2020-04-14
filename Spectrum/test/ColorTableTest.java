import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ColorTable class.
 *
 * @author Ryan McCubbin
 *
 */
class ColorTableTest
{

	/**
	 * Sets up the table.
	 */
	@BeforeAll
	static void setUp()
	{

		try
		{
			ColorTable.loadTable();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Tests munsellToRGB for colors that needs to change chroma to fit the table.
	 */
	@Test
	void testMunsellToRGBChangeChroma()
	{

		MunsellColor m1 = new MunsellColor(2.5, 12, 1);
		MunsellColor m2 = new MunsellColor(Hue.getHueNumber(5, "Y"), 12, 3);
		MunsellColor m3 = new MunsellColor(Hue.getHueNumber(10, "Y"), 9, 3);

		Color c1e = new Color(78, 0, 38);
		Color c2e = new Color(91, 69, 0);
		Color c3e = new Color(79, 74, 0);

		Color c1 = ColorTable.munsellToRGB(m1);
		Color c2 = ColorTable.munsellToRGB(m2);
		Color c3 = ColorTable.munsellToRGB(m3);

		assertTrue(c1.equals(c1e), "Should be " + c1e + " but was " + c1);
		assertTrue(c2.equals(c2e), "Should be " + c2e + " but was " + c2);
		assertTrue(c3.equals(c3e), "Should be " + c3e + " but was " + c3);
	}

	/**
	 * Test MunsellToRGB with non-zero hue, zero chroma.
	 */
	@Test
	void testMunsellToRGBChroma0()
	{

		MunsellColor m = new MunsellColor(25, 0, 6);

		Color c1 = ColorTable.munsellToRGB(m);

		Color c1e = new Color(151, 146, 154);

		assertTrue(c1.equals(c1e));

	}

	/**
	 * Test MunsellToRGB with hue = 0, non-zero chroma.
	 */
	@Test
	void testMunsellToRGBHue0()
	{

		MunsellColor m1 = new MunsellColor(0, 10, 7);

		Color c1 = ColorTable.munsellToRGB(m1);

		Color c1e = new Color(252, 140, 165);

		assertTrue(c1.equals(c1e), "Should be " + c1e + " but was " + c1);

	}

	/**
	 * Test MunsellToRGB with neutral Munsell colors.
	 */
	@Test
	void testMunsellToRGBNeutral()
	{
		MunsellColor m1 = new MunsellColor(0, 0, 10);
		MunsellColor m2 = new MunsellColor(0, 0, 0);
		MunsellColor m3 = new MunsellColor(0, 0, 4);

		Color c1 = ColorTable.munsellToRGB(m1);
		Color c2 = ColorTable.munsellToRGB(m2);
		Color c3 = ColorTable.munsellToRGB(m3);

		Color c2e = new Color(0, 0, 0);
		Color c1e = new Color(253, 253, 253);
		Color c3e = new Color(99, 95, 101);

		assertTrue(c1.equals(c1e), "Failed on Color 1, expected " + c1e + " but was " + c1);
		assertTrue(c2.equals(c2e), "Failed on Color 2");
		assertTrue(c3.equals(c3e), "Failed on Color 3");

	}

	/**
	 * Test MunsellToRGB with a few colors with non-neutral colors where no rounding
	 * is necessary.
	 */
	@Test
	void testMunsellToRGBNoRound()
	{

		MunsellColor m1 = new MunsellColor(27.5, 4, 9);
		MunsellColor m2 = new MunsellColor(60, 8, 3);
		MunsellColor m3 = new MunsellColor(45, 2, 1);

		Color c1 = ColorTable.munsellToRGB(m1);
		Color c2 = ColorTable.munsellToRGB(m2);
		Color c3 = ColorTable.munsellToRGB(m3);

		Color c1e = new Color(243, 227, 176);
		Color c2e = new Color(0, 84, 106);
		Color c3e = new Color(19, 31, 27);

		assertTrue(c1.equals(c1e), "Should be " + c1e + " but was " + c1);
		assertTrue(c2.equals(c2e), "Should be " + c2e + " but was " + c2);
		assertTrue(c3.equals(c3e), "Should be " + c3e + " but was " + c3);

	}

	/**
	 * Test MunsellToRGB with a few colors where rounding is necessary.
	 */
	@Test
	void testMunsellToRGBRound()
	{

		MunsellColor m1 = new MunsellColor(22, 4, 8); // Should round hue up to 22.5
		MunsellColor m2 = new MunsellColor(75, 5.99, 6); // Should round chroma up to 6
		MunsellColor m3 = new MunsellColor(56.25, 6, 7); // Should round hue up to 57.5
		MunsellColor m4 = new MunsellColor(82.5, 3, 8); // Should round chroma up to 4
		MunsellColor m5 = new MunsellColor(21, 4, 8); // Should round hue down to 20
		MunsellColor m6 = new MunsellColor(95, 0.99, 5); // Should round chroma down to 0

		// Should round hue up to 62.5 and chroma down to 8
		MunsellColor m7 = new MunsellColor(62, 8.5, 2);

		// Should round hue up to 67.5 and hue up to 6
		MunsellColor m8 = new MunsellColor(66.25, 5, 3);

		MunsellColor m9 = new MunsellColor(65, 5, 10);

		MunsellColor[] mColors = new MunsellColor[] {m1, m2, m3, m4, m5, m6, m7, m8, m9};
		Color[] rgbColors = new Color[mColors.length];

		for (int i = 0; i < rgbColors.length; i++)
		{
			Color c = ColorTable.munsellToRGB(mColors[i]);
			if (c == null)
			{
				fail("Failed on " + mColors[i].getKey());
			}
			rgbColors[i] = ColorTable.munsellToRGB(mColors[i]);

		}

		Color[] expRGBColors = new Color[] {

			new Color(225, 196, 156), new Color(127, 148, 196), new Color(103, 187, 194), 
			new Color(209, 194, 233),new Color(230, 194, 160), new Color(125, 121, 127), 
			new Color(0, 60, 89), new Color(0, 78, 114), new Color(253, 253, 253)

		};

		for (int i = 0; i < mColors.length; i++)
		{

			assertTrue(rgbColors[i].equals(expRGBColors[i]),
					"Should be " + expRGBColors[i] + " but was " + rgbColors[i]);

		}

	}

	/**
	 * Tests RGBToMunsell for exact RGB values on the table.
	 */
	@Test
	void testRGBToMunsellExact()
	{

		Color c1 = new Color(0, 0, 0);
		Color c2 = new Color(34, 51, 187);
		Color c3 = new Color(153, 153, 153);
		Color c4 = new Color(153, 34, 136);
		Color c5 = new Color(255, 153, 170);

		MunsellColor m1e = new MunsellColor("10RP", 0, 0);

		MunsellColor m2e = new MunsellColor("7.10PB", 17.15, 2.98);

		MunsellColor m3e = new MunsellColor("7.95GY", 0.69, 6.22);
		MunsellColor m4e = new MunsellColor("0.92RP", 13.45, 3.67);
		MunsellColor m5e = new MunsellColor("1.40R", 8.75, 7.34);

		MunsellColor m1 = ColorTable.rgbToMunsell(c1);
		MunsellColor m2 = ColorTable.rgbToMunsell(c2);
		MunsellColor m3 = ColorTable.rgbToMunsell(c3);
		MunsellColor m4 = ColorTable.rgbToMunsell(c4);
		MunsellColor m5 = ColorTable.rgbToMunsell(c5);

		assertTrue(m1.equals(m1e), "Failed on m1. Was " + m1 + " but should be " + m1e);
		assertTrue(m2.equals(m2e), "Failed on m2. Was " + m2 + " but should be " + m2e);
		assertTrue(m3.equals(m3e), "Failed on m3. Was " + m3 + " but should be " + m3e);
		assertTrue(m4.equals(m4e), "Failed on m4. Was " + m4 + " but should be " + m4e);
		assertTrue(m5.equals(m5e), "Failed on m5. Was " + m5 + " but should be " + m5e);
	}

	/**
	 * Tests rgbToMunsell for colors that are not on the table.
	 */
	@Test
	void testRGBToMunsellRound()
	{

		Color c1 = new Color(17, 51, 238);

		MunsellColor m1e = new MunsellColor("6.94PB", 21.16, 3.79);

		MunsellColor m1 = ColorTable.rgbToMunsell(c1);

		Color c2 = new Color(51, 17, 255);

		MunsellColor m2e = new MunsellColor("7.32PB", 25.52, 3.2);

		MunsellColor m2 = ColorTable.rgbToMunsell(c2);

		Color c3 = new Color(34, 34, 170);

		MunsellColor m3e = new MunsellColor("7.65PB", 16.86, 2.57);

		MunsellColor m3 = ColorTable.rgbToMunsell(c3);

		Color c4 = new Color(0, 0, 17);

		MunsellColor m4e = new MunsellColor("5.78GY", 3.84, 1.06);

		MunsellColor m4 = ColorTable.rgbToMunsell(c4);

		assertTrue(m4.equals(m4e), "Should be " + m4e + " but was " + m4);
		assertTrue(m3.equals(m3e), "Should be " + m3e + " but was " + m3);
		assertTrue(m2.equals(m2e), "Should be " + m2e + " but was " + m2);
		assertTrue(m1.equals(m1e));

	}

}
