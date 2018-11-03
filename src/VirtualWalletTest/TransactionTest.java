package VirtualWalletTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import VirtualWallet.Accounts;
import VirtualWallet.Transaction;

public class TransactionTest {

	@Test
	void debittransactionTest() {
		
		Accounts account=new Accounts();
		Transaction transaction1=new Transaction(true,100,account);
		
		assertEquals("Debit",transaction1.getTransactionType());
		
	}
	@Test
	void credittransactionTest() {
		Accounts account=new Accounts();
		Transaction transaction2=new Transaction(false,50,account);
		assertEquals("Credit",transaction2.getTransactionType());
	}
	
	@Test
	void transactionBalanceTest() {
		Accounts account=new Accounts(100);
		Transaction transaction=new Transaction(false,50,account);
		assertEquals(100,transaction.getBalance());
	}
	

}
