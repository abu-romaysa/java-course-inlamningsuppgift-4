package saldao8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class implements the bank logic that manages customers and their
 * accounts
 * 
 * @author Salim Daoud, saldao-8
 */

import java.util.ArrayList;

public class BankLogic implements AccountTypes
{
    public static final String DATA_FILE_NAME = "saldao8_Files/customer-full-info.dat";
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    /**
     * Constructor
     */
    public BankLogic()
    {
    }

    /**
     * Provides a list of information about all the customers in the bank system
     * 
     * @return a list of strings containing information about all the customers
     */
    public ArrayList<String> getAllCustomers()
    {
        ArrayList<String> customersInfo = new ArrayList<String>();

        for(Customer customer : customers)
        {
            customersInfo.add(customer.toString());
        }

        return customersInfo;
    }

    /**
     * Creates and adds a new customer to the bank system
     * 
     * @param firstName - new customer's first name
     * @param lastName - new customer's last name
     * @param personalIdentityNumber - new customer's personal identity number
     * @return true if the customer could be created
     */
    public boolean createCustomer(String firstName, String lastName, String personalIdentityNumber)
    {
        boolean customerCreated = false;

        // check if the customer exists i.e. findCustomer returns an object rather than null
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer == null) 
        {
            Customer newCustomer = new Customer(firstName, lastName, personalIdentityNumber);
            customers.add(newCustomer);
            customerCreated = true;
        }

        return customerCreated;
    }

    /**
     * Provides information about a customer and her accounts
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return list of strings containing information about the customer and her
     *         accounts
     */
    public ArrayList<String> getCustomer(String personalIdentityNumber)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            ArrayList<String> customerInfo = new ArrayList<String>();
            customerInfo.add(customer.toString());

            for(Integer accountId : customer.getAccountIds())
            {
                customerInfo.add(customer.getAccount(accountId));
            }

            return customerInfo;
        }

        return null;
    }

    /**
     * Changes a customer's first and last name
     * 
     * @param firstName - first name to change to
     * @param lastName - last name to change to
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return true if the customer's full name could be changed
     */
    public boolean changeCustomerName(String firstName, String lastName, String personalIdentityNumber)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            customer.changeFirstName(firstName);
            customer.changeLastName(lastName);

            return true;
        }

        return false;
    }

    /**
     * Deletes the customer from the bank system
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return string containing information about the removed customer and her
     *         removed accounts, otherwise null if the customer could not be
     *         deleted
     */
    public ArrayList<String> deleteCustomer(String personalIdentityNumber)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            ArrayList<String> deletedCustomerInfo = new ArrayList<String>();
            deletedCustomerInfo.add(customer.toString());

            ArrayList<String> deletedCustomerAccounts = customer.deleteAccounts();

            // merge the two lists into one
            deletedCustomerInfo.addAll(deletedCustomerAccounts);

            customers.remove(customer);

            return deletedCustomerInfo;
        }

        return null;
    }

    /**
     * Closes a customer account
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @param accountId - account ID to remove
     * @return string containing information about the customer's closed
     *         account, otherwise null if it could not be closed
     */
    public String closeAccount(String personalIdentityNumber, int accountId)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            String closedAccountInfo = customer.removeAccount(accountId);            
            return closedAccountInfo;
        }

        return null;
    }

    /**
     * Creates a saving account for a customer
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return the account ID for the created account, otherwise the value -1 if
     *         it couldn't create an account
     */
    public int createSavingsAccount(String personalIdentityNumber)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.createAccount(AccountType.SAVINGS_ACCOUNT);
        }

        return -1;
    }
    
    /**
     * Creates a credit account for a customer
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return the account ID for the created account, otherwise the value -1 if
     *         it couldn't create an account
     */
    public int createCreditAccount(String personalIdentityNumber)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.createAccount(AccountType.CREDIT_ACCOUNT);
        }

        return -1;
    }

    /**
     * Provides information about a customer's account
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @param accountId - the account ID in question
     * @return string containing information about the customer's account,
     *         otherwise null if it could not be found
     */
    public String getAccount(String personalIdentityNumber, int accountId)
    {
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.getAccount(accountId);
        }

        return null;
    }
    
    /**
     * Provides the customer's account IDs
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @return list with account IDs belonging to the customer
     */
    public ArrayList<Integer> getAccountIds(String personalIdentityNumber)
    {
        ArrayList<Integer> accountIds = new ArrayList<Integer>();

        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            accountIds = customer.getAccountIds();
        }
        
        return accountIds;
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
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.deposit(accountId, amount);
        }

        return false;
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
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.withdraw(accountId, amount);
        }

        return false;
    }
    
    /**
     * Provides information about a customer's account transactions
     * 
     * @param personalIdentityNumber - belonging to the customer of interest
     * @param accountId - the account ID in question
     * @return a list of strings containing all the account's transactions
     */
    public ArrayList<String> getTransactions(String personalIdentityNumber, int accountId)
    {
        ArrayList<String> transactions = new ArrayList<String>();
        
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.getTransactions(accountId);
        }
        
        return transactions;
    }
    
    /**
     *  Provides the account's balance - saldao
     *
     * @param personalIdentityNumber - belonging to the customer of interest
     * @param accountId - the account ID in question
     * @return customer account balance
     */
    public double getBalance(String personalIdentityNumber, int accountId)
    {
        double balance = 0;
        Customer customer = findCustomer(personalIdentityNumber);
        if(customer != null)
        {
            return customer.getBalance(accountId);
        }
        
        return balance;
    }
    
    
    /**
     * Saves all customer information in a data file
     */
    public void saveFullCustomersInfo()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("saldao8_Files/customer-full-info.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeInt(customers.size());
            oos.writeInt(Account.getAccountIdCounter());
            
            for(Customer customer : customers)
            {
                oos.writeObject(customer);
            }
         
            //https://softwareengineering.stackexchange.com/q/363161 - faild to close but inout
            //https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html
            //https://docs.oracle.com/javase/7/docs/api/java/io/OutputStreamWriter.html#close()
            oos.close();
        }
        catch(FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Loads all customer information from a previously saved data file
     */
    public void loadFullCustomersInfo()
    {
        try
        {
            FileInputStream fos = new FileInputStream("saldao8_Files/customer-full-info.dat"); // todo check if file exists first
            ObjectInputStream ois = new ObjectInputStream(fos);
            
            int nrOfCustomers = (int) ois.readInt();
            Account.setAccountIdCounter((int) ois.readInt());
            
            for(int i = 0; i < nrOfCustomers; i++)
            {
                Customer c_file = (Customer) ois.readObject();
                customers.add(c_file);
                
//                System.out.println(c_file.getPersonalIdentityNumber());
//                System.out.println(c_file.getAccountIds());
//                
//                ArrayList<Integer> accountIds_t = c_file.getAccountIds();
//                for(Integer accountId_t : accountIds_t)
//                {
//                    System.out.println(c_file.getAccount(accountId_t));
//                    System.out.println(c_file.getTransactions(accountId_t));
//                }                
            }

            ois.close();
        }
        catch(FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) // todo merge exceptions?
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Searches for a customer with a specific personal identity number
     * 
     * @param personalIdentityNumber - personal identity number to lookup
     * @return the customer with a specific personal identity number, otherwise
     *         null if the customer could not be found
     */
    private Customer findCustomer(String personalIdentityNumber)
    {
        for(Customer customer : customers)
        {
            //https://www.journaldev.com/18009/java-string-compare#java-string-comparison-using-operator
            if(customer.getPersonalIdentityNumber().equals(personalIdentityNumber))
            {
                return customer;
            }
        }

        return null;
    }

}
