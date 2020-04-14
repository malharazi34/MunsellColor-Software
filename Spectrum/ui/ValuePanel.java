import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The panel holding the Munsell Value parts of Spectrum.
 *
 * @author Ryan McCubbin
 *
 */
public class ValuePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor.
	 */
	public ValuePanel()
	{

		// This label should be removed when actual implementation is done. I just added
		// it to test
		// the buttons
		this.setLayout(new BorderLayout());

		MunsellColor[] stdValues = new MunsellColor[11];

		for (int i = 0; i < stdValues.length; i++)
		{

			stdValues[i] = new MunsellColor(0, 0, i);

		}

		ColorPanel[] colors = new ColorPanel[stdValues.length];
		JPanel colorGradient = new JPanel(new GridBagLayout());
		for (int i = 0; i < colors.length; i++)
		{

			GridBagConstraints con = new GridBagConstraints();
			con.weightx = 1;
			con.gridx = i;
			con.gridy = 0;
			con.ipady = 80;
			con.insets = new Insets(50, 0, 0, 0);
			con.anchor = GridBagConstraints.ABOVE_BASELINE;
			con.fill = GridBagConstraints.BOTH;
			colors[i] = new ColorPanel(stdValues[i]);

			MunsellColor textColor = new MunsellColor(0, 0, 10 * Math.round((10 - i) / 10.0));
			colors[i].setTextColor(ColorTable.munsellToRGB(textColor));
			colorGradient.add(colors[i], con);

		}
		this.add(colorGradient, BorderLayout.NORTH);
		String explanation1 = "This shows all standard, neutral Munsell Colors. Note that neutral "
				+ "Munsell colors are referred to as N[value] instead of the usual three number " 
				+ "strings.";

		JLabel expLabel1 = new JLabel(explanation1, SwingConstants.CENTER);
		expLabel1.setFont(expLabel1.getFont().deriveFont((float) 16.0));
		this.add(expLabel1, BorderLayout.CENTER);

	}

}
