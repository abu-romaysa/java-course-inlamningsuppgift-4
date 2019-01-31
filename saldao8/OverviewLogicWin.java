package saldao8;

/**
 * This class implements the overview GUI that has 
 * all the actions needed for handling customers and their accounts.
 * 
 * @author Salim Daoud, saldao-8
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OverviewLogicWin extends JFrame implements AccountTypes
{
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    public static final int DEPOSIT = 1;
    public static final int WITHDRAW = 2;
    
    // menu related
    private JMenuItem exitMenuItem;
    private JMenuItem saveMenuItem; 
    private JMenuItem loadMenuItem; 

    // customer section related
    private JPanel customersPanel = new JPanel();
    private JTextField customerFirstNameTextField = new JTextField("", 10);
    private JTextField customerLastNameTextField = new JTextField("", 10);
    private JTextField customerPNRTextField = new JTextField("", 10);
    private JList customerList = new JList();
    
    // account section related
    private JList accountList = new JList();
    private JTextField accountTypeTextField = new JTextField(10);
    private JTextField accountInterestTextField = new JTextField(10);
    private JTextField accountBalanceTextField = new JTextField(10);
    private JTextField accountCreditTextField = new JTextField(10);
    private JLabel creditLabel = new JLabel("Credit Limit");
    
    private BankLogic bankLogic;
    private OverviewLogicWin overviewLogicWin;

    /**
     * Constructor
     */
    public OverviewLogicWin()
    {
        initiateInstanceVariables();
        buildWindow();
    }
    
    /**
     * Initiates variables
     */
    private void initiateInstanceVariables()
    {
        overviewLogicWin = this;
        bankLogic = new BankLogic();
    }
    
    /**
     * Builds the window's GUI
     */
    private void buildWindow()
    {
        createMenu();
        createWindowContent();
        
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Saldao-8 Banking System (Beta)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Creates the main structure and content for this window's GUI
     */
    private void createWindowContent()
    {
        JLabel windowTitleLabel = new JLabel("SYSTEM OVERVIEW");
        JPanel titlePanel = new JPanel();
        //https://stackoverflow.com/a/29148550
        windowTitleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titlePanel.add(windowTitleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        customersPanel.setLayout(new GridLayout(2, 2, 20, 5));
        createCustomerListSection();
        createAccountListSection();
        createCustomerDetailSection();
        createAccountDetailSection();
        customersPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(customersPanel);
        
        this.add(mainPanel);
    }
    
    /**
     * Creates and builds the GUI components for the customer list section  
     */
    private void createCustomerListSection()
    {
        //http://www.java2s.com/Tutorial/Java/0240__Swing/SettingtheSelectionModeofaJListComponent.htm
        // allow only one item to be selected at a time
        customerList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        customerList.addListSelectionListener(new ListSelectionHandler());
        
        JPanel customerListPanel = new JPanel();
        //https://stackoverflow.com/a/20359885
        // using BorderLayout so the JList fills the JPanel
        customerListPanel.setLayout(new BorderLayout());
        customerListPanel.add(customerList);
        
        TitledBorder customerListTitle = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "List of Customers");
        customerListTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        customerListPanel.setBorder(customerListTitle);
        
        customersPanel.add(customerListPanel);
    }
    
    /**
     * Creates and builds the GUI components for the account list section  
     */
    private void createAccountListSection()
    {  
        accountList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        accountList.addListSelectionListener(new AccountsListSelectionHandler());

        JPanel accountListPanel = new JPanel();
        accountListPanel.setLayout(new BorderLayout());
        accountListPanel.add(accountList);

        TitledBorder accountListTitle = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "List of Accounts");
        accountListTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        accountListPanel.setBorder(accountListTitle);

        customersPanel.add(accountListPanel);
    }
    
    /**
     * Creates and builds the GUI components for the customer detail section 
     */
    private void createCustomerDetailSection()
    {
        JPanel customerDetailsPanel = new JPanel();
        
        JPanel customerDetailsTextPanel = new JPanel();
        customerDetailsTextPanel.setLayout(new GridLayout(4, 2, 2, 2));
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel pnrLabel = new JLabel("Personal Identity Number");
        customerDetailsTextPanel.add(firstNameLabel);
        customerDetailsTextPanel.add(lastNameLabel);
        customerDetailsTextPanel.add(customerFirstNameTextField);
        customerDetailsTextPanel.add(customerLastNameTextField);
        customerDetailsTextPanel.add(pnrLabel);
        JLabel empty = new JLabel();
        customerDetailsTextPanel.add(empty);
        customerDetailsTextPanel.add(customerPNRTextField);
                
        JPanel customerDetailsButtonsPanel = new JPanel();
        // right align the buttons
        customerDetailsButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        
        JButton updateButton = new JButton("Update");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        customerDetailsButtonsPanel.add(updateButton);
        customerDetailsButtonsPanel.add(addButton);
        customerDetailsButtonsPanel.add(deleteButton);
        customerDetailsButtonsPanel.add(clearButton);
        ActionListener buttonListener = new ButtonListener();
        updateButton.addActionListener(buttonListener);
        addButton.addActionListener(buttonListener);
        deleteButton.addActionListener(buttonListener);
        clearButton.addActionListener(buttonListener);
        customerDetailsButtonsPanel.setBorder(BorderFactory.createEmptyBorder(110, 0, 0, 0));
        
        customerDetailsPanel.setLayout(new BorderLayout());
        customerDetailsPanel.add(customerDetailsTextPanel);
        customerDetailsPanel.add(customerDetailsButtonsPanel, BorderLayout.SOUTH);
        TitledBorder customerDetailTitle = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Customer Information");
        customerDetailTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        customerDetailsPanel.setBorder(customerDetailTitle);
        
        customersPanel.add(customerDetailsPanel);
    }
    
    /**
     * Creates and builds the GUI components for the account detail section 
     */
    private void createAccountDetailSection()
    {
        JPanel accountDetailsPanel = new JPanel();
        accountDetailsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8, 1));
        
        JLabel typeLabel = new JLabel("Type");
        JLabel interestLabel = new JLabel("Interest");
        JLabel balanceLabel = new JLabel("Balance");
        
        accountTypeTextField.setEditable(false);
        accountInterestTextField.setEditable(false);
        accountBalanceTextField.setEditable(false);
        accountCreditTextField.setEditable(false);
        accountCreditTextField.setVisible(false);
        creditLabel.setVisible(false);

        leftPanel.add(typeLabel);
        leftPanel.add(accountTypeTextField);
        leftPanel.add(balanceLabel);
        leftPanel.add(accountBalanceTextField);
        leftPanel.add(interestLabel);
        leftPanel.add(accountInterestTextField);
        leftPanel.add(creditLabel);
        leftPanel.add(accountCreditTextField);
        
        JPanel rightPanel = new JPanel();
        JLabel empty = new JLabel();
        
        rightPanel.setLayout(new GridLayout(7, 1, 0, 5));
        JButton addAccountButton = new JButton("Add Account");
        JButton deleteAccountButton = new JButton("Delete");
        JButton depositButton = new JButton("Deposit");
        JButton widthdrawButton = new JButton("Widthdraw");
        JButton transactionButton = new JButton("View Transactions");
        rightPanel.add(addAccountButton);
        rightPanel.add(deleteAccountButton);
        rightPanel.add(depositButton);
        rightPanel.add(widthdrawButton);
        rightPanel.add(transactionButton);
        // filling the grid with some empty components to get wanted design and button sizes
        rightPanel.add(empty);
        rightPanel.add(empty);
        rightPanel.add(empty);
        
        ActionListener accountButtonListener = new AccountButtonListener();
        addAccountButton.addActionListener(accountButtonListener);
        deleteAccountButton.addActionListener(accountButtonListener);
        depositButton.addActionListener(accountButtonListener);
        widthdrawButton.addActionListener(accountButtonListener);
        transactionButton.addActionListener(accountButtonListener);

        accountDetailsPanel.add(leftPanel);
        accountDetailsPanel.add(rightPanel);
        TitledBorder accountDetailTitle = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Account Information");
        accountDetailTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        accountDetailsPanel.setBorder(accountDetailTitle);
        
        customersPanel.add(accountDetailsPanel);
    }
    
    /**
     * Builds the GUI's menu
     */
    private void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("FILE");        
        loadMenuItem = new JMenuItem("Load");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new MenuItemListener());
        // this is a beta version, therefore some options are inactive for the moment
        // disable the load option if there are no available customers stored
        loadMenuItem.setEnabled(bankLogic.sysHasStoredCustomers());
        saveMenuItem.setEnabled(true);
        loadMenuItem.addActionListener(new MenuItemListener());
        saveMenuItem.addActionListener(new MenuItemListener());
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        
        JMenu customerMenu = new JMenu("CUSTOMER");
        JMenuItem createCustomerMenuItem = new JMenuItem("Create Customer");
        JMenuItem createAccountMenuItem = new JMenuItem("Change Customer Name");
        JMenuItem deleteAccountMenuItem = new JMenuItem("Delete Customer");
        createCustomerMenuItem.setEnabled(false);
        createAccountMenuItem.setEnabled(false);
        deleteAccountMenuItem.setEnabled(false);
        customerMenu.add(createCustomerMenuItem);
        customerMenu.add(createAccountMenuItem);
        customerMenu.add(deleteAccountMenuItem);
        
        JMenu accountMenu = new JMenu("ACCOUNT");
        JMenuItem createSavingsAccountMenuItem = new JMenuItem("Create Savings Account");
        JMenuItem createCreditAccountMenuItem = new JMenuItem("Create Credit Account");
        JMenuItem closeAccountMenuItem = new JMenuItem("Close Account");
        createSavingsAccountMenuItem.setEnabled(false);
        createCreditAccountMenuItem.setEnabled(false);
        closeAccountMenuItem.setEnabled(false);
        accountMenu.add(createSavingsAccountMenuItem);
        accountMenu.add(createCreditAccountMenuItem);
        accountMenu.add(closeAccountMenuItem);        

        JMenu HelpMenu = new JMenu("HELP");
        JMenuItem aboutUsMenuItem = new JMenuItem("About Us");
        aboutUsMenuItem.setEnabled(false);
        HelpMenu.add(aboutUsMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(customerMenu);
        menuBar.add(accountMenu);
        menuBar.add(HelpMenu);
        this.add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Inner class that implements the listener used for the customer list
     */
    public class ListSelectionHandler implements ListSelectionListener {
        //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/events/ListSelectionDemoProject/src/events/ListSelectionDemo.java
        public void valueChanged(ListSelectionEvent e) { 
            int idx = customerList.getSelectedIndex();
            
            // update and visualize the customer details and accounts if a new item in the list is selected  
            if(idx != -1)
            {
                String selectedListCustomer = (String) customerList.getSelectedValue();
                String[] selectedCustomer = selectedListCustomer.split(" ");
                customerFirstNameTextField.setText(selectedCustomer[0]);
                customerLastNameTextField.setText(selectedCustomer[1]);
                String personalIdentityNumber = selectedCustomer[2];
                customerPNRTextField.setText(personalIdentityNumber);
                
                // update the account list to visualize accounts belonging to this specific customer
                //https://stackoverflow.com/questions/3269516/java-arraylists-into-jlist
                accountList.setListData(bankLogic.getAccountIds(personalIdentityNumber).toArray());
            }
        }
    }
    
    /**
     * Inner class that implements the listener used for the account list
     */
    public class AccountsListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
            
            int idx = accountList.getSelectedIndex();            
            if(idx != -1)
            {
                // the prerequisite is that a customer has to be selected
                if(customerList.getSelectedIndex() != -1)
                {
                    String personalIdentityNumber = getSelectedCustomerIdentityNr();
                    int selectedListAccount = (int) accountList.getSelectedValue();
                    String accountInfo = bankLogic.getAccount(personalIdentityNumber, selectedListAccount);
                    
                    if(accountInfo != null)
                    {
                        // update and visualize the customer's account details
                        String[] accountSubInfo = accountInfo.split(" ");
                        String accountType = accountSubInfo[2] + " " + accountSubInfo[3];
                        
                        accountBalanceTextField.setText(accountSubInfo[1]);
                        accountTypeTextField.setText(accountType);
                        accountInterestTextField.setText(accountSubInfo[4]);
                        
                        // if it is a credit account, then visualize and update corresponding components
                        if(accountType.equals("Credit account"))
                        {
                            accountCreditTextField.setText(accountSubInfo[5]);
                            accountCreditTextField.setVisible(true);
                            creditLabel.setVisible(true);
                        }
                        else
                        {
                            accountCreditTextField.setVisible(false);
                            creditLabel.setVisible(false);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Customer does not exists", "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer in the list!");
                }
            }
            else
            {
                clearAccountTextFields();
            }
        }
    }

    /**
     * Inner class that implements the listeners used for customer related buttons
     */
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            String buttonText = e.getActionCommand();

            if(buttonText.equals("Add"))
            {
                // remove all space characters (that the user might accidentally added)
                //https://stackoverflow.com/a/16035109
                String firstName = customerFirstNameTextField.getText().replaceAll("\\s","");;
                String lastName = customerLastNameTextField.getText().replaceAll("\\s","");;
                String personalIdentityNumber = customerPNRTextField.getText().replaceAll("\\s","");;
                
                if(!personalIdentityNumber.equals("") && hasValidPNRFormat(personalIdentityNumber))
                {
                    if(hasOnlyLetters(firstName) && hasOnlyLetters(lastName))
                    {
                        if(bankLogic.createCustomer(firstName, 
                                lastName, 
                                personalIdentityNumber))
                        {
                            // update the customer list and clear the text fields to facilitate further additions of customers
                            customerList.setListData(bankLogic.getAllCustomers().toArray());
                            clearTextFields();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Customer already exists", "Alert", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Only letters are aloud for customer names!", "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please type a valid customer personal identity number (in format):\nYYYYMMDD-XXXX");
                }
            }

            if(buttonText.equals("Update"))
            {
                int idx = customerList.getSelectedIndex();
                if(idx != -1)
                {
                    // get new info, do the name change, update customer list and finally clear the textfields
                    String firstName = customerFirstNameTextField.getText();
                    String lastName = customerLastNameTextField.getText();
                    String personalIdentityNumber = customerPNRTextField.getText();
                    bankLogic.changeCustomerName(firstName, lastName, personalIdentityNumber);                    
                    customerList.setListData(bankLogic.getAllCustomers().toArray());
                    clearTextFields();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer in the list!");
                }
            }

            if(buttonText.equals("Delete"))
            {
                int idx = customerList.getSelectedIndex();
                if(idx != -1)
                {
                    String personalIdentityNumber = getSelectedCustomerIdentityNr();
                    
                    // make sure that the user really wants to delete the customer by using a confirmation dialog
                    int selectedOption = JOptionPane.showConfirmDialog(
                            null, 
                            "Are you sure you want to delete customer: " + personalIdentityNumber, 
                            "ATTENTION", 
                            JOptionPane.YES_NO_OPTION); 
                    
                    // if user confirms the wish to delete
                    if (selectedOption == JOptionPane.YES_OPTION) 
                    {
                        // delete customer and update the lists
                        bankLogic.deleteCustomer(personalIdentityNumber);
                        customerList.setListData(bankLogic.getAllCustomers().toArray());
                        accountList.setListData(bankLogic.getAccountIds(personalIdentityNumber).toArray());
                        clearTextFields();                        
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer in the list!");
                }
            }

            if(buttonText.equals("Clear"))
            {
                clearTextFields();
            }
        }
    }
    
    /**
     * Inner class that implements the listeners used for account related buttons
     */
    public class AccountButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            String buttonText = e.getActionCommand();

            if(buttonText.equals("Add Account"))
            {
                // the prerequisite is that a customer has to be selected
                int idx = customerList.getSelectedIndex();
                if(idx != -1)
                {
                    String personalIdentityNumber = getSelectedCustomerIdentityNr();

                    // create and open own tailored dialog window for adding accounts
                    JDialog addAccountDialog = new AddAccountDialog(overviewLogicWin, personalIdentityNumber);
                    addAccountDialog.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer in the list!");
                }
            }
            
            if(buttonText.equals("Delete"))
            {
                int account_idx = accountList.getSelectedIndex();
                int customer_idx = customerList.getSelectedIndex();

                // the prerequisite is that a customer and an account is selected
                if(account_idx != -1 && customer_idx != -1)
                {          
                    int selectedListAccount = (int) accountList.getSelectedValue();
                    
                    // make sure that the user really wants to delete the account by using a confirmation dialog
                    int selectedOption = JOptionPane.showConfirmDialog(
                            null, 
                            "Are you sure you want to delete account: " + selectedListAccount, 
                            "ATTENTION", 
                            JOptionPane.YES_NO_OPTION); 
                    
                    // if user confirms the wish to delete
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        String personalIdentityNumber = getSelectedCustomerIdentityNr();

                        String closedAccountInfo = bankLogic.closeAccount(personalIdentityNumber, selectedListAccount);                       
                        if(closedAccountInfo != null)
                        {    
                            // update the account list and present information about the closed account to the user
                            accountList.setListData(bankLogic.getAccountIds(personalIdentityNumber).toArray());
                            JOptionPane.showMessageDialog(null, "Deleted Account: \n" + closedAccountInfo);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Account does not exists", "Alert", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer & an account in the list!");
                }
            }
            
            if(buttonText.equals("Deposit") || buttonText.equals("Widthdraw"))
            {
                int account_idx = accountList.getSelectedIndex();
                int customer_idx = customerList.getSelectedIndex();

                // the prerequisite is that a customer and an account is selected
                if(account_idx != -1 && customer_idx != -1)
                {          
                    int selectedListAccount = (int) accountList.getSelectedValue();
                    String personalIdentityNumber = getSelectedCustomerIdentityNr();
                    
                    // check action to be executed
                    int action = DEPOSIT;
                    if(buttonText.equals("Widthdraw"))
                    {
                        action = WITHDRAW;
                    }
                    
                    // create and open own tailored dialog window for transaction actions
                    JDialog depositDialog = new TransactionDialog(action, overviewLogicWin, personalIdentityNumber, selectedListAccount);
                    depositDialog.setVisible(true);
                    
                    // update and visualize the customer's new balance  
                    String accountInfo = bankLogic.getAccount(personalIdentityNumber, selectedListAccount);
                    if(accountInfo != null)
                    {
                        String[] accountSubInfo = accountInfo.split(" ");                        
                        accountBalanceTextField.setText(accountSubInfo[1]);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer & an account in the list!");
                }
            }
            
            if(buttonText.equals("View Transactions"))
            {
                int account_idx = accountList.getSelectedIndex();
                int customer_idx = customerList.getSelectedIndex();

                // the prerequisite is that a customer and an account is selected
                if(account_idx != -1 && customer_idx != -1)
                {          
                    int selectedListAccount = (int) accountList.getSelectedValue();
                    String personalIdentityNumber = getSelectedCustomerIdentityNr();

                    // create and open own tailored dialog window for showing the transaction history
                    JDialog depositDialog = new TransactionHistoryDialog(overviewLogicWin, personalIdentityNumber, selectedListAccount);
                    depositDialog.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You have to mark a customer & an account in the list!");
                }
            }
        }
    }

    /**
     * Inner class that implements the listeners used for the menu items
     */
    public class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                        
            if(e.getSource() == exitMenuItem)
            {
                System.exit(0);
            }
            
            if(e.getSource() == saveMenuItem)
            {
                bankLogic.saveAllCustomersInfo();
            }
            
            if(e.getSource() == loadMenuItem)
            {
                bankLogic.loadAllCustomersInfo();
                customerList.setListData(bankLogic.getAllCustomers().toArray());
                loadMenuItem.setEnabled(false);
            }
        }
    }
    
    /**
     * Clears all the customer related text fields 
     */
    private void clearTextFields()
    {
        customerFirstNameTextField.setText("");
        customerLastNameTextField.setText("");
        customerPNRTextField.setText("");
    }

    /**
     * Clears all the account related text fields 
     */
    private void clearAccountTextFields()
    {
        accountTypeTextField.setText("");
        accountInterestTextField.setText("");
        accountBalanceTextField.setText("");
        accountCreditTextField.setText("");
    }
    
    
    /**
     * Fetches the selected customer's personal identity number
     * 
     * @return string containing the customer's personal identity number
     */
    private String getSelectedCustomerIdentityNr()
    { 
        String selectedListCustomer = (String) customerList.getSelectedValue();
        String[] selectedCustomer = selectedListCustomer.split(" ");
        return selectedCustomer[2];
    }
    
    /**
     * Creates a saving account for a customer
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return true if the the account could be created
     */
    public boolean createSavingsAccount(String personalIdentityNumber)
    {
        if(bankLogic.createSavingsAccount(personalIdentityNumber) != -1)
        {
            accountList.setListData(bankLogic.getAccountIds(personalIdentityNumber).toArray());
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    /**
     * Creates a credit account for a customer
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return true if the the account could be created
     */
    public boolean createCreditAccount(String personalIdentityNumber)
    {
        if(bankLogic.createCreditAccount(personalIdentityNumber) != -1)
        {
            accountList.setListData(bankLogic.getAccountIds(personalIdentityNumber).toArray());
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    /**
     * Deposits the amount to the customer's account
     * 
     * @param personalIdentityNumber - belonging to the customer to deposit to
     * @param accountId - account ID to deposit to
     * @param amount - the amount to deposit
     * @return true if the amount could be deposit
     */
    public boolean deposit(String personalIdentityNumber, int accountId, double amount)
    {
        return bankLogic.deposit(personalIdentityNumber, accountId, amount);
    }
    
    /**
     * Withdraws the amount from the customer's account
     * 
     * @param personalIdentityNumber - belonging to the customer to withdraw from
     * @param accountId - account ID to withdraw from
     * @param amount - the amount to withdraw
     * @return true if the amount could be withdrawn
     */
    public boolean withdraw(String personalIdentityNumber, int accountId, double amount)
    {
        return bankLogic.withdraw(personalIdentityNumber, accountId, amount);
    }
    
    /**
     * Provides information about a customer's account transactions
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @param accountId - the account ID in question
     * @return list of strings containing all the account's transactions
     */    
    public ArrayList<String> getTransactions(String personalIdentityNumber, int accountId)
    {
        return bankLogic.getTransactions(personalIdentityNumber, accountId);
    }
    
    /**
     * Saves an accounts transactions
     * 
     * @param accountId - the account ID in question
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return true if the transactions could be saved successfully
     */
    public boolean saveAccountTransactions(int accountId, String personalIdentityNumber)
    {
        ArrayList<String> transactions = this.getTransactions(personalIdentityNumber, accountId);
        return bankLogic.saveAccountTransactions(accountId, transactions, personalIdentityNumber);
    }
    
    /**
     * Check if the personal identity number has the right format
     * 
     * @param personalIdentityNumber - personal identity number in question to check
     * @return true if it has the right format
     */
    private boolean hasValidPNRFormat(String personalIdentityNumber)
    {
        //https://stackoverflow.com/a/40097058
        return personalIdentityNumber.matches("\\d\\d\\d\\d\\d\\d\\d\\d-\\d\\d\\d\\d");
    }
    
    /**
     * Check if a string only contains of letters
     * 
     * @param input - string to check
     * @return true if it only contains of letters
     */
    private boolean hasOnlyLetters(String input)
    {        
      //https://stackoverflow.com/a/3059373
      System.out.println(input);
      for(int i = 0; i<input.length(); i++)
      {
         if(!Character.isLetter(input.charAt(i)))
         {
             System.out.println(input.charAt(i));
            return false;
         }
      }
      
      return true;
    }

}
