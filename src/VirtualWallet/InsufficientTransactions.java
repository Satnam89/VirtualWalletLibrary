package VirtualWallet;

/**Exception is throw if account have less than n transactions. 
 * @author satnam
 *
 */
public class InsufficientTransactions  extends Exception{
	public InsufficientTransactions(String msg)
	{
		super(msg);
	}

}
