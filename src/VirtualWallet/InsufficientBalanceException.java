package VirtualWallet;

/**Exception class to handle withdrawal transaction which makes account balance less than $0
 * @author satnam
 */
public class InsufficientBalanceException extends Exception {
	/**
	 * @param msg alert message for insufficient balance in the account	
	 */
	public InsufficientBalanceException(String msg)
		{
			super(msg);
		}
}
