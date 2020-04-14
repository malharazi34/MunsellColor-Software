import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the hue class.
 *
 * @author Ryan McCubbin
 *
 */
class HueTest
{

	private double sensitivity = 0.001;

	/**
	 * Test that input of 100 gives hue of 0.
	 */
	@Test
	void testGetHue100()
	{

		Hue h = new Hue(100);
		assertEquals(0, h.getHue(), sensitivity, "Input 100 should give hue 0");

	}

	/**
	 * Tests for arbitrary h's.
	 */
	@Test
	void testGetHueArbitrary()
	{

		Hue h1 = new Hue(1);
		Hue h2 = new Hue(27.5);
		Hue h3 = new Hue(99.99);
		Hue h4 = new Hue(32);
		Hue h5 = new Hue(10.001);
		Hue h6 = new Hue(58.99);

		assertEquals(1, h1.getHue(), sensitivity, "h1 should be 1");
		assertEquals(27.5, h2.getHue(), sensitivity, "h2 should be 27.5");
		assertEquals(99.99, h3.getHue(), sensitivity, "h3 should be 99.99");
		assertEquals(32, h4.getHue(), sensitivity, "h4 should be 32");
		assertEquals(10.001, h5.getHue(), sensitivity, "h5 should be 10.001");
		assertEquals(58.99, h6.getHue(), sensitivity, "h6 should be 58.99");

	}

	/**
	 * Tests that arbitrarily large input gives correct hue.
	 */
	@Test
	void testGetHueLarge()
	{

		Hue h = new Hue(1000083.45);
		assertEquals(83.45, h.getHue(), sensitivity, "Input 1000083.45 should give hue 83.45");

	}

	/**
	 * Tests the getHueNumber() method with 10RP.
	 */
	@Test
	void testGetHueNumber10RP()
	{

		assertEquals(0, Hue.getHueNumber(10, "RP"));

	}

	/**
	 * Tests the getHueNumber() for all 10's.
	 */
	@Test
	void testGetHueNumber10Y()
	{

		assertEquals(10, Hue.getHueNumber(10, "R"), "Failed on 10R");
		assertEquals(20, Hue.getHueNumber(10, "YR"), "Failed on 10YR");
		assertEquals(30, Hue.getHueNumber(10, "Y"), "Failed on 10Y");
		assertEquals(40, Hue.getHueNumber(10, "GY"), "Failed on 10GY");
		assertEquals(50, Hue.getHueNumber(10, "G"), "Failed on 10G");
		assertEquals(60, Hue.getHueNumber(10, "BG"), "Failed on 10BG");
		assertEquals(70, Hue.getHueNumber(10, "B"), "Failed on 10Y");
		assertEquals(80, Hue.getHueNumber(10, "PB"), "Failed on 10Y");
		assertEquals(90, Hue.getHueNumber(10, "P"), "Failed on 10Y");

	}

	/**
	 * Tests the getHueNumber for arbitrary hues.
	 */
	@Test
	void testGetHueNumberArbitrary()
	{
		assertEquals(62.5, Hue.getHueNumber(2.5, "B"), "Failed on 2.5B");
		assertEquals(35.5, Hue.getHueNumber(5.5, "GY"), "Failed on 5.5GY");
		assertEquals(84.778, Hue.getHueNumber(4.778, "P"), "Failed on 4.778P");
	}

	/**
	 * Tests for h = 0.
	 */
	@Test
	void testGetHueZero()
	{

		Hue h = new Hue(0);
		assertEquals(0, h.getHue(), sensitivity, "Hue should be 0");

	}

	/**
	 * Tests getRadians for h = 100 (should be 0).
	 */
	@Test
	void testGetRadian100()
	{

		Hue h = new Hue(100);

		assertEquals(0, h.getRadians());

	}

	/**
	 * Tests getRadians for h = 0.
	 */
	@Test
	void testGetRadians0()
	{

		Hue h = new Hue(0);
		assertEquals(0, h.getRadians(), sensitivity, "Hue 0 should have radians 0");

	}

	/**
	 * Test getRadians for arbitrary inputs.
	 */
	@Test
	void testGetRadiansArbitrary()
	{

		Hue h1 = new Hue(1);
		Hue h2 = new Hue(50);
		Hue h3 = new Hue(75);

		assertEquals(Math.PI / 50, h1.getRadians(), sensitivity, "1 Unit in Hue should be PI/50 in "
				+ "radians");
		assertEquals(Math.PI, h2.getRadians(), sensitivity, "Halfway should be PI radians");
		assertEquals((3 * Math.PI) / 2, h3.getRadians(), sensitivity, "Three quarters should be "
				+ "3*PI/2 radians");

	}

	/**
	 * Tests the String constructor for 10P.
	 */
	@Test
	void testHueStringConstruct10P()
	{

		Hue h = new Hue("10P");
		assertEquals(90, h.getHue());

	}

	/**
	 * Tests the String constructor for 10RP.
	 */
	@Test
	void testHueStringConstruct10RP()
	{

		Hue h = new Hue("10RP");
		assertEquals(0, h.getHue());

	}

	/**
	 * Tests the toString() method for hue = 0 (10RP).
	 */
	@Test
	void testString0()
	{

		Hue h = new Hue(0);
		assertTrue(h.toString().equals("10.0RP"), "Failed on 10.0RP, was " + h.toString());

	}

	/**
	 * Tests the toString method for hue = 10 (10R).
	 */
	@Test
	void testString10()
	{

		Hue h = new Hue(10);
		assertTrue(h.toString().equals("10.0R"), "Failed on 10.0R, was " + h.toString());

	}

	/**
	 * Test the toString method for a few arbitrary numbers.
	 */
	@Test
	void testStringArbitrary()
	{

		Hue h1 = new Hue(1);
		Hue h2 = new Hue(45.4);
		Hue h3 = new Hue(79.9);
		Hue h4 = new Hue(62);

		assertTrue(h1.toString().equals("1.0R"), "Failed on 1.0R, was " + h1.toString());
		assertTrue(h2.toString().equals("5.4G"), "Failed on 5.4G, was " + h2.toString());
		assertTrue(h3.toString().equals("9.9PB"), "Failed on 9.9PB, was " + h3.toString());
		assertTrue(h4.toString().equals("2.0B"), "Failed on 2.0B, was " + h4.toString());

	}

}
