import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class representing a rectangle of a certain color along with that color's
 * label on it.
 *
 * @author Ryan McCubbin
 *
 */
public class ColorPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private MunsellColor color;
	
	private boolean showLabel;

	private JLabel label;

	/**
	 * The boolean constructor for JPanel.
	 *
	 * @param arg0 Whether the panel is double buffered
	 * @param c    The color to diplay
	 */
	public ColorPanel(boolean arg0, MunsellColor c)
	{
		super(arg0);
		color = c;
		makePanel();
		showLabel = true;
	}

	/**
	 * The explicit value constructor.
	 *
	 * @param arg0 The LayoutManager
	 * @param arg1 double buffered
	 * @param c    The color to display
	 */
	public ColorPanel(LayoutManager arg0, boolean arg1, MunsellColor c)
	{
		super(arg0, arg1);
		color = c;
		makePanel();
		showLabel = true;
	}

	/**
	 * The LayoutManager constructor for JPanel.
	 *
	 * @param arg0 The LayoutManager to use in this panel
	 * @param c    The color to display
	 */
	public ColorPanel(LayoutManager arg0, MunsellColor c)
	{
		super(arg0);
		color = c;
		makePanel();
		showLabel = true;
	}

	/**
	 * Default constructor for JPanel.
	 *
	 * @param c The color to display
	 */
	public ColorPanel(MunsellColor c)
	{
		this.setLayout(new GridBagLayout());
		color = c;
		showLabel = true;
		makePanel();

	}
	
	/**
	 * Constructor with boolean for showing label.
	 * 
	 * @param c The color to display
	 * @param showLabel whether to show the label or not
	 */
	public ColorPanel(MunsellColor c, boolean showLabel)
	{
		
		this.showLabel = showLabel;
		color = c;
		this.setLayout(new GridBagLayout());
		makePanel();
		
	}

	/**
	 * Gets the color on this panel.
	 *
	 * @return The color on this panel
	 */
	public MunsellColor getColor()
	{

		return color;

	}

	/**
	 * Sets the panel's color and adds the JLabel to it.
	 */
	private void makePanel()
	{
		Color rgb = ColorTable.munsellToRGB(color);
		this.setBackground(rgb);
		label = new JLabel(color.toString());
		label.setBackground(rgb);

		GridBagConstraints con = new GridBagConstraints();
		con.anchor = GridBagConstraints.CENTER;

		if (color.getValue() < 5)
		{
			label.setForeground(new Color(255, 255, 255));
		}
		
		if (showLabel) this.add(label, con);


	}

	/**
	 * Sets the font size for the label on this panel.
	 *
	 * @param size The point size to set the font to
	 */
	public void setFontSize(int size)
	{

		label.setFont(label.getFont().deriveFont((float) size));

	}

	/**
	 * Sets the text color of the label on this panel.
	 *
	 * @param c The color to set the text to
	 */
	public void setTextColor(Color c)
	{

		label.setForeground(c);

	}

}
