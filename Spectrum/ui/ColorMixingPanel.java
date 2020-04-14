import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;

/**
 * The panel for the color mixing function.
 *
 * @author Ryan McCubbin
 *
 */
public class ColorMixingPanel extends JSplitPane implements PaletteUser
{

	private static final long serialVersionUID = 1L;

	private JButton mix; // The button to mix the colors
	private ColorPanel outputPanel; // This panel will display the output

	private ArrayList<MunsellColor> palette; // The palette
	private JPanel palettePanel; // This panel will display the inputted palette

	// A hashmap of colors to spinners, which hold the weights of each color
	private HashMap<MunsellColor, JSpinner> spinners;

	/**
	 * Default constructor.
	 */
	public ColorMixingPanel()
	{

		// Initialize the panels to be shown for this function.
		palettePanel = new JPanel();
		outputPanel = new ColorPanel(new MunsellColor(0, 0, 0));

		palettePanel.setLayout(new GridBagLayout());
		palette = PalettePanel.getPalette();

		spinners = new HashMap<>();

		// Set up buttons

		mix = new JButton("Mix Colors");
		ActionListener mixListener = e -> mixColors();

		mix.addActionListener(mixListener);

		// Initialize the palette
		setUpPalettePanel();

		// Add the display panels
//		this.add(palettePanel, BorderLayout.CENTER);
//		this.add(outputPanel, BorderLayout.EAST);
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setDividerLocation(900);
		this.setLeftComponent(palettePanel);
		this.setRightComponent(outputPanel);

	}

	/**
	 * Mixes the colors based on the chosen weights.
	 */
	private void mixColors()
	{

		int weight1 = 0;
		int weight2 = 0;
		MunsellColor c1 = null;
		MunsellColor c2 = null;

		for (MunsellColor c : palette)
		{
			Integer spinnerInput;
			try
			{
				spinnerInput = (Integer) spinners.get(c).getValue();
			} catch (NullPointerException e)
			{
				break;
			}
			if (spinnerInput != 0)
			{

				if (c1 == null)
				{

					weight1 = spinnerInput;
					c1 = c;

				} else if (c2 == null)
				{

					weight2 = spinnerInput;
					c2 = c;

					MunsellColor result = MunsellColor.mixColors(c1, c2, weight1, weight2);
					c1 = result;
					c2 = null;
					weight1 = 1;

				}

			}

		}

		if (c1 != null)
		{
			this.setVisible(false);
			this.remove(outputPanel);
			outputPanel = new ColorPanel(c1);
			this.setRightComponent(outputPanel);
			this.setDividerLocation(900);
			this.setVisible(true);
		}
	}
	
	@Override
	public void setPalette() 
	{
		
		palette = PalettePanel.getPalette();
		setUpPalettePanel();
		
	}
	
	/**
	 * Sets up the input panel for mixing colors based on a previously chosen
	 * palette.
	 */
	private void setUpPalettePanel()
	{
		// Keep track of current grid position
		int curX = 0;
		int curY = 0;
		int maxX = 10;
		boolean fullRow = false;

		palettePanel.setVisible(false);
		palettePanel.removeAll();
		GridBagConstraints con = new GridBagConstraints();
		// For each palette color, add a new ColorPanel and spinner to the palettePanel
		for (MunsellColor c : palette)
		{

			con.gridx = curX++;
			con.gridy = curY;
			con.weightx = 1;
			con.weighty = 1;
			con.gridwidth = 1;
			con.gridheight = 1;
			con.insets = new Insets(5, 5, 5, 5);
			con.anchor = GridBagConstraints.CENTER;
			con.fill = GridBagConstraints.BOTH;

			ColorPanel cPanel = new ColorPanel(c);
			palettePanel.add(cPanel, con);

			con.gridx = curX++;
			con.weightx = .1;
			con.fill = GridBagConstraints.NONE;

			JSpinner spin = new JSpinner();
			SpinnerNumberModel spinModel = new SpinnerNumberModel(0, 0, 10, 1);
			spin.setModel(spinModel);

			palettePanel.add(spin, con);

			spinners.put(c, spin);

			if (curX >= maxX)
			{

				fullRow = true;
				curX = 0;
				curY++;

			}

		}

		JPanel buttonPanel = new JPanel();

		con.gridy++;
		con.gridx = 0;
		con.gridwidth = fullRow ? maxX : curX;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.NONE;
		con.weightx = .1;
		con.weighty = 0.1;
		buttonPanel.add(mix);

		palettePanel.add(buttonPanel, con);

		palettePanel.setVisible(true);

	}

}
