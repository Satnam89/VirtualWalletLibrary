package VirtualWalletTest;

import static org.junit.Assert.*;

import org.junit.Test;

import VirtualWallet.User;
import VirtualWallet.Wallet;

public class UserTest {

	@Test
	public void testGetName() {
		User user=new User("John");
		assertEquals("John",user.getName());
	}
	
	@Test
	public void testGetUser() {
		User user=new User("Ponting");
		assertEquals(2,user.getUserId());
		User user2=new User("Ponting");
		assertEquals(3,user2.getUserId());
	}
	
	@Test
	public void testgetwallet() {
		User user=new User("Ponting");
		Wallet wallet=user.getWallet();
		assertNotNull(wallet);
	}

}
