import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

//import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The panel for the hue rendering function.
 *
 * @author Ryan McCubbin, Abdulshaid Alibekov
 *
 */
public class HuePanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private HueInputPanel input;
	private ActionListener inputConfirmed;
	private JPanel output1;

	/**
	 * Default constructor.
	 */
	public HuePanel()
	{
		this.setLayout(new BorderLayout());
		output1 = new JPanel();

		inputConfirmed = e -> setOutputColor();

		setInputPanel();
	}

	/**
	 * Sets the input for which color to see the colors of the same hue.
	 */
	private void setInputPanel()
	{

		input = new HueInputPanel(inputConfirmed);

		this.add(input, BorderLayout.WEST);

	}

	/**
	 * Sets the background color for the color output panel.
	 */

	private void setOutputColor()
	{
		this.setVisible(false);
		MunsellColor baseHueColor = input.getColor();
		HarmoniousColors hc = new HarmoniousColors(baseHueColor);
		MunsellColor[][] shc = hc.getSameHueColors();
		try
		{
			this.remove(output1);
		} catch (NullPointerException e)
		{
		}

		output1 = new JPanel();
		output1.setLayout(new GridLayout(9, 11));
		this.setVisible(false);
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 11; j++)
			{
				ColorPanel colorPanel = new ColorPanel(shc[i][j]);
				if (shc[i][j].getValue() != 10)
				{
					colorPanel.setFontSize(10);
				} else
				{
					colorPanel.setFontSize(0);
				}
				output1.add(colorPanel);
			}
		}
		this.setVisible(true);
		this.add(output1);

	}
}
