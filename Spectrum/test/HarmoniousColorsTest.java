import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Harmonious Colors Class.
 *
 * @author Mohammed Al-Harazi
 *
 */

class HarmoniousColorsTest
{

	/**
	 * Tests the analogous colors function.
	 */
	@Test
	void analogousTest()
	{
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor[] m = new MunsellColor[2];
		m = h.getAnalogousColors(1);
		assertEquals(m[0].getHue().getHue(), 6, "Hue should be 6");

		h = new HarmoniousColors(new MunsellColor(80, 2, 6));
		m = h.getAnalogousColors(30);
		assertEquals(m[0].getHue().getHue(), 10, "Hue should be 10");

		h = new HarmoniousColors(new MunsellColor(20, 2, 6));
		m = h.getAnalogousColors(30);
		assertEquals(m[1].getHue().getHue(), 90, "Hue should be 90");
	}

	/**
	 * Tests the complementary colors function.
	 */
	@Test
	void complementaryTest()
	{
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor c = h.getComplementary();
		assertEquals(c.getHue().getHue(), 55);

		h = new HarmoniousColors(new MunsellColor(80, 2, 6));
		c = h.getComplementary();
		assertEquals(c.getHue().getHue(), 30, "Hue should be 30");
		assertEquals(c.getChroma(), 2, "Chroma should be 2");
		assertEquals(c.getValue(), 6, "Value should be 6");

	}

	/**
	 * Tests the Harmonious Color constructors.
	 */
	@Test
	void constructorTest()
	{
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		assertEquals(5, h.getHue(), "Hue should be 5");
		assertEquals(2, h.getChroma(), "Chroma should be 2");
		assertEquals(6, h.getValue(), "Value should be 6");

	}

	/**
	 * Tests the split complementary colors function.
	 */
	@Test
	void splitTest()
	{
		// Regular Test to see if calculations are correct
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor[] m = new MunsellColor[2];
		MunsellColor c = h.getComplementary();
		h = new HarmoniousColors(c);
		m[0] = h.getAnalogousColors(5)[0];
		m[1] = h.getAnalogousColors(5)[1];

		assertEquals(m[0].getHue().getHue(), 60);
		assertEquals(m[1].getHue().getHue(), 50);

		// Check to make sure analogous hue value wraps around if over 100
		h = new HarmoniousColors(new MunsellColor(45, 2, 6));
		c = h.getComplementary();
		h = new HarmoniousColors(c);

		m[0] = h.getAnalogousColors(30)[0];
		m[1] = h.getAnalogousColors(30)[1];

		assertEquals(m[0].getHue().getHue(), 25);
		assertEquals(m[1].getHue().getHue(), 65);

	}

	/**
	 * Tests the square colors function.
	 */
	@Test
	void squareTest()
	{
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor[] m = new MunsellColor[4];
		m = h.getSquareColors();
		assertEquals(m[0].getHue().getHue(), 5);
		assertEquals(m[1].getHue().getHue(), 30);
		assertEquals(m[2].getHue().getHue(), 55);
		assertEquals(m[3].getHue().getHue(), 80);
	}

	/**
	 * Tests the tetrad colors function.
	 */
	@Test
	void tetradTest()
	{
		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor[] m = new MunsellColor[4];
		m = h.getTetradColors(new MunsellColor(45, 5, 7));
		assertEquals(m[0].getHue().getHue(), 5);
		assertEquals(m[1].getHue().getHue(), 55);
		assertEquals(m[2].getHue().getHue(), 45);
		assertEquals(m[3].getHue().getHue(), 95);

	}

	/**
	 * Tests the triad colors function.
	 */
	@Test
	void triadTest()
	{

		HarmoniousColors h = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor[] m = new MunsellColor[3];
		m = h.getTriadColors();
		assertEquals(m[0].getHue().getHue(), h.getHue());
		assertEquals(m[1].getHue().getHue(), 38.3);
		assertEquals(m[2].getHue().getHue(), 71);

	}
	
	/**
	 * Tests the split comp function.
	 */
	@Test
	void splitCompTest()
	{
		double distance = 2;
		HarmoniousColors test = new HarmoniousColors(new MunsellColor(5, 2, 6));
		MunsellColor e1 = new HarmoniousColors(new MunsellColor(7, 2, 6)).getComplementary();
		MunsellColor e2 = new HarmoniousColors(new MunsellColor(3, 2, 6)).getComplementary();
		
		MunsellColor[] expected = new MunsellColor[2];
		expected[0] = e1;
		expected[1] = e2;
		
		MunsellColor[] actual = test.getSplitComp(distance);
		assertArrayEquals(expected, actual);

	}
	
	/**
	 * Tests getSameHueColors.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	void testGetSameHueColors() throws FileNotFoundException, IOException
	{
		
		MunsellColor mc = new MunsellColor("5Y", 4, 4);
		
		HarmoniousColors hc = new HarmoniousColors(mc);
		
		ColorTable.loadTable();
		
		MunsellColor[][] actual = hc.getSameHueColors();
		
		boolean success = true;
		
		for (int i = 0; i < actual.length && success; i++)
		{
			
			for (int j = 0; j < actual[i].length && success; j++) 
			{
				
				if (actual[i][j] != null)
				{
					
					if (actual[i][j].getChroma() != 0
							&& !actual[i][j].getHue().toString().equals("5.0Y"))
					{
						success = false;
						
					}
					
				}
				
			}
			
		}
		
		assertTrue(success, "Get same hue colors failed");
		
		
	}
	
}
