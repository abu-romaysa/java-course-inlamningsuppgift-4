package saldao8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class FileHandler
{
    private static final String SYSTEM_DATA_FILE_NAME = "saldao8_Files/customer-full-info.dat";
    private static final String TRANSACTION_FILE_PREFIX = "saldao8_Files/transactions-account-";
    private static final String TRANSACTION_FILE_EXTENSION = ".txt";
    
    private File dataFile;

    /**
     * Constructor
     */
    public FileHandler()
    {
        dataFile = new File(SYSTEM_DATA_FILE_NAME);
    }
        
    /**
     * Saves all customer information in a data file
     * 
     * @param customers - list of customers to save to file
     */
    public void saveCustomersToStorage(ArrayList<Customer> customers)
    {
        ObjectOutputStream oos = null;
        try
        {
            FileOutputStream fos = new FileOutputStream(SYSTEM_DATA_FILE_NAME);
            oos = new ObjectOutputStream(fos);
            
            oos.writeInt(customers.size());
            oos.writeInt(Account.getAccountIdCounter());
            
            for(Customer customer : customers)
            {
                oos.writeObject(customer);
            }
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "IO operation failed. IOException caught with message: " + "\n" + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            if (oos != null) 
            {
                try 
                {
                    //https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html
                    //https://docs.oracle.com/javase/7/docs/api/java/io/OutputStreamWriter.html#close()
                    oos.close();
                }
                catch (IOException e) 
                {
                  //https://softwareengineering.stackexchange.com/q/363161 - faild to close but inout
                }
            }
        }
    }
    
    /**
     * Loads all customer information from a previously saved data file
     * 
     * @return a list of customers 
     */
    public ArrayList<Customer> loadCustomersFromStorage()
    {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        // already checked the existence when creating the GUI, but the path or file
        // could been removed or modified in between (low possibility though)
        if(dataFile.isFile())
        {
            ObjectInputStream ois = null;
            try
            {
                FileInputStream fos = new FileInputStream(SYSTEM_DATA_FILE_NAME); // todo check if file exists first
                ois = new ObjectInputStream(fos);
                
                int nrOfCustomers = (int) ois.readInt();
                Account.setAccountIdCounter((int) ois.readInt());
                
                for(int i = 0; i < nrOfCustomers; i++)
                {
                    Customer c_file = (Customer) ois.readObject();
                    customers.add(c_file);             
                }            
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(null, "IO operation failed. IOException caught with message: " + "\n" + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            }
            catch(ClassNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Loading operation failed. ClassNotFoundException caught with message: " + "\n" + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);   
            }
            finally {
                if (ois != null) 
                {
                    try 
                    {
                        ois.close();
                    }
                    catch (IOException e) 
                    {
                      //https://softwareengineering.stackexchange.com/q/363161 - faild to close but inout
                    }
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "System data file does not exist:\n" + SYSTEM_DATA_FILE_NAME + "\n\n" + "Please restore needed file an restart the program if loading is the preference", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        
        return customers;
        
    }

    /**
     * Saves transactions belonging to a specific account to a file
     * 
     * @param accountId - the account ID in question
     * @param transactions - list of strings containing all the account's transactions
     * @param balance - the account´s balance
     * @return true if the transactions could be saved successfully
     */
    public boolean saveTransactionsToStorage(int accountId, ArrayList<String> transactions, String balance)
    {
        FileWriter fw = null;
        String filename = null;
        boolean writtenSuccessfully = false;            
        try
        {
            filename = TRANSACTION_FILE_PREFIX + accountId + TRANSACTION_FILE_EXTENSION;
            fw = new FileWriter(new File(filename));
            
            // output current date
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");           
            String transactionDate = dateFormat.format(currentDate);  
            fw.write("Printout date: " + transactionDate);
            //https://stackoverflow.com/a/18549788 //https://examples.javacodegeeks.com/core-java/io/filewriter/java-filewriter-example/
            fw.write(System.lineSeparator()); 
            
            // output transactions
            fw.write(System.lineSeparator());
            fw.write("Transactions:");
            for(String transaction : transactions)
            {
                fw.write(System.lineSeparator());
                fw.write(transaction);
            }
            fw.write(System.lineSeparator());

            // output balance            
            fw.write(System.lineSeparator());
            fw.write("Balance: " + balance);
            
            writtenSuccessfully = true;
        }
        catch(IOException ex)
        {
            //https://stackoverflow.com/q/6779787
            //https://examples.javacodegeeks.com/core-java/io/ioexception/java-io-ioexception-how-to-solve-ioexception/
            JOptionPane.showMessageDialog(null, "IO operation failed. IOException caught with message: " + "\n" + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);   
        }
        finally {
            if (fw != null) 
            {
                try 
                {
                    fw.close(); // todo catch? https://stackoverflow.com/a/5122970
                    
                    if(writtenSuccessfully)
                    {
                        JOptionPane.showMessageDialog(null, "Transactions successfully saved to file: " + filename);
                        return true;
                    }
                }
                catch (IOException e) 
                {
                    // nothing to do here except log the exception
                }
            }
        }

        return false;
    }
    
    /**
     * Checks if there are any stored system data file 
     * 
     * @return true if the system data file exists
     */
    public boolean hasStoredCustomers()
    {
        return dataFile.isFile();
    }

}
