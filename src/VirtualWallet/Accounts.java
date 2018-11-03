
package VirtualWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Accounts class handle accounts related queries for a user like deposit,withdrawal,transfer,last n transactions.
 * @author satnam
 *
 */
public class Accounts {
	private static int uniqueNumber=1000;
	private int balance;
	private int accountNumber;
	private Map<Integer,ArrayList<Transaction>> transactions;
	
	/** Initialize account with a zero balance
	 * and with unique account number
	 */
	public Accounts()
	{	
		this.setBalance(0);
		accountNumber=uniqueNumber++;
		this.transactions=new HashMap<>();
	}
	/**Initialize account with a amount balance
	 * 
	 * @param amount Intial amount in the account
	 */
	public Accounts(int amount)
	{
		this.setBalance(amount);
		accountNumber=uniqueNumber++;
		this.transactions=new HashMap<>();
	}
	
	/**Adding certain amount in the account
	 * @param amount certain amount to add.
	 */
	public synchronized void deposit(int amount)
	{
		int currentBalance=this.getBalance();
		this.setBalance(currentBalance+amount);

		Transaction transaction = new Transaction(true, amount, this);
		ArrayList<Transaction> list=new ArrayList<>();
		if(transactions.containsKey(accountNumber))
		{
			list=transactions.get(accountNumber);
			list.add(transaction);
			this.transactions.put(accountNumber,list);
		}
		else {
			list.add(transaction);
			this.transactions.put(accountNumber,list);
		}
	}
	
	/**Withdraw a certain amount from the account
	 * @param amount certain amount to withdraw.
	 */
	public synchronized void withdrawal(int amount) throws InsufficientBalanceException
	{
		int currentBalance=this.getBalance();
		if(amount<currentBalance)
		{
			this.setBalance(currentBalance-amount);
			Transaction transaction = new Transaction(false, amount, this);
			ArrayList<Transaction> list=new ArrayList<>();
			if(transactions.containsKey(accountNumber))
			{
				list=transactions.get(accountNumber);
				list.add(transaction);
				this.transactions.put(accountNumber,list);
			}
			else {
				list.add(transaction);
				this.transactions.put(accountNumber,list);
			}
		}
		else {
			throw new InsufficientBalanceException("Insufficient balance in the account");
		}	
	}
	
	/** Transfer funds from one account to another.
	 * @param amount to transfer
	 * @param account target account.
	 * @throws InsufficientBalanceException if account has insufficient balance
	 */
	public void transferFunds(int amount,Accounts account) throws InsufficientBalanceException
	{
		if(this.getBalance() > amount)
		{
			this.withdrawal(amount);;
			account.deposit(amount);
		}
		else {
			throw new InsufficientBalanceException("Insufficient balance in the account for Transfer");
		}
	}
	
	/**Display the list of last n transactions
	 * @param account 
	 * @param n last n Transaction for an account
	 * @return list of latest  n Transaction for an account
	 * @throws InsufficientBalanceException if account has insufficient balance while transferring
	 */
	
	public synchronized List<Transaction> nTransactions(Accounts account,int n) throws InsufficientTransactions
	{
		if(transactions.containsKey(account.getAccount()))
		{
		List<Transaction> transactionList=new ArrayList<>();
		ArrayList<Transaction> ntransactions=new ArrayList<>();
		transactionList=transactions.get(account.getAccount());
			
			if(transactionList.size()>=n)
			{
				int max=Math.max(transactionList.size(), n);
				int min=Math.min(transactionList.size(), n);
				int start =max-min;
				for (int i = start; i < transactionList.size(); i++) {
				ntransactions.add(transactionList.get(i));
			}
				return ntransactions;
			}
			else {
				throw new InsufficientTransactions("Insufficient Transactions in our account to get");
			}
		}
		else {
			return null;
		}
	}
	
	/**
	 * @return the unique accountNumber for an account
	 */
	public int getAccount() {
		return accountNumber;
	}
	
	
	/**
	 * @return the transactions for an account
	 */
	public Map<Integer,ArrayList<Transaction>> getTransactions() {
		return transactions;
	}
	
	/**
	 * @param set the transactions for a account
	 */
	public void setTransactions(Map<Integer,ArrayList<Transaction>> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the balance for an account
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * @param balance set the balance for a account
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
