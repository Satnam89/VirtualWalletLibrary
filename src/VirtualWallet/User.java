package VirtualWallet;

/**User class is used to create a user object 
 * single user per wallet
 * @author satnam
 */

public class User {
	private static int uniqueID=1;
	private int userId;
	private String name;
	private Wallet wallet;
	
	/**create a user with a name and unique Id.
	 * One wallet per user
	 * @param name set the name of a user
	 */
	public User(String name)
	{
		this.name=name;
		this.userId=uniqueID++;
		setWallet(new VirtualWalletImp());
	}
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId set the userID for a user
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param uniqueID set the uniqueID for a user
	 */
	public static void setUniqueID(int uniqueID) {
		User.uniqueID = uniqueID;
	}
	
	/**
	 * @return the wallet
	 */
	public Wallet getWallet() {
		return wallet;
	}

	/**
	 * @param wallet set the wallet to for a user
	 */
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}
