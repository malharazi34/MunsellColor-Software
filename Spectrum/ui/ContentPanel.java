import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The Content Pane for the main JFrame of the GUI.
 *
 * @author Ryan McCubbin, Gianna Casolara
 *
 */
public class ContentPanel extends JPanel
{

	public static final String COLOR_MIX_PANEL_NAME = "Color Mixing";
	public static final String COLOR_TEST_PANEL_NAME = "Color Test";
	public static final String HARMONIOUS_PANEL_NAME = "Harmonious Colors";
	public static final String HUE_PANEL_NAME = "Munsell Hues";
	public static final String IMAGE_PANEL_NAME = "Image Analysis";
	public static final String PALETTE_PANEL_NAME = "Palette";
	public static final String VALUE_PANEL_NAME = "Munsell Values";
	public static final String MIX_ANALYSIS_PANEL_NAME = "Mix Analysis";
	
	private static final long serialVersionUID = 1L;

	private static PalettePanel currentPalettePanel;
	
	private static ColorTestPanel currentTestPanel;

	private CardLayout centerLayout;

	private JPanel centerPanel;

	private BorderLayout layout = new BorderLayout();

	

	/**
	 * Default Constructor.
	 */
	public ContentPanel()
	{

		this.setLayout(layout);
		setButtonPanel();
		setCenterPanel();

	}

	/**
	 * Sets the North Panel to the button panel, used for navigating between
	 * functions.
	 */
	private void setButtonPanel()
	{

		// The northern panel of the content pane will be the button panel, used
		// to navigate between the different functions.
		JPanel buttonPanel = new JPanel();

		// Create one button for each function interface. More buttons and name
		// constants should be created as we add more functions
		JButton valueButton = new JButton(VALUE_PANEL_NAME);
		JButton harmoniousButton = new JButton(HARMONIOUS_PANEL_NAME);
		JButton hueButton = new JButton(HUE_PANEL_NAME);
		JButton mixButton = new JButton(COLOR_MIX_PANEL_NAME);
		JButton paletteButton = new JButton(PALETTE_PANEL_NAME);
		JButton imageButton = new JButton(IMAGE_PANEL_NAME);
		JButton colorTestButton = new JButton(COLOR_TEST_PANEL_NAME);
		JButton mixAnalysisButton = new JButton(MIX_ANALYSIS_PANEL_NAME);

		// Add each button to the button panel
		buttonPanel.add(valueButton);
		buttonPanel.add(hueButton);
		buttonPanel.add(harmoniousButton);
		buttonPanel.add(mixButton);
		buttonPanel.add(paletteButton);
		buttonPanel.add(imageButton);
		buttonPanel.add(colorTestButton);
		buttonPanel.add(mixAnalysisButton);

		// The ActionCommand for each event will be the name of the button pressed.
		// Combine this
		// with the fact that the same names are used to add the function panels to the
		// CardLayout
		// in the center pane, and we can use the getActionCommand() method here to
		// refer to the
		// panel we want to select instead of creating a new listener for every panel.
		ActionListener listener = e -> centerLayout.show(centerPanel, e.getActionCommand());

		// Give each button this listener
		valueButton.addActionListener(listener);
		harmoniousButton.addActionListener(listener);
		hueButton.addActionListener(listener);
		mixButton.addActionListener(listener);
		paletteButton.addActionListener(listener);
		imageButton.addActionListener(listener);
		colorTestButton.addActionListener(listener);
		mixAnalysisButton.addActionListener(listener);

		// Add the button panel to the north of the content panel
		this.add(buttonPanel, BorderLayout.NORTH);
	}

	/**
	 * Sets the Center Panel, where the content will actually be placed. This is
	 * gonna be a little complicated, but hopefully it will work out.
	 */
	private void setCenterPanel()
	{

		// The center panel is going to be where all the functions are actually
		// implemented. It will
		// be a CardLayout, so that only one function shows at a time.
		centerLayout = new CardLayout();
		centerPanel = new JPanel(centerLayout);

		// There should be a different class for the panel for each function, to make
		// sure functions
		// are kept separate from one another. Here, we create a single panel of each
		// type.
		ValuePanel valuePanel = new ValuePanel();
		HarmoniousPanel harmoniousPanel = new HarmoniousPanel();
		HuePanel huePanel = new HuePanel();
		PalettePanel palettePanel = new PalettePanel();
		ColorMixingPanel mixingPanel = new ColorMixingPanel();
		ImageAnalysisPanel imageAnalysisPanel = new ImageAnalysisPanel();
		ColorTestPanel colorTestPanel = new ColorTestPanel();
		MixAnalysisPanel mixAnalysisPanel = new MixAnalysisPanel();
		
		//Set a focus listener for any panel that uses the palette
		ComponentListener compListener = new ComponentListener()
		{

			@Override
			public void componentHidden(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent arg0)
			{
				PaletteUser user = (PaletteUser) arg0.getSource();
				user.setPalette();
				
			}
			
			
			
		};
		
		mixingPanel.addComponentListener(compListener);
		mixAnalysisPanel.addComponentListener(compListener);
		
		currentPalettePanel = palettePanel;
		currentTestPanel = colorTestPanel;

		// To make use of the single listener above, we use this to assign the name of
		// each panel to
		// that of the buttons in the northern area.
		centerLayout.addLayoutComponent(valuePanel, VALUE_PANEL_NAME);
		centerLayout.addLayoutComponent(harmoniousPanel, HARMONIOUS_PANEL_NAME);
		centerLayout.addLayoutComponent(huePanel, HUE_PANEL_NAME);
		centerLayout.addLayoutComponent(mixingPanel, COLOR_MIX_PANEL_NAME);
		centerLayout.addLayoutComponent(palettePanel, PALETTE_PANEL_NAME);
		centerLayout.addLayoutComponent(imageAnalysisPanel, IMAGE_PANEL_NAME);
		centerLayout.addLayoutComponent(colorTestPanel, COLOR_TEST_PANEL_NAME);
		centerLayout.addLayoutComponent(mixAnalysisPanel, MIX_ANALYSIS_PANEL_NAME);

		// Add each function panel to the center panel, so that the CardLayout is
		// enforced
		centerPanel.add(valuePanel);
		centerPanel.add(harmoniousPanel);
		centerPanel.add(huePanel);
		centerPanel.add(mixingPanel);
		centerPanel.add(palettePanel);
		centerPanel.add(imageAnalysisPanel);
		centerPanel.add(colorTestPanel);
		centerPanel.add(mixAnalysisPanel);

		// Feel free to change the coloring.
		valuePanel.setBackground(Color.WHITE);
		harmoniousPanel.setBackground(Color.WHITE);
		huePanel.setBackground(Color.WHITE);
		mixingPanel.setBackground(Color.WHITE);
		palettePanel.setBackground(Color.WHITE);
		colorTestPanel.setBackground(Color.WHITE);
		mixAnalysisPanel.setBackground(Color.WHITE);

		// Add the center panel to the center of the content pane
		this.add(centerPanel, BorderLayout.CENTER);

	}

	/**
	 * Gets the palette panel on this instance of Spectrum, so that other panels can access the
	 * palette.
	 * @return The current palette panel
	 */
	public static PalettePanel getPalettePanel()
	{

		return currentPalettePanel;

	}
	
	/**
	 * Gets the current ColorTestPanel.
	 * @return currentTestPanel
	 */
	public static ColorTestPanel getColorTestPanel()
	{
		return currentTestPanel;
	}

}
