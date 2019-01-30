package saldao8;

/**
 * This class implements the Dialog GUI for presenting transactions
 * 
 * @author Salim Daoud, saldao-8
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class TransactionHistoryDialog extends JDialog implements ActionListener
{
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;

    private JPanel accountPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    JButton saveButton;
    
    private OverviewLogicWin overviewLogicWin;
    private int accountId;
    private String personalIdentityNumber;

    /**
     * Constructor
     * 
     * @param overviewLogicWin - parent and owner frame
     * @param personalIdentityNumber - new customer's personal identity number
     * @param accountId - the account ID in question
     */
    public TransactionHistoryDialog(OverviewLogicWin overviewLogicWin, String personalIdentityNumber, int accountId)
    {
        // calls the parents constructor i.e. JDialog
        super(overviewLogicWin, "Transaction History", true);
        
        this.overviewLogicWin = overviewLogicWin;
        this.accountId = accountId;
        this.personalIdentityNumber = personalIdentityNumber;
        
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
        // build account info section
        JLabel accountIdLabel = new JLabel("Account ID: ");
        JTextField accountIdTextField = new JTextField(10);
        accountPanel.setLayout(new GridLayout(1,  2));
        accountPanel.add(accountIdLabel);
        accountPanel.add(accountIdTextField);
        accountIdTextField.setEditable(false);
        accountIdTextField.setText(Integer.toString(accountId));
        accountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(accountPanel, BorderLayout.NORTH);
        
        // build scrollable text area section
        JTextArea transactionTextArea = new JTextArea(5, 20);
        ArrayList<String> transactions = overviewLogicWin.getTransactions(personalIdentityNumber, accountId);
        
        String transactionsText = new String();
        for(String transaction : transactions)
        {
            transactionsText += transaction + "\n";
        }
        
        transactionTextArea.setText(transactionsText);
        transactionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        transactionTextArea.setEditable(false);
        // add a vertical scrollbar to the text area 
        JScrollPane scrollPane = new JScrollPane(transactionTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane);
        
        // build button's section
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        saveButton = new JButton("Save to file");
        JButton closeButton = new JButton("Close");
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        
        String buttonText = e.getActionCommand();
        
        if(buttonText.equals("Close"))
        {
            this.dispose();     
        }
        
        if(buttonText.equals("Save to file"))
        {
            if(overviewLogicWin.saveAccountTransactions(this.accountId, personalIdentityNumber))
            {
                saveButton.setEnabled(false);
            }
        }
    }
}

