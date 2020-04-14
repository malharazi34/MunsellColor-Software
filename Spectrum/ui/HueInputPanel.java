import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A Panel to use to input a MunsellColor when one is needed.
 *
 * @author Ryan McCubbin, Abdulshaid Alibekov
 *
 */
public class HueInputPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private ActionListener buttonListener;
	private double chroma;
	private MunsellColor color;
	private ColorPanel colorChosen;

	private GridBagConstraints colorChosenCon;
	private JButton confirm;

	private ActionListener confirmListener;
	private Hue hue;

	private JTextField huePrefixInput;

	private JComboBox<String> hueSuffixInput;
	private double value;

	/**
	 * Default constructor.
	 *
	 * @param confirmListener The listener for the button confirmation
	 */
	public HueInputPanel(ActionListener confirmListener)
	{
		hue = new Hue(0);

		color = new MunsellColor(0, 0, 0);
		this.confirmListener = confirmListener;

		confirm = new JButton("Confirm");
		confirm.setEnabled(false);

		huePrefixInput = new JTextField();

		hueSuffixInput = new JComboBox<>();
		String[] suffixes = new String[] {"R", "YR", "Y", "GY", "G", "BG", "B", "PB", "P", "RP"};
		for (String s : suffixes)
		{
			hueSuffixInput.addItem(s);
		}

		InputVerifier numInputVerifier = new InputVerifier()
		{

			@Override
			public boolean shouldYieldFocus(JComponent input)
			{
				boolean valid = verify(input);

				if (valid) // If there is a bad input, turn the button off until it is good
				{
					confirm.setEnabled(true);
				} else
				{
					confirm.setEnabled(false);
				}

				return valid;
			}

			@Override
			public boolean verify(JComponent arg0)
			{
				boolean isNumberInRange = true;
				JTextField field = (JTextField) arg0;
				String text = field.getText();

				// If the text in the field is a number, return true. else, return false.
				try
				{
					double d = Double.parseDouble(text);

					if (!text.isEmpty())
					{

						isNumberInRange = d >= 0 && d <= 10;

					} else
					{

						isNumberInRange = false;

					}
				} catch (NumberFormatException e)
				{
					isNumberInRange = false;
				}

				return isNumberInRange;
			}

		};

		huePrefixInput.setInputVerifier(numInputVerifier);

		colorChosen = new ColorPanel(color);

		// Since these constraints have to be used more than once, it is easier to keep
		// them in their
		// own object
		colorChosenCon = new GridBagConstraints();
		colorChosenCon.ipadx = 10;
		colorChosenCon.ipady = 0;
		colorChosenCon.weightx = .2;
		colorChosenCon.weighty = .2;
		colorChosenCon.gridx = 0;
		colorChosenCon.gridy = 4;
		colorChosenCon.gridwidth = 2;
		colorChosenCon.fill = GridBagConstraints.BOTH;
		colorChosenCon.insets = new Insets(0, 5, 0, 5);

		// If confirm is pressed, set the color, notify the class using this inputPanel,
		// and set
		// the color preview panel.
		buttonListener = e -> {
			this.hue = new Hue(huePrefixInput.getText() + hueSuffixInput.getSelectedItem());
			this.chroma = 20;
			this.value = 4;
			this.color = new MunsellColor(this.hue, this.chroma, this.value);
			setHVC();
			setColorChosenPanel();
		};

		this.setLayout(new GridBagLayout());
		setInputPanel();
		confirm.addActionListener(buttonListener);

	}

	/**
	 * Gets the value of the color input.
	 *
	 * @return color
	 */
	public MunsellColor getColor()
	{

		return this.color;

	}

	/**
	 * Sets the color of the output ColorPanel.
	 */
	private void setColorChosenPanel()
	{
		this.setVisible(false);
		this.remove(colorChosen);
		colorChosen = new ColorPanel(color);
		this.add(colorChosen, colorChosenCon);
		this.setVisible(true);

	}

	/**
	 * Sets the hue, value, and chroma to the colors in the input boxes.
	 */
	private void setHVC()
	{

		this.confirmListener.actionPerformed(new ActionEvent(confirm, 0, "Confirm"));

	}

	/**
	 * Adds the components to the input panel.
	 */
	private void setInputPanel()
	{

		GridBagConstraints con = new GridBagConstraints();

		con.gridx = 0;

		con.gridy = 0;
		con.ipadx = 10;
		con.ipady = 0;
		con.weightx = .2;
		con.weighty = .2;
		this.add(new JLabel("Hue prefix: "), con);
		con.gridy = 1;
		this.add(new JLabel("Hue suffix: "), con);

		con.gridx = 1;
		con.gridy = 0;
		con.fill = GridBagConstraints.HORIZONTAL;

		this.add(huePrefixInput, con);
		con.gridy = 1;
		this.add(hueSuffixInput, con);
		con.gridy = 2;

		this.add(colorChosen, colorChosenCon);

		con.gridy = 5;
		con.gridx = 0;
		con.fill = GridBagConstraints.NONE;
		con.gridwidth = 2;
		con.anchor = GridBagConstraints.CENTER;
		this.add(confirm, con);

	}

}
