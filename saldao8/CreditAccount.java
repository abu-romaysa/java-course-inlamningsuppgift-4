package saldao8;

/**
 * This class implements a credit account and it's needed support and management
 * as transactions, account information etc.
 * 
 * @author Salim Daoud, saldao-8
 */

public class CreditAccount extends Account
{
    private double creditLimit;
    private final static String ACCOUNT_TYPE = "Credit account";
    
    /**
     * Constructor
     */
    public CreditAccount()
    {
        super();
        
        this.setInterestRate(0.5);
        creditLimit = 5000;
    }
    
    /* (non-Javadoc)
     * @see saldao8.Account#getInterest()
     */
    public double getInterest()
    {
        if(this.getBalance() < 0)
        {
            return (Math.round(this.getBalance() * (this.getInterestRate() / 100) * 10 / 10.0));
        }
        else
        {
            return (this.getBalance() * (this.getInterestRate() / 100));
        }
    }
    
    /* (non-Javadoc)
     * @see saldao8.Account#withdraw(double)
     */
    public boolean withdraw(double amount)
    {
        double balance = this.getBalance();
        
        // check if the amount isn't exceeding the credit limit
        if((balance - amount) < (0 - creditLimit))
        {
            return false;
        }
        
        balance -= amount;
        this.setBalance(balance);
        
        // add transaction to the transaction history
        this.setTransaction(this.getTransactionDate() + " " + (0 - amount) + " " + balance);
        
        // change the interest rate if the balance has become lower than 0
        if(balance < 0)
        {
            this.setInterestRate(7);
        }
        
        return true;
    }
    
    /* (non-Javadoc)
     * @see saldao8.Account#deposit(double)
     */
    public void deposit(double amount)
    {
        // first run the common code in the superclass
        super.deposit(amount);

        // add specific subclass code that change interest rate if the new balance is 0 or larger
        if(this.getBalance() >= 0)
        {
            this.setInterestRate(0.5);
        }
    }

    /* (non-Javadoc)
     * @see saldao8.Account#getAccountType()
     */
    protected String getAccountType()
    {
        return ACCOUNT_TYPE;
    }
    
    /**
     * Provides the credit limit for the account
     * @return the credit limit
     */
    public double getCreditLimit()
    {
        return creditLimit;
    }
}
