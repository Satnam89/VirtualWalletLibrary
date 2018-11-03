package VirtualWallet;

import java.util.ArrayList;

/**Wallet is a Interface which is used by the user class to create a one wallet per user;
 * @author satnam
 */
public interface Wallet {
	
	public void createAccount();
	public void createAccount(int amount);
	public ArrayList<Accounts> getAccounts();
}
