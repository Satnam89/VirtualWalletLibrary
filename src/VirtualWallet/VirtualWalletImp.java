package VirtualWallet;

import java.util.ArrayList;

/**VirtualWalletImp implements Wallet interface
 * This class is used to create list of accounts for a single user.
 * @author satnam
 */
public class VirtualWalletImp implements Wallet{
	
	/** Use Arraylist to store multiple userAccounts
	 */
	private ArrayList<Accounts> accountList;
	
	
	/**Create wallet list to store accountList. 
	 */
	public VirtualWalletImp()
	{
		this.accountList=new ArrayList<>();
	}
	
	
	/**Create a account and add to the account list.
	 */
	public void createAccount() {
		Accounts account=new Accounts();
		this.accountList.add(account);
	}
	/**Create a account with a certain amount in it
	 * @param amount ,Initial amount in the account
	 */
	public void createAccount(int amount) {
		Accounts account=new Accounts(amount);
		this.accountList.add(account);
	}
	
	/**@return a list of accounts.
	 */
	public ArrayList<Accounts> getAccounts(){
		return this.accountList;
	}
}
