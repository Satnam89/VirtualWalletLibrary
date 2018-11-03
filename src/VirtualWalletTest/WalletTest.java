package VirtualWalletTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import VirtualWallet.User;
import VirtualWallet.Wallet;

public class WalletTest {

	@Test
	void testCreateAccounts() {
		User user1=new User("Fake User");
		Wallet wallet=user1.getWallet();
		wallet.createAccount();
		assertEquals(1,wallet.getAccounts().size() );
		
		wallet.createAccount();
		assertEquals(2,wallet.getAccounts().size() );
		
		User user2=new User("Fake User2");
		Wallet wallet2=user2.getWallet();
		wallet2.createAccount(100);
		assertEquals(1,wallet2.getAccounts().size() );
	}

}
