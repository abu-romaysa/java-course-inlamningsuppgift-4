package saldao8;

/**
 * This class implements the Dialog GUI for doing transactions
 * 
 * @author Salim Daoud, saldao-8
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TransactionDialog extends JDialog implements ActionListener
{
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;

    private JTextField amountTextField = new JTextField(10);
    private JTextField accountIdTextField = new JTextField(10);
    JButton actionButton = new JButton("Deposit");
    JButton abortButton = new JButton("Abort");
    
    private OverviewLogicWin overviewLogicWin;
    private int accountId;
    private String personalIdentityNumber;
    private int action;

    /**
     * Constructor
     * 
     * @param action - action to perform (deposit/withdraw)
     * @param overviewLogicWin - parent and owner frame
     * @param personalIdentityNumber - new customer's personal identity number
     * @param accountId - the account ID in question
     */
    public TransactionDialog(int action, OverviewLogicWin overviewLogicWin, String personalIdentityNumber, int accountId)
    {
        // calls the parents constructor i.e. JDialog
        super(overviewLogicWin, "Deposit", true);
        
        this.overviewLogicWin = overviewLogicWin;
        this.accountId = accountId;
        this.personalIdentityNumber = personalIdentityNumber; 
        this.action = action;
        
        // set title accordingly to action
        if(action == overviewLogicWin.WITHDRAW)
        {
            setTitle("Withdraw");
            actionButton.setText("Withdraw");
        }
        
        createWindowContent();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Builds the window's GUI
     */
    private void createWindowContent()
    {
        // build input section
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
      
        JLabel amountLabel = new JLabel("Amount: ");
        JLabel accountIdLabel = new JLabel("Account ID: ");
        inputPanel.setLayout(new GridLayout(2,  2));
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);
        inputPanel.add(accountIdLabel);
        inputPanel.add(accountIdTextField);
        accountIdTextField.setEditable(false);
        accountIdTextField.setText(Integer.toString(accountId));
        this.add(inputPanel);
        
        // build button's section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.add(actionButton);
        buttonPanel.add(abortButton);
        abortButton.addActionListener(this);
        actionButton.addActionListener(this);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == actionButton)
        {
            try
            {
                double amount = Double.parseDouble(amountTextField.getText());
                
                if(action == overviewLogicWin.DEPOSIT)
                {
                    if(overviewLogicWin.deposit(personalIdentityNumber, accountId, amount))
                    {
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Transaction could not be performed!", "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if(overviewLogicWin.withdraw(personalIdentityNumber, accountId, amount))
                    {
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Transaction could not be performed!", "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                } 
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null, "Please insert only numbers!", "Alert", JOptionPane.ERROR_MESSAGE);
            }
       }
        
        if(e.getSource() == abortButton)
        {
            this.dispose();     
        }
    }
}

