package VirtualWallet;

import java.sql.Timestamp;

/**Transaction class store the information related to user transaction(unique TransactionId,TransactionType
 * (debit or credit),Transaction timestamp) 
 * and transactions history 
 * @author satnam
 */
public class Transaction {
	private static int transactionId=100;
	private String transactionType;
	private Timestamp timestamp;
	private int balance;
	
	/**Create a transaction with below details:-
	 * @param type if the type is true then its a debit transaction else credit
	 * @param amount A amount of a transaction on a account
	 * @param account on which transaction is happened
	 */
	public Transaction(boolean type,int amount,Accounts account)
	{
		if(type)
		{
			this.setTransactionType("Debit");
		}
		else {
			this.setTransactionType("Credit");
		}
		transactionId=transactionId++;
		setTimestamp(new Timestamp(System.currentTimeMillis()));
		this.setBalance(account.getBalance());
	}

	/**debit or credit transaction
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**At what time transaction happened
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**Set the transaction time
	 * @param timestamp set the timestamp of a transaction
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**get the remaining balance in the account
	 * @return the balance 
	 */
	public int getBalance() {
		return balance;
	}

	/**set the remaining balance in the account
	 * @param balance set the balance of a transaction
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}


	
}
