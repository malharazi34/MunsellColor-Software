import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The panel for the Harmonious Colors function.
 *
 * @author Ryan McCubbin, Mohammed Al-Harazi
 *
 */
public class HarmoniousPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	private JButton ana;

	private JButton comp;

	private double distance = 1;

	private JPanel harmoniousPanel;

	private String harmoniousType;

	private ColorInputPanel input;

	private ActionListener inputConfirmed;

	private boolean isValidDistance = false;

	private JButton split;

	private JButton square;

	private MunsellColor tempColor;

	private JButton tetrad;

	private JButton triad;

	/**
	 * Default Constructor.
	 */
	public HarmoniousPanel()
	{

		// Creates a panel for the Munsell Colors to appear on and an ActionListener is
		// used
		// to see if a button is pressed, which will prompt it to go to the
		// setOutputColor function.
		// The function will check for which harmoniousType is passed and output the
		// corresponding colors
		this.setLayout(new BorderLayout());
		harmoniousPanel = new JPanel();
		harmoniousType = "Complementary Color";
		inputConfirmed = e -> setOutputColor();

		// Sets the different outputs depending on the input and button selected
		setButtonPanel();
		setInputPanel();
	}

	/**
	 * Gets the double distance that will be used to compute analogous and split
	 * complementary colors.
	 *
	 * @return distance double inputed by the user
	 */
	public double getDistance()
	{

		return distance;

	}

	/**
	 * Sets buttons for different Harmonious Functions.
	 */
	private void setButtonPanel()
	{

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 3, 5, 10));

		// Initializes the Buttons for each Harmonious function
		comp = new JButton("Complementary Color");
		ana = new JButton("Analogous Colors");
		split = new JButton("Split Complementary Colors");
		square = new JButton("Square Colors");
		tetrad = new JButton("Tetrad Colors");
		triad = new JButton("Triad Colors");

		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		comp.setPreferredSize(new Dimension(50, 50));

		// If a certain button is pressed then the harmoniousType string will change to
		// the
		// corresponding button name which will be used in setOutputColor
		comp.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				harmoniousType = "Complementary Colors";
				setOutputColor();
			}
		});

		// The Analogous function requires a distance from the user
		ana.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				isValidDistance = false;
				distance = 0;
				harmoniousType = "Analogous Colors";
				do
				{
					try
					{

						distance = Double
								.parseDouble(JOptionPane.showInputDialog("Please enter a distance "
										+ "between 1-49:"));

					} catch (NumberFormatException ex)
					{

						JOptionPane.showMessageDialog(null, "Invalid Input");
						isValidDistance = false;
					} catch (NullPointerException ex) 
					{
						
						return;
						
					}

					if (distance <= 49 && distance >= 1)
					{
						isValidDistance = true;
					}
				} while (!isValidDistance);

				setOutputColor();
			}
		});

		// The Split Complementary function requires a distance from the user
		split.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				isValidDistance = false;
				distance = 0;
				harmoniousType = "Split Complementary Colors";
				do
				{

					try
					{
						distance = Double
								.parseDouble(JOptionPane.showInputDialog("Please enter a distance "
										+ "between 1-49:"));

					} catch (NumberFormatException ex)
					{

						JOptionPane.showMessageDialog(null, "Invalid Input");
						isValidDistance = false;
					} catch (NullPointerException ex)
					{
						
						return;
						
					}

					if (distance <= 49 && distance >= 1)
					{

						isValidDistance = true;
					}
				} while (!isValidDistance);

				setOutputColor();
			}
		});
		square.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				harmoniousType = "Square Colors";
				setOutputColor();
			}
		});

		// The tetrad colors function requires another Munsell Color from the user for
		// the calculations
		tetrad.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				boolean valid = false;
				double tempHue = -1;
				double tempChroma = -1;
				double tempValue = -1;
				harmoniousType = "Tetrad Colors";
				JOptionPane.showMessageDialog(null, "You will be prompted to input a Munsell "
						+ "Color");
				do
				{
					valid = false;
					try
					{
						tempHue = Double.parseDouble(JOptionPane.showInputDialog("Please enter a "
								+ "Hue between 0-10:"));

					} catch (NumberFormatException ex)
					{

						JOptionPane.showMessageDialog(null, "Invalid Input");
						valid = false;
					} catch (NullPointerException ex) 
					{
						return;
					}

					if (tempHue >= 0 && tempHue <= 10)
					{
						valid = true;
					}
				} while (!valid);

				valid = false;
				do
				{
					try
					{

						tempValue = Double
								.parseDouble(JOptionPane.showInputDialog("Please enter a Value "
										+ "between 0-10:"));

					} catch (NumberFormatException ex)
					{

						JOptionPane.showMessageDialog(null, "Invalid Input");
						valid = false;
					}

					if (tempValue >= 0 && tempValue <= 10)
					{
						valid = true;
					}

				} while (!valid);

				valid = false;
				do
				{
					try
					{

						tempChroma = Double
								.parseDouble(JOptionPane.showInputDialog("Please enter a Chroma "
										+ "between 0-100:"));

					} catch (NumberFormatException ex)
					{

						JOptionPane.showMessageDialog(null, "Invalid Input");
						valid = false;
					}

					if (tempChroma >= 0 && tempChroma <= 100)
					{
						valid = true;
					}

				} while (!valid);

				// This is the part that producing the error

				tempColor = new MunsellColor(tempHue, tempChroma, tempValue);

				tempColor = ColorTable.rgbToMunsell(ColorTable.munsellToRGB(tempColor));

				setOutputColor();
			}
		});
		triad.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				harmoniousType = "Triad Colors";
				setOutputColor();
			}
		});

		// The buttons with their ActionListeners are added to the JPanel
		p.add(comp);
		p.add(ana);
		p.add(split);
		p.add(square);
		p.add(tetrad);
		p.add(triad);

		this.add(p, BorderLayout.NORTH);
		p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

	}

	/**
	 * Sets the input for which color to see the harmonious colors of.
	 */
	private void setInputPanel()
	{

		input = new ColorInputPanel(inputConfirmed);

		this.add(input, BorderLayout.WEST);

	}

	/**
	 * Sets the background color for the color output panel.
	 */
	private void setOutputColor()
	{

		int num;
		try
		{

			this.remove(harmoniousPanel);

		} catch (NullPointerException e)
		{
		}

		this.setVisible(false);

		// This takes the color that the user provided and stores it in the
		// outputBaseColor object.
		// The MunsellColor will then be used in a HarmoniousColors object that
		// calculates the
		// corresponding harmonious color. The grid of the panel will depend on how many
		// MunsellColor
		// objects are returned for the function. The Analogous and Split-Complementary
		// functions
		// still need to be implemented, but we need to prompt for user input because it
		// requires a
		// distance to run their function.
		MunsellColor outputBaseColor = input.getColor();
		HarmoniousColors harmColor = new HarmoniousColors(outputBaseColor);
		// Switch that performs the Harmonious Button selected
		harmoniousPanel = new JPanel();
		switch (harmoniousType)
		{

			case "Complementary Colors":
				outputBaseColor = harmColor.getComplementary();
				harmoniousPanel.setLayout(new GridLayout(1, 1));
				num = 1;
				break;
			case "Analogous Colors":
				harmoniousPanel.setLayout(new GridLayout(1, 2));
				num = 2;
				for (int i = 0; i < num; i++)
				{
	
					ColorPanel colorPanel = new ColorPanel(harmColor.getAnalogousColors(
							distance)[i]);
					colorPanel.setFontSize(24);
					harmoniousPanel.add(colorPanel);
				}
				break;
	
			case "Split Complementary Colors":
	
				harmoniousPanel.setLayout(new GridLayout(1, 2));
				num = 2;
				for (int i = 0; i < num; i++)
				{
	
					MunsellColor temp = harmColor.getComplementary();
					HarmoniousColors temp1 = new HarmoniousColors(temp);
					ColorPanel colorPanel = new ColorPanel(temp1.getAnalogousColors(distance)[i]);
					colorPanel.setFontSize(24);
					harmoniousPanel.add(colorPanel);
				}
				break;
	
			case "Square Colors":
	
				num = 4;
				harmoniousPanel.setLayout(new GridLayout(1, 4));
				for (int i = 0; i < num; i++)
				{
	
					ColorPanel colorPanel = new ColorPanel(harmColor.getSquareColors()[i]);
					colorPanel.setFontSize(24);
					harmoniousPanel.add(colorPanel);
				}
				break;
	
			case "Tetrad Colors":
	
				num = 4;
				harmoniousPanel.setLayout(new GridLayout(1, 4));
	
				for (int i = 0; i < num; i++)
				{
	
					ColorPanel colorPanel = new ColorPanel(harmColor.getTetradColors(tempColor)[i]);
					colorPanel.setFontSize(24);
					harmoniousPanel.add(colorPanel);
				}
				break;
	
			case "Triad Colors":
	
				num = 3;
				harmoniousPanel.setLayout(new GridLayout(1, 3));
				for (int i = 0; i < num; i++)
				{
	
					ColorPanel colorPanel = new ColorPanel(harmColor.getTriadColors()[i]);
					colorPanel.setFontSize(24);
					harmoniousPanel.add(colorPanel);
				}
				break;
	
			default:
				num = 1;
				harmoniousPanel.setLayout(new GridLayout(1, 1));
				break;

		}

		if (num == 1)
		{

			MunsellColor outputColor = outputBaseColor;
			ColorPanel colorPanel = new ColorPanel(outputColor);
			colorPanel.setFontSize(24);
			harmoniousPanel.add(colorPanel);

		}
		this.setVisible(true);
		this.add(harmoniousPanel);

	}

}
