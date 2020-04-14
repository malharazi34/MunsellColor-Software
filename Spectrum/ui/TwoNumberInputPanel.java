import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel for inputting two numbers, used in the color tests.
 * @author Ryan McCubbin
 *
 */
public class TwoNumberInputPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	private static int num1;
	private static int num2;
	
	private JTextField num1Input;
	private JTextField num2Input;
	
	private JButton confirm;
	
	/**
	 * Listener constructor.
	 * 
	 * @param confirmListener The listener to add to the confirm button
	 */
	public TwoNumberInputPanel(ActionListener confirmListener)
	{
		
		confirm = new JButton("Confirm");
		confirm.addActionListener(confirmListener);
		
		num1 = 0;
		num2 = 0;
		num1Input = new JTextField();
		num2Input = new JTextField();
		
		InputVerifier numRange18Verifier = new InputVerifier()
		{
			
			@Override
			public boolean shouldYieldFocus(JComponent input)
			{
				boolean valid = verify(input);

				if (valid) // If there is a bad input, turn the button off until it is good
				{
					confirm.setEnabled(true);
					if (input == num1Input)
					{
						num1 = Integer.parseInt(((JTextField)input).getText());
					} else
					{
						num2 = Integer.parseInt(((JTextField)input).getText());
					}
					
				} else
				{
					confirm.setEnabled(false);
				}

				return valid;
			}
			
			@Override
			public boolean verify(JComponent arg0)
			{
				
				JTextField input = (JTextField) arg0;
				
				String str = input.getText();
				
				boolean valid = true;
				
				try
				{
					
					int num = Integer.parseInt(str);
					
					if (num < 1 || num > 8)
					{
						
						throw new NumberFormatException();
						
					}
					
				} catch (NumberFormatException e)
				{
					
					valid = false;
					
				}
				
				
				
				return valid;
			}
			
			
			
		};
		
		this.setLayout(new GridBagLayout());
		
		num1Input.setInputVerifier(numRange18Verifier);
		num2Input.setInputVerifier(numRange18Verifier);
		
		setInputPanel();
		
		
	}
	
	/**
	 * Sets up all components on this panel.
	 */
	private void setInputPanel()
	{
		
		GridBagConstraints con = new GridBagConstraints();
		
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		con.weightx = 1;
		con.weighty = .2;
		con.insets = new Insets(5, 5, 5, 5);
		con.anchor = GridBagConstraints.CENTER;
		
		JLabel topLabel = new JLabel("Switch");
		
		this.add(topLabel, con);
		
		con.gridy = 1;
		con.weighty = 1;
		con.weightx = .5;
		con.gridwidth = 1;
		
		JLabel index1Label = new JLabel("Index 1: ");
		
		this.add(index1Label, con);
		
		con.gridx = 1;
		con.weightx = 1;
		con.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(num1Input, con);
		
		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 2;
		con.weighty = .2;
		con.fill = GridBagConstraints.NONE;
		
		JLabel middleLabel = new JLabel("With");
		
		this.add(middleLabel, con);
		
		con.weighty = 1;
		con.gridy = 3;
		con.weightx = .5;
		con.gridwidth = 1;
		
		JLabel index2Label = new JLabel("Index 2: ");
		
		this.add(index2Label, con);
		
		con.gridx = 1;
		con.weightx = 1;
		con.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(num2Input, con);
		
		con.fill = GridBagConstraints.NONE;
		con.gridy = 4;
		con.gridx = 0;
		con.gridwidth = 2;
		
		this.add(confirm, con);
		
		
		
	}
	
	/**
	 * Getter for num1.
	 * @return num1
	 */
	public int getNum1()
	{
		
		return num1;
		
	}
	
	/**
	 * Getter for num2.
	 * @return num2
	 */
	public int getNum2()
	{
		
		return num2;
		
	}
	
}
