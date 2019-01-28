package saldao8;

/**
 * This class implements the main GUI that gives quick access to different 
 * common actions.
 * 
 * @author Salim Daoud, saldao-8
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainWindow extends JFrame
{
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;

    private JButton overviewButton = new JButton("Overview");
    private JButton handleCustomerButton = new JButton("Handle Customer");
    private JButton newCustomerButton = new JButton("New Customer");
    private JButton newAccountButton = new JButton("New Account");
    private JButton closeAccountButton = new JButton("Close Account");
    private JButton depositButton = new JButton("Deposit");
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton transactionsButton = new JButton("Transactions");
    private ArrayList<JButton> buttonList = new ArrayList<JButton>();
    private JMenuItem exitMenuItem = new JMenuItem("Exit"); 
    
    private MainWindow mainWindow;
    
    /**
     * The application's entry point
     *
     * @param args - an array of command-line arguments for the application (not used)
     */
    public static void main(String[] args)
    {
        JFrame frame = new MainWindow();
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null,"Note: this is a beta version!");
    }

    /**
     * Constructor
     */
    public MainWindow()
    {
        buttonList.add(overviewButton);
        buttonList.add(handleCustomerButton);
        buttonList.add(newCustomerButton);
        buttonList.add(newAccountButton);
        buttonList.add(closeAccountButton);
        buttonList.add(depositButton);
        buttonList.add(withdrawButton);
        buttonList.add(transactionsButton);
        
        mainWindow = this;
 
        createComponents();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Saldao-8 Banking System (Beta)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Builds the GUI
     */
    private void createComponents()
    {
        createMenu();
        createMainWindowContent();     
    }
    
    /**
     * Builds the main content of the GUI
     */
    private void createMainWindowContent()
    {
        JPanel titlePanel = new JPanel(); 
        JLabel windowTitleLabel = new JLabel("QUICK ACCESS VIEW");
        //https://stackoverflow.com/a/29148550
        windowTitleLabel.setFont(new Font("Serif", Font.BOLD, 20)); 
        titlePanel.add(windowTitleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        JPanel overviewOnePanel = new JPanel();
        JPanel overviewTwoPanel = new JPanel();
        JPanel overviewThreePanel = new JPanel();
        JPanel overviewFourPanel = new JPanel();
        
        overviewOnePanel.setLayout(new FlowLayout());
        overviewTwoPanel.setLayout(new FlowLayout());
        overviewThreePanel.setLayout(new FlowLayout());
        overviewFourPanel.setLayout(new FlowLayout());
        
        overviewOnePanel.add(overviewButton);
        overviewTwoPanel.add(handleCustomerButton);
        overviewTwoPanel.add(newCustomerButton);
        overviewThreePanel.add(newAccountButton);
        overviewThreePanel.add(closeAccountButton);
        overviewFourPanel.add(depositButton);
        overviewFourPanel.add(withdrawButton);
        overviewFourPanel.add(transactionsButton);

        // this is a beta version, therefore some options are inactive for the moment
        handleCustomerButton.setEnabled(false);
        newCustomerButton.setEnabled(false);
        newAccountButton.setEnabled(false);
        closeAccountButton.setEnabled(false);
        depositButton.setEnabled(false);
        withdrawButton.setEnabled(false);
        transactionsButton.setEnabled(false);
        setButtonSizes();
        addButtonListeners();

        //https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
        overviewOnePanel.setBorder(BorderFactory.createTitledBorder("Overview"));
        overviewTwoPanel.setBorder(BorderFactory.createTitledBorder("Customer"));
        overviewThreePanel.setBorder(BorderFactory.createTitledBorder("Account"));
        overviewFourPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JPanel quickOptionsPanel = new JPanel();
        quickOptionsPanel.setLayout(new BoxLayout(quickOptionsPanel, BoxLayout.Y_AXIS));
        quickOptionsPanel.add(overviewOnePanel);
        quickOptionsPanel.add(overviewTwoPanel);
        quickOptionsPanel.add(overviewThreePanel);
        quickOptionsPanel.add(overviewFourPanel);
         
        //https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
        //https://www.daniweb.com/programming/software-development/threads/425665/add-space-between-components-and-jframe
        quickOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
        
        JPanel mainPanel = new JPanel();
        mainPanel.add(titlePanel);
        mainPanel.add(quickOptionsPanel);
                
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
    }
    
    /**
     * Sets all buttons to the same size
     */
    private void setButtonSizes()
    {
        for(JButton jButton : buttonList)
        {
            jButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        }
    }
    
    /**
     * Adds listeners to all buttons
     */
    private void addButtonListeners()
    {
        ActionListener buttonListener = new ButtonListener();
        for(JButton jButton : buttonList)
        {
            jButton.addActionListener(buttonListener);
        }
    }
    
    /**
     * Builds the GUI's menu
     */
    private void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("FILE");        
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new MenuItemListener());
        loadMenuItem.setEnabled(false);
        saveMenuItem.setEnabled(false);
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
     * Inner class that implements the listeners used for buttons
     */
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == overviewButton)
            {
                JFrame frame = new OverviewLogicWin();
                frame.setVisible(true);
                mainWindow.setVisible(false);
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
                //https://stackoverflow.com/a/19764841
                System.exit(0);
            }
        }
    }

}