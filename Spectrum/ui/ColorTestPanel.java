import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.xml.soap.Node;

/**
 * The panel for the Color Test.
 *
 * @author Mohammed Al-Harazi
 *
 */
public class ColorTestPanel extends JPanel
{

	static Node head;
	
	private static ColorPanel c1;
	
	private static ColorPanel c10;
	
	private static ColorPanel[] panelArray = new ColorPanel[8];

	private static JPanel secondPanel;
	
	private static final long serialVersionUID = 1L;
	
	private static final double HUE_STEP = 2.5;

	private JButton confirm, done;

	private JPanel introPanel;

	private ColorPanel c2;

	private ColorPanel c3;

	private ColorPanel c4;

	private ColorPanel c5;

	private ColorPanel c6;

	private ColorPanel c7;

	private ColorPanel c8;

	private ColorPanel c9;




	private int randomHue, score;

	private JSplitPane s1;


	/**
	 * Default Constructor.
	 */
	public ColorTestPanel()
	{

		setUpTest();

		// Creates the initial panels after the confirm button has been selected
		confirm.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				secondPanel.removeAll();
				secondPanel.updateUI();

				secondPanel.setLayout(new GridLayout(1, 10, 5, 5));
				secondPanel.setPreferredSize(new Dimension(150, 150));
				secondPanel.setMaximumSize(new Dimension(150, 150));
				secondPanel.setMinimumSize(new Dimension(150, 150));

				randomHue = (int) (Math.random() * 80 + 1);

				c1 = new ColorPanel(new MunsellColor(randomHue, 5, 6), false);
				c2 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP, 5, 6), false);
				c3 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 2, 5, 6), false);
				c4 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 3, 5, 6), false);
				c5 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 4, 5, 6), false);
				c6 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 5, 5, 6), false);
				c7 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 6, 5, 6), false);
				c8 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 7, 5, 6), false);
				c9 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 8, 5, 6), false);
				c10 = new ColorPanel(new MunsellColor(randomHue + HUE_STEP * 9, 5, 6), false);

				panelArray[0] = c2;
				panelArray[1] = c3;
				panelArray[2] = c4;
				panelArray[3] = c5;
				panelArray[4] = c6;
				panelArray[5] = c7;
				panelArray[6] = c8;
				panelArray[7] = c9;

				Collections.shuffle(Arrays.asList(panelArray));

				secondPanel.add(c1);
				for (int i = 0; i < 8; i++)
				{
					panelArray[i].add(new JLabel((i + 1) + ""));
					secondPanel.add(panelArray[i]);
				}

				secondPanel.add(c10);

				confirm.setVisible(false);
				secondPanel.setVisible(true);

				introPanel.add(done, BorderLayout.SOUTH);

			}
		});

		done = new JButton("Done");

		done.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				score = 0;

				if (panelArray[0].getColor().equals(c2.getColor()))
				{
					score += 1;
				}
				if (panelArray[1].getColor().equals(c3.getColor()))
				{
					score += 1;
				}
				if (panelArray[2].getColor().equals(c4.getColor()))
				{
					score += 1;
				}
				if (panelArray[3].getColor().equals(c5.getColor()))
				{
					score += 1;
				}
				if (panelArray[4].getColor().equals(c6.getColor()))
				{
					score += 1;
				}
				if (panelArray[5].getColor().equals(c7.getColor()))
				{
					score += 1;
				}
				if (panelArray[6].getColor().equals(c8.getColor()))
				{
					score += 1;
				}
				if (panelArray[7].getColor().equals(c9.getColor()))
				{
					score += 1;
				}

				JOptionPane.showMessageDialog(null, "You're score is: %" + score / 8.0 * 100);
			}
		});

		JLabel title = new JLabel("Hue Color Test");
		title.setSize(10, 10);
		this.add(title, BorderLayout.NORTH);

		this.add(s1);
		this.setVisible(true);

	}

	/**
	 * Method to set up the Color Test in the panel.
	 */
	public void setUpTest()
	{

		this.removeAll();
		this.revalidate();
		this.repaint();
		this.setLayout(new BorderLayout());
		
		ActionListener buttonListener = e -> {
			
			JButton source = (JButton) e.getSource();
			TwoNumberInputPanel panel = (TwoNumberInputPanel) source.getParent();
			
			switchColors(panel.getNum1(), panel.getNum2());
		};
			
		
		TwoNumberInputPanel inputPanel = new TwoNumberInputPanel(buttonListener);

		this.add(inputPanel, BorderLayout.WEST);

		introPanel = new JPanel();
		secondPanel = new JPanel();
		secondPanel.setVisible(false);
		JLabel intro = new JLabel("Hue Color Test", SwingConstants.CENTER);

		introPanel.setLayout(new BorderLayout());

		confirm = new JButton("Confirm");

		introPanel.add(confirm, BorderLayout.SOUTH);
		introPanel.add(intro, BorderLayout.NORTH);
		introPanel.add(new JLabel("<html><p>This is a hue color test designed to test your "
				+ "sensitivity to different colors. There will be 10 different colors in the bottom"
				+ " panel that you will need to put in order by Hue. The first and last colors will"
				+ " be fixed, but you will be need to move the other colors using the text box that"
				+ " will be available in this panel once the test begins. Once you press confirm, "
				+ "slide the bottom panel up and the 10 colors will appear.</p></html>"), 
				BorderLayout.CENTER);
		introPanel.setPreferredSize(new Dimension(50, 50));
		Font f = intro.getFont();
		intro.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

		s1 = new JSplitPane(SwingConstants.HORIZONTAL, introPanel, secondPanel);

		confirm.setVisible(true);

	}
	
	/**
	 * Returns the current panel array.
	 * @return The panel array
	 */
	public static JPanel[] getPanelArray()
	{
		return panelArray;
	}

	/**
	 * Method used to switch the colors in the second color test panel.
	 * @param idx1 The first index to swap
	 * @param idx2 The second index to swap
	 */
	public void switchColors(int idx1, int idx2)
	{
		
		swap(idx1 - 1, idx2 - 1);
		this.setVisible(false);
		this.remove(s1);
		
		secondPanel.add(c1);
		for (int i = 0; i < 8; i++)
		{
			panelArray[i].removeAll();
			panelArray[i].add(new JLabel((i + 1) + ""));
			secondPanel.add(panelArray[i]);
		}

		secondPanel.add(c10);
		
		s1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, introPanel, secondPanel);
		this.add(s1);
		
		this.setVisible(true);
		
	}
	
	/**
	 * Method to swap elements in the panelArray.
	 * @param idx1 the first index to swap
	 * @param idx2 the second index to swap
	 */
	public void swap(int idx1, int idx2)
	{
		
		ColorPanel temp = panelArray[idx1];
		panelArray[idx1] = panelArray[idx2];
		panelArray[idx2] = temp;
		
	}
}
