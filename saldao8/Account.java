package saldao8;

/**
 * This class implements an abstract base class for accounts and it's needed support and management
 * as transactions, account information etc.
 *
 * @author Salim Daoud, saldao-8
 */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Account implements Serializable
{
    private double balance;
    private int accountId;
    private double interestRate;
    private static int accountIdCounter = 1001;
    private ArrayList<String> transactions = new ArrayList<String>();
    
    /**
     * Constructor
     */
    public Account()
    {
        // assign an accountId and accumulate the counter that handles Ids
        accountId = accountIdCounter;
        accountIdCounter += 1;

        balance = 0;
    }
    
    /**
     * Provides the account's balance
     *  
     * @return account's balance
     */
    protected double getBalance()
    {
        return balance;
    }
    
    /**
     * Sets the account's balance
     *  
     * @param amount - the amount to set
     */
    protected void setBalance(double amount)
    {
        balance = amount;
    }

    /**
     * Provides the account's interest rate 
     * 
     * @return the interest rate
     */
    protected double getInterestRate()
    {
        return interestRate;
    }

    /**
     * Sets the account's interest rate 
     * 
     * @param interestRate - the interest rate 
     */
    protected void setInterestRate(double interestRate)
    {
        this.interestRate = interestRate;
    }

    /**
     * Provides the account ID for this account
     * 
     * @return account ID
     */
    public int getAccountId()
    {
        return accountId;
    }
    
    /**
     * Provides the global account ID counter
     * 
     * @return global account ID counter
     */
    public static int getAccountIdCounter()
    {
        return accountIdCounter;
    }
    
    /**
     * Sets the global account ID counter
     */
    public static void setAccountIdCounter(int counterValue)
    {
        accountIdCounter = counterValue;
    }

    /**
     * Deposits the amount to the account
     * 
     * @param amount - the amount to deposit
     */
    public void deposit(double amount)
    {
        balance += amount;

        transactions.add(getTransactionDate() + " " + amount + " " + balance); 
    }
    
    /**
     * Creates and provides a transaction date
     * 
     * @return string containing a transaction date
     */
    protected String getTransactionDate()
    {
        // Instantiate a Date object that also contains the current date and time
        Date date = new Date();

        // A SimpleDateFormat instance can be used for formatting 
        // the string representation of a date (year-month-day hour:minute:second)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Use the format method to create a string 
        // representation of a date with the above defined format.           
        String transactionDate = dateFormat.format(date);  
             
        return transactionDate;
    }
    
    /**
     * Adds a transaction to the account's transaction history
     * 
     * @param transaction - transaction to add to the list
     */
    protected void setTransaction(String transaction)
    {
        transactions.add(transaction);
    }
    
    /**
     * Provides the account's transactions
     * 
     * @return a list of strings containing all the account's transactions
     */
    public ArrayList<String> getTransactions()
    {
        return transactions;
    }

    /**
     * Provides presentation information about the account
     * 
     * @return string containing information about the account
     */
    public String toString()
    {
        return Integer.toString(accountId) + " " + Double.toString(balance) + " " + getAccountType() + " "
                + Double.toString(interestRate);
    }
    
    /****************************************************
     *  Declaration of abstract methods
     ****************************************************/
    
    /**
     * Provides the type of the account
     * 
     * @return string containing the type of the account
     */
    protected abstract String getAccountType();
      
    /**
     * Withdraws the amount from the account if condition met
     * 
     * @param amount - the amount to withdraw
     * @return true if amount is withdrawn otherwise false
     */
    public abstract boolean withdraw(double amount);
    
    /**
     * Provides the amount of interest
     * 
     * @return amount of interest
     */
    public abstract double getInterest();
}
