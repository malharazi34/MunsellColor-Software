import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A panel for determining the colors in a palette that mix to become a desired color.
 * @author Ryan McCubbin
 *
 */
public class MixAnalysisPanel extends JPanel implements PaletteUser
{
	
	public static final double HUE_TOL = .1;
	public static final double CHROMA_TOL = .1;
	public static final double VALUE_TOL = .1;
	public static final int MAX_WEIGHT = 10;
	
	private static final long serialVersionUID = 1L;
	
	private ColorInputPanel colorInput;
	
	private MunsellColor target;
	
	private ArrayList<MunsellColor> palette;
	private JPanel palettePanel;
	
	private HashMap<MunsellColor, Integer> weights;

	/**
	 * Default constructor.
	 */
	public MixAnalysisPanel() 
	{
		
		target = null;
		this.setLayout(new BorderLayout());
		colorInput = new ColorInputPanel(e -> 
		{ 
			target = colorInput.getColor();
			for (MunsellColor c : palette)
			{
				
				weights.put(c, 0);
				
			}
			calculateWeights();
			setUpPalettePanel();
		});
		
		palette = PalettePanel.getPalette();
		palettePanel = new JPanel(new GridBagLayout());
		
		JPanel northPanel = new JPanel(new GridLayout(2, 0));
		
		JLabel disclaimer1 = new JLabel("This tab will attempt to find combinations of colors on "
				+ "your palette that mix to create a color you specify. Note that it will not be "
				+ "able to find a mix of more than three colors, and will not allow a ", 
				SwingConstants.CENTER);
		JLabel disclaimer2 = new JLabel("color weight \n greater than 10. It can have difficulty "
				+ "finding some mixes that do exist, so not every possible color mix can be found "
				+ "using this utility.", SwingConstants.CENTER);
		northPanel.add(disclaimer2, 1, 0);
		northPanel.add(disclaimer1, 0, 0);
		
		
		weights = new HashMap<>();
		
		setUpPalettePanel();
		
		this.add(colorInput, BorderLayout.WEST);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(palettePanel, BorderLayout.CENTER);
		
	}
	
	@Override
	public void setPalette() 
	{
		
		
		palette = PalettePanel.getPalette();
		for (MunsellColor c : palette) 
		{
			
			weights.put(c, 0);
			
		}
		setUpPalettePanel();
		
	}
	
	/**
	 * Sets up the palette panel in the middle of the tab, where the colors that one would need
	 * to mix along with their weights will be displayed.
	 */
	private void setUpPalettePanel() 
	{
		
		palettePanel.setVisible(false);
		palettePanel.removeAll();
		
		for (int y = 0; y < 4; y++) 
		{
			
			for (int x = 0; x < 10; x += 2) 
			{
				
				int idx = y * 5 + x/2;
				MunsellColor m;
				try 
				{
					m = palette.get(idx);
				} catch (IndexOutOfBoundsException e)
				{
					break;
				}
				
				ColorPanel cPanel = new ColorPanel(m);
				
				GridBagConstraints con = new GridBagConstraints();
				
				con.gridx = x;
				con.gridy = y;
				con.weightx = 1;
				con.weighty = 1;
				con.gridheight = 1;
				con.gridwidth = 1;
				con.insets = new Insets(5, 5, 5, 5);
				con.anchor = GridBagConstraints.CENTER;
				con.fill = GridBagConstraints.BOTH;
				
				palettePanel.add(cPanel, con);
				
				JLabel cLabel = new JLabel(weights.get(m).toString());
				
				con.gridx = x + 1;
				con.weightx = .1;
				con.fill = GridBagConstraints.NONE;
				
				palettePanel.add(cLabel, con);
				
				
			}
			
		}
		
		palettePanel.setVisible(true);
		
	}
	
	/**
	 * Calculates the weights that get the desired color from the palette colors given, within a 
	 * certain tolerance (currently within one MunsellToRGB table entry, so +- 1.25 in hue, 1 in
	 * chroma, and .5 in value).
	 */
	private void calculateWeights()
	{
		
		int paletteSize = palette.size();
		
		
		
		//The maximum possible distance from the target color is the first comparison distance
		double lastDistance = Double.MAX_VALUE;
		
		//A flag if a mix has been found for the target color
		boolean mixFound = false;
		
		//If the color being searched for is one of the ones on the palette, return that
		for (MunsellColor c : palette)
		{
			
			if (c.equals(target, HUE_TOL, VALUE_TOL, CHROMA_TOL))
			{
				
				weights.put(c, 1);
				mixFound = true;
				
			}
			
		}
		
		//If there are at least two colors to work with and the color is not one on the palette
		if (paletteSize >= 2 && !mixFound)
		{
			
			//For each possible non-zero weight of the first color
			for (int w = 0; w <= MAX_WEIGHT && !mixFound; w++)
			{
				weights.put(palette.get(0), w);
				
				
				//Calculate the current mix, which should just be the first color, and make that
				//the starting distance
				MunsellColor mix = calculateCurrentMix();
				if (mix != null 
						&& mix.equals(target, HUE_TOL, VALUE_TOL, CHROMA_TOL)) mixFound = true;
				
				for (int i = 1; i < paletteSize && !mixFound; i++) 
				{
					
					weights.put(palette.get(i), 0);
					
				}
				
				mix = calculateCurrentMix();
				
				if (mix != null) lastDistance = MunsellColor.distance(mix, target);
				
				//For each other color in the palette
				for (int i = 1; i < paletteSize && !mixFound; i++)
				{
					
					//The current color to work with
					MunsellColor mColor = palette.get(i);
					
					//Flag for the optimal weight being found
					boolean weightFound = false;
					
					//For each possible weight in the current color
					for (int j = 0; j <= MAX_WEIGHT && !mixFound && !weightFound; j++)
					{
						
						//Add that weight
						weights.put(mColor, j);
						
						//Calculate the current color defined by weights
						MunsellColor currentMix = calculateCurrentMix();
						
						//Calculate the distance from that color to the target
						double distance;
						if (currentMix != null) distance = MunsellColor.distance(target, 
								currentMix);
						else distance = lastDistance;
						
						//If the current mix is within the tolerances defined of the color
						if (currentMix != null 
								&& currentMix.equals(target, HUE_TOL, VALUE_TOL, CHROMA_TOL))
						{
							
							mixFound = true;
							
						} 
						//Else if the distance calculated is NOT better than the last one, the last
						//distance will be the optimal one
						else if (distance > lastDistance)
						{
							
							weightFound = true;
							weights.put(mColor, j - 1);
							
						} else 
						{
							
							lastDistance = distance;
							
						}
						
						
						
					}
					
				}
				
			}
			
			if (!mixFound) 
			{
				
				weights.put(palette.get(0), 0);
				MunsellColor mix = calculateCurrentMix();
				if (mix != null && mix.equals(target, HUE_TOL, VALUE_TOL, CHROMA_TOL)) 
					mixFound = true;
				else JOptionPane.showMessageDialog(this, "No mix could be found for this color.");
				
				
				
			}
			
		} else if (!mixFound)
		{
			
			JOptionPane.showMessageDialog(this, "Please add more than one color to your palette.");
			
		}
		
	}
	
	/**
	 * A helper method for calculateWeights(). Calculates the current color that would be acquired
	 * by mixing all colors in the HashMap with their respective weights.
	 * @return The color that would be acquired
	 */
	private MunsellColor calculateCurrentMix()
	{
		int weight1 = 0;
		int weight2 = 0;
		MunsellColor c1 = null;
		MunsellColor c2 = null;

		for (MunsellColor c : palette)
		{
			Integer weight;
			try
			{
				weight = weights.get(c);
			} catch (NullPointerException e)
			{
				break;
			}
			if (weight != 0)
			{

				if (c1 == null)
				{

					weight1 = weight;
					c1 = c;

				} else if (c2 == null)
				{

					weight2 = weight;
					c2 = c;

					MunsellColor result = MunsellColor.mixColors(c1, c2, weight1, weight2);
					c1 = result;
					c2 = null;
					weight1 = 1;

				}

			}

		}
		return c1;
		
	}
	
}
