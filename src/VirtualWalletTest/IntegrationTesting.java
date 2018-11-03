package VirtualWalletTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import VirtualWallet.Accounts;
import VirtualWallet.InsufficientBalanceException;
import VirtualWallet.User;
import VirtualWallet.Wallet;

public class IntegrationTesting {

	@Test
	void integrationDepositTest() {
		User user=new User("fake user");
		Wallet wallet=user.getWallet();
		assertNotNull(wallet);
		wallet.createAccount();
		List<Accounts> accounts=wallet.getAccounts();
		assertNotNull(accounts);
		Accounts account=accounts.get(0);
		account.deposit(50);
		
		assertEquals(50, account.getBalance());
	}
	
	@Test
	void integrationwithdrawalTest() throws InsufficientBalanceException {
		User user=new User("fake user");
		Wallet wallet=user.getWallet();
		wallet.createAccount();
		List<Accounts> accounts=wallet.getAccounts();
		Accounts account=accounts.get(0);
		account.deposit(500);
		account.withdrawal(100);
		assertEquals(400, account.getBalance());
	}
	
	@Test
	void integrationTransferTest() throws InsufficientBalanceException {
		User user=new User("fake user");
		Wallet wallet=user.getWallet();
		wallet.createAccount();
		wallet.createAccount();
		List<Accounts> accounts=wallet.getAccounts();
		Accounts account1=accounts.get(0);
		Accounts account2=accounts.get(1);
		account1.deposit(500);
		account1.transferFunds(100, account2);
		assertEquals(100, account2.getBalance());
		assertEquals(400, account1.getBalance());
	}

}
