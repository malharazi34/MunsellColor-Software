import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Handles adding and removing colors from the user's palette.
 *
 * @author Ryan McCubbin
 *
 */
public class PalettePanel extends JPanel
{

	private static ArrayList<MunsellColor> palette;

	private static final long serialVersionUID = 1L;

	

	private JPanel centerPanel;

	private int curX = 0;

	private int curY = 0;

	private ColorInputPanel input;
	private int maxPaletteSize = 20;

	private HashMap<JButton, ColorPanel> removeButtons;

	private ActionListener removeListener;

	private int width = 5;

	/**
	 * Default constructor.
	 */
	public PalettePanel()
	{

		this.setLayout(new BorderLayout());

		removeButtons = new HashMap<>();

		ActionListener listener = e -> addToPalette(input.getColor());
		removeListener = e -> {

			JButton source = (JButton) e.getSource();
			ColorPanel cPanel = removeButtons.get(source);

			palette.remove(cPanel.getColor());

			ArrayList<MunsellColor> newPalette = new ArrayList<>(palette);

			palette.clear();

			this.setVisible(false);

			this.remove(centerPanel);
			centerPanel = new JPanel(new GridBagLayout());
			curX = 0;
			curY = 0;
			for (MunsellColor c : newPalette)
			{
				addToPalette(c);
			}
			this.add(centerPanel);

			this.setVisible(true);

		};

		palette = new ArrayList<>(maxPaletteSize);

		input = new ColorInputPanel(listener);
		input.setButtonName("Add To Palette");

		centerPanel = new JPanel(new GridBagLayout());

		this.add(input, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);

	}
	
	/**
	 * Gets the palette colors.
	 *
	 * @return The list of colors that is this palette
	 */
	public static ArrayList<MunsellColor> getPalette()
	{

		return palette;

	}
	
	/**
	 * Adds the color in the ColorInputPanel to the palette.
	 *
	 * @param m The color to add to the palette
	 */
	public void addToPalette(MunsellColor m)
	{
		this.setVisible(false);

		if (!palette.contains(m) && palette.size() < maxPaletteSize)
		{
			palette.add(m);
			GridBagConstraints con = new GridBagConstraints();
			con.weightx = .01;
			con.weighty = .2;
			con.gridwidth = 1;
			con.gridheight = 1;

			con.gridx = curX;
			con.gridy = curY;

			if (++curX >= width)
			{

				curX = 0;
				curY += 2;

			}

			con.insets = new Insets(5, 5, 5, 5);
			con.fill = GridBagConstraints.BOTH;

			ColorPanel cPanel = new ColorPanel(m);

			centerPanel.add(cPanel, con);

			con.gridy++;
			con.weighty = .03;
			con.insets = new Insets(0, 0, 0, 0);
			con.fill = GridBagConstraints.NONE;

			JButton remove = new JButton("Remove");
			remove.addActionListener(removeListener);
			removeButtons.put(remove, cPanel);
			centerPanel.add(remove, con);

		}
		this.setVisible(true);

	}

}
