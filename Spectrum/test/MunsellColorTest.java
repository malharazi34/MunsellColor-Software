import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the MunsellColor class.
 *
 * @author Ryan McCubbin, Gianna Casolara 
 *
 */
class MunsellColorTest
{
	
	
	/*
	 * toString tests.
	 */
	
	/**
	 * Tests equals for two colors with non-equal constructors but equal colors.
	 */
	@Test
	void testEqualsNonequalConstructor()
	{

		MunsellColor m1 = new MunsellColor("1RP", 8, 0);
		MunsellColor m2 = new MunsellColor("10Y", 0, 0);

		assertTrue(m1.equals(m2), "Both colors should be N0");

	}

	/**
	 * test equals for two equal, non-zero chroma colors.
	 */
	@Test
	void testEqualsNonzeroEqual()
	{

		MunsellColor m1 = new MunsellColor("1RP", 8, 8);
		MunsellColor m2 = new MunsellColor("1RP", 8, 8);

		assertTrue(m1.equals(m2), "Two colors are identical, should be equal");

	}

	/**
	 * Tests equals for non-zero, non-equal chromas.
	 */
	@Test
	void testEqualsNonzeroNonEqualChroma()
	{

		MunsellColor m1 = new MunsellColor("1RP", 7, 8);
		MunsellColor m2 = new MunsellColor("1RP", 8, 8);

		assertFalse(m1.equals(m2), "Two colors have different chromas.");

	}

	/**
	 * test equals for two non-equal hue, non-zero chroma colors.
	 */
	@Test
	void testEqualsNonzeroNonEqualHue()
	{

		MunsellColor m1 = new MunsellColor("1RP", 8, 8);
		MunsellColor m2 = new MunsellColor("2RP", 8, 8);

		assertFalse(m1.equals(m2), "Two colors have different hues");

	}
	
	/**
	 * test equals for two non-equal hue, non-zero chroma colors.
	 */
	@Test
	void testGetValue()
	{

		Hue hue = new Hue(10);
		MunsellColor m = new MunsellColor(hue, 0, 10);
		
		assertEquals(10, m.getValue(), 0);

	}

	/*
	 * getKey tests
	 */
	/**
	 * Tests the getKey method for arbitrary HCVs.
	 */
	@Test
	void testGetKeyHCVArbitrary()
	{

		Hue h1 = new Hue(10);
		Hue h3 = new Hue(34);
		MunsellColor c1 = new MunsellColor(h1, 9, 0);
		MunsellColor c2 = new MunsellColor(20, 0, 4);
		MunsellColor c3 = new MunsellColor(h3, 2, 6);
		MunsellColor c4 = new MunsellColor(55.5, 12, 9);
		MunsellColor c5 = new MunsellColor(87, 3, 7);

		assertTrue(c1.getKey().equals("10.0RP-0-0"), "Should be 10.0RP-0-0, was " + c1.getKey());
		assertTrue(c2.getKey().equals("10.0RP-4-0"), "Should be 10.0RP-4-0, was " + c2.getKey());
		assertTrue(c3.getKey().equals("5.0GY-6-2"), "Should be 5.0GY-6-2, was " + c3.getKey());
		assertTrue(c4.getKey().equals("5.0BG-9-12"), "Should be 5.0BG-9-12, was " + c4.getKey());
		assertTrue(c5.getKey().equals("7.5P-7-4"), "Should be 7.5P-7-4, was " + c5.getKey());

	}

	/**
	 * Tests toString for 0 hue, 0 value, 0 chroma.
	 */
	@Test
	void testGetString000()
	{

		MunsellColor c = new MunsellColor(0, 0, 0);

		assertTrue(c.toString().equals("N0"), "Should be N0, was " + c.toString());

	}

	/**
	 * Tests toString for 0 hue, 10 value, 0 chroma.
	 */
	@Test
	void testGetString10Value()
	{

		MunsellColor c = new MunsellColor(0, 0, 10);

		assertTrue(c.toString().equals("N10"), "Should be N10, was " + c.toString());

	}

	/**
	 * Tests toString for 0 hue, 0 chroma, 5 value.
	 */
	@Test
	void testGetString5Value()
	{

		MunsellColor c = new MunsellColor(0, 0, 5);

		assertTrue(c.toString().equals("N5"), "Should be N5, was " + c.toString());

	}

	/**
	 * Tests for 0 chroma, non-zero hue.
	 */
	@Test
	void testGetStringHCV0Chroma()
	{

		MunsellColor c = new MunsellColor(11, 0, 6);

		assertTrue(c.toString().equals("N6"), "Should be N6, was " + c.toString());

	}

	/**
	 * Tests for 85.5 hue, 10 chroma, 0 value.
	 */
	@Test
	void testGetStringHCV0Value()
	{

		MunsellColor c = new MunsellColor(85.5, 10, 0);
		assertTrue(c.getHue().toString().equals("10.0RP"), "Should be 10.0RP but was " 
				+ c.getHue());
		assertTrue(c.toString().equals("N0"), "Should be N0, was " + c.toString());

	}

	/*
	 * Equals tests.
	 */

	/**
	 * Tests toString for 15 hue, 4 chroma, 5 value.
	 */
	@Test
	void testGetStringHCV1545()
	{

		MunsellColor c = new MunsellColor(15, 4, 5);

		assertTrue(c.toString().equals("5.0YR 5.0/4.0"), "Should be 5.0YR 5.0/4.0, was " 
				+ c.toString());

	}

	/**
	 * Tests toString for 37.5 hue, 12 chroma, 9 value.
	 */
	@Test
	void testGetStringHCV9Value()
	{

		MunsellColor c = new MunsellColor(37.5, 12, 9);

		assertTrue(c.toString().equals("7.5GY 9.0/12.0"), "Should be 7.5GY 9.0/12.0, was " 
				+ c.toString());

	}

	/**
	 * Test toString with arbitrary HCV.
	 */
	@Test
	void testGetStringHCVArbitrary()
	{

		MunsellColor c1 = new MunsellColor(42.5, 3, 5);
		MunsellColor c2 = new MunsellColor(70, 11, 6);
		MunsellColor c3 = new MunsellColor(71.2, 6, 7);

		assertTrue(c1.toString().equals("2.5G 5.0/3.0"), "Should be 2.5G 5.0/3.0, was " 
				+ c1.toString());
		assertTrue(c2.toString().equals("10.0B 6.0/11.0"), "Should be 10B 6.0/11.0, was " 
					+ c2.toString());
		assertTrue(c3.toString().equals("1.2PB 7.0/6.0"), "Should be 1.2PB 7.0/6.0, was " 
						+ c3.toString());

	}

	/**
	 * Test mix with arbitrary colors.
	 */
	@Test
	void testMixArbitraryColors()
	{

		MunsellColor mix11 = new MunsellColor("5Y", 8, 6);
		MunsellColor mix12 = new MunsellColor("5B", 2, 4);

		MunsellColor exp1 = new MunsellColor("7.9Y", 3.245, 5.0);
		MunsellColor mix1 = MunsellColor.mixColors(mix11, mix12, 1, 1);

		assertTrue(exp1.equals(mix1), "Should be " + exp1 + " but was " + mix1);

	}

	/*
	 * mixColors tests.
	 */

	/**
	 * Test mix with arbitrary, weighted colors.
	 */
	@Test
	void testMixArbitraryColorsWeights()
	{

		MunsellColor mix11 = new MunsellColor("10RP", 0, 10);
		MunsellColor mix12 = new MunsellColor("10RP", 0, 0);

		MunsellColor exp1 = new MunsellColor("10RP", 0, 4);

		MunsellColor mix1 = MunsellColor.mixColors(mix11, mix12, 2, 3);

		assertTrue(exp1.equals(mix1), "Should be " + exp1 + " but was " + mix1);

	}

	/**
	 * Tests mixColors for two colors with the same value and hue but different
	 * chroma.
	 */
	@Test
	void testMixDifferentChroma()
	{

		MunsellColor m1 = new MunsellColor("6G", 6, 9);
		MunsellColor m2 = new MunsellColor("6G", 2, 9);

		MunsellColor exp = new MunsellColor("6G", 4, 9);

		assertTrue(exp.equals(MunsellColor.mixColors(m1, m2, 1, 1)), 
				"New chroma should be average");

	}

	/**
	 * Tests mixColors for two colors with the same value and chroma but different
	 * hues.
	 */
	@Test
	void testMixDifferentHue()
	{

		double testChroma = 6;
		double testValue = 6;
		double expChroma = 5.7;
		double expValue = 6;

		MunsellColor m1[] = new MunsellColor[10];
		MunsellColor m2[] = new MunsellColor[10];
		MunsellColor exp[] = new MunsellColor[10];
		MunsellColor mix[] = new MunsellColor[10];

		for (int i = 0; i < 10; i++)
		{
			double hue1 = (i * 10 + 5);
			double hue2 = (hue1 + 10) % 100;
			m1[i] = new MunsellColor(hue1, testChroma, testValue);
			m2[i] = new MunsellColor(hue2, testChroma, testValue);

			double expHue = (i + 1) * 10;
			exp[i] = new MunsellColor(expHue, expChroma, expValue);
			mix[i] = MunsellColor.mixColors(m1[i], m2[i], 1, 1);

		}

		for (int i = 0; i < exp.length; i++)
		{
			assertTrue(exp[i].equals(mix[i]), "Failed on " + i + ". Should be " + exp[i] 
					+ " but was " + mix[i]);
		}

	}

	/**
	 * Test mix for two colors with the same hue and chroma but different value.
	 */
	@Test
	void testMixDifferentValues()
	{

		MunsellColor m1 = new MunsellColor("6G", 8, 7);
		MunsellColor m2 = new MunsellColor("6G", 8, 3);

		MunsellColor exp = new MunsellColor("6G", 8, 5);
		MunsellColor mix = MunsellColor.mixColors(m1, m2, 1, 1);

		assertTrue(exp.equals(mix), "Should be " + exp + " but was " + mix);

	}

	/**
	 * Tests mixColors() for two of the same colors.
	 */
	@Test
	void testMixSameColor()
	{

		MunsellColor m1 = new MunsellColor("6G", 8, 9);

		MunsellColor mix = MunsellColor.mixColors(m1, m1, 1, 1);

		assertTrue(m1.equals(mix), "Should be " + m1 + " but was " + mix);

	}

	/**
	 * Test mix for white and black with nonzero hue and chroma.
	 */
	@Test
	void testMixWhiteBlack()
	{

		MunsellColor m1 = new MunsellColor("6G", 8, 0);
		MunsellColor m2 = new MunsellColor("6G", 8, 10);

		MunsellColor exp = new MunsellColor("10RP", 0, 5);

		MunsellColor mix = MunsellColor.mixColors(m1, m2, 1, 1);

		assertTrue(exp.equals(mix), "Should be " + exp + " but was " + mix);

	}

	/*
	 * String constructor tests
	 */
	/**
	 * Tests the String constructor for several colors.
	 */
	@Test
	void testStringConstructArbitrary()
	{

		MunsellColor c1 = new MunsellColor("2.5G", 3, 5);
		MunsellColor c2 = new MunsellColor("10.0B", 11, 6);
		MunsellColor c3 = new MunsellColor("1.2PB", 6, 7);
		MunsellColor c4 = new MunsellColor("10RP", 5, 7);
		MunsellColor c5 = new MunsellColor("9.9R", 0, 5);

		assertTrue(c1.toString().equals("2.5G 5.0/3.0"), "Should be 2.5G 5.0/3.0, was " 
				+ c1.toString());
		assertTrue(c2.toString().equals("10.0B 6.0/11.0"), "Should be 10B 6.0/11.0, was "
				+ c2.toString());
		assertTrue(c3.toString().equals("1.2PB 7.0/6.0"), "Should be 1.2PB 7.0/6.0, was " 
				+ c3.toString());
		assertTrue(c4.toString().equals("10.0RP 7.0/5.0"), "Should be 10.0RP 7.0/5.0, was " 
				+ c4.toString());
		assertTrue(c5.toString().equals("N5"), "Should be N5, was " + c5.toString());

	}
	
	/*
	 * Equals tolerance tests
	 */
	/**
	 * Tests tolerance equals for two equal colors.
	 */
	@Test
	void testToleranceEqualsEqual() 
	{
		
		MunsellColor m1 = new MunsellColor("10RP", 0, 0);
		MunsellColor m2 = new MunsellColor("10RP", 0, 0);
		
		assertTrue(m1.equals(m2, 1, 1, 1), "Colors are exactly equal");
		
	}
	
	/**
	 * Test tolerance equals for colors with different value.
	 */
	@Test
	void testToleranceEqualsDiffValue()
	{
		
		MunsellColor m1 = new MunsellColor("10RP", 0, 0);
		MunsellColor m2 = new MunsellColor("10RP", 0, 10);
		
		assertFalse(m1.equals(m2, 1, 1, 1), "Colors are opposite in value");
		
	}
	
	/**
	 * Test tolerance equals for colors with different value.
	 */
	@Test
	void testToleranceEqualsDiffChroma()
	{
		
		MunsellColor m1 = new MunsellColor("10RP", 0, 1);
		MunsellColor m2 = new MunsellColor("10RP", 10, 1);
		
		assertFalse(m1.equals(m2, 1, 1, 1), "Colors are unequal in chroma");
		
	}
	
	/**
	 * Test tolerance equals in tolerance range.
	 */
	@Test
	void testToleranceInRangeAll()
	{
		MunsellColor m1 = new MunsellColor("10G", 6, 6);
		MunsellColor m2 = new MunsellColor("1BG", 7, 5.5);
		
		assertTrue(m1.equals(m2, 2, 1, 2), "Colors are within tolerance");
	}
	
	/**
	 * Tests tolerance equals on tolerance range.
	 */
	@Test
	void testToleranceOnRange() 
	{
		
		MunsellColor m1 = new MunsellColor("10G", 6, 6);
		MunsellColor m2 = new MunsellColor("1BG", 7, 5.5);
		
		assertTrue(m1.equals(m2, 1, .5, 1));
		
	}
	
	/*
	 * Distance tests
	 */
	/**
	 * Tests distance for two equal colors.
	 */
	@Test
	void testDistanceEqualColors() 
	{
		
		MunsellColor m1 = new MunsellColor("10G", 0, 0);
		MunsellColor m2 = new MunsellColor("10B", 2, 0);
		
		assertEquals(0.0, MunsellColor.distance(m1, m2), .0001);
		
	}
	
	/**
	 * Tests distance for two colors with same chroma, hue.
	 */
	@Test
	void testDistanceValueOnly()
	{
		
		MunsellColor m1 = new MunsellColor("2.5G", 8, 2);
		MunsellColor m2 = new MunsellColor("2.5G", 8, 7.5);
		
		assertEquals(5.5, MunsellColor.distance(m1, m2), .0001);
		
	}
	
	/**
	 * Tests the distance for two colors with the same value, hue.
	 */
	@Test
	void testDistanceChromaOnly()
	{
		
		MunsellColor m1 = new MunsellColor("2.5G", 6.5, 5);
		MunsellColor m2 = new MunsellColor("2.5G", 4.2, 5);
		
		assertEquals(2.3, MunsellColor.distance(m1, m2), .0001);
		
	}
	
	/**
	 * Tests the distance for two colors with the same value, chroma.
	 */
	@Test
	void testDistanceHueOnly() 
	{
		
		MunsellColor m1 = new MunsellColor(0, 8, 4);
		MunsellColor m2 = new MunsellColor(25, 8, 4);
		MunsellColor m3 = new MunsellColor(50, 8, 4);
		
		assertEquals(16, MunsellColor.distance(m1, m3), .0001);
		assertEquals(Math.sqrt(128), MunsellColor.distance(m2, m1), .0001);
		
	}
	
	/**
	 * Tests the distance method for two colors with same hue.
	 */
	@Test
	void testDistanceValueChroma()
	{
		
		MunsellColor m1 = new MunsellColor("10G", 8, 4);
		MunsellColor m2 = new MunsellColor("10G", 2, 7);
		
		assertEquals(Math.sqrt(45), MunsellColor.distance(m1, m2), .0001);
		
	}
	
	/**
	 * Tests the distance method for two colors with all unequal components.
	 */
	@Test
	void testDistanceAll()
	{
		
		MunsellColor m1 = new MunsellColor(30, 8, 4);
		MunsellColor m2 = new MunsellColor(40, 6, 8);
		
		assertEquals(6.191475, MunsellColor.distance(m1, m2), .00001);
		
	}
	
	/**
	 * Tests the equals method 3 times with random colors.
	 */
	@Test
	void equals3Tests()
	{
		
		MunsellColor m1 = new MunsellColor(0, 7, 2);
		MunsellColor m2 = new MunsellColor(40, 2, 8);
		assertFalse(m1.equals(m2, 0, 3, 3));
		
		m1 = new MunsellColor(40, 5, 2);
		m2 = new MunsellColor(0, 0, 8);
		assertFalse(m1.equals(m2, 0, 0, 3));
		
		m1 = new MunsellColor(20, 0, 5);
		m2 = new MunsellColor(0, 0, 0);
		assertFalse(m1.equals(m2, 0, 0, 3));
		
	}
}
