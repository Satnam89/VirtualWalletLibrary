package VirtualWalletTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import VirtualWallet.Accounts;
import VirtualWallet.InsufficientBalanceException;
import VirtualWallet.InsufficientTransactions;
import VirtualWallet.Transaction;

public class Accountstest {

	@Test
	void testAccount() {
		Accounts account=new Accounts();
		assertEquals(account.getBalance(), 0);
		assertEquals(1001,account.getAccount());
		assertNotNull(account.getTransactions());
		
		Accounts account2=new Accounts(10);
		assertEquals( 10,account2.getBalance());
	}
	
	@Test
	void testdeposit() {
		Accounts account=new Accounts(50);
		account.deposit(100);
		assertEquals( 150,account.getBalance());
	}
	
	@Test
	void testwithdrawal() throws InsufficientBalanceException {
		Accounts account=new Accounts(50);
		account.deposit(100);
		account.withdrawal(50);
		assertEquals( 100,account.getBalance());
		
	}

	@Test
	public void TestInsufficientBalanceException() {
		Accounts account=new Accounts(50);
		account.deposit(100);
	    assertThrows(InsufficientBalanceException.class, () -> {
	    	account.withdrawal(151);
	    });
	}
	
	@Test
	void testTransfer() throws InsufficientBalanceException{
		Accounts source=new Accounts(50);
		Accounts target=new Accounts();
		source.transferFunds(20, target);
		assertEquals( 30,source.getBalance());
		assertEquals( 20,target.getBalance());
		
	}
	
	@Test
	void testnTransactions() throws InsufficientBalanceException, InsufficientTransactions
	{
		Accounts account=new Accounts(100);
		account.deposit(20);
		account.withdrawal(50);
		account.deposit(20);
		account.deposit(20);
		account.deposit(20);
		account.deposit(200);
		account.deposit(20);
		account.deposit(20);
		account.deposit(200);
		account.withdrawal(50);
		List<Transaction> transactions=account.nTransactions(account, 5);
		assertEquals(5,transactions.size());
		
	}
	
	@Test
	public void TestInsufficientTransactions() throws InsufficientTransactions, InsufficientBalanceException {
		Accounts account=new Accounts(100);
		account.deposit(20);
		account.withdrawal(50);
		account.deposit(20);
		account.deposit(20);
		account.deposit(20);
		
	    assertThrows(InsufficientTransactions.class, () -> {
	    	account.nTransactions(account, 10).size();
	    });
	}
	
	@Test
	public void testConcurrentDeposit() throws InterruptedException, ExecutionException 
	{
		Accounts account=new Accounts(100);
		CountDownLatch latch = new CountDownLatch(1);
		AtomicBoolean running = new AtomicBoolean();
		AtomicInteger overlaps = new AtomicInteger();
		int threads=10;
		ExecutorService service =
			      Executors.newFixedThreadPool(threads);
		
		Collection<Future<Integer>> futures =
		  new ArrayList<>(threads);
		for (int t = 0; t < threads; ++t) {
		  futures.add(
		    service.submit(
		      () -> {
		        latch.await();
		        if (running.get()) {
		          overlaps.incrementAndGet();
		        }
		        running.set(true);
		        account.deposit(10);
		        
		        running.set(false);
		        return account.getBalance();
		      }
		    )
		  );
		}
		latch.countDown();
		List<Integer> list=new ArrayList<>();
		for (Future<Integer> f : futures) {
		  list.add(f.get());
		}
		assertEquals(200,account.getBalance());
	}

@Test
public void testConcurrentWithdrawal() throws InterruptedException, ExecutionException
{
	Accounts account=new Accounts(1000);
	CountDownLatch latch = new CountDownLatch(1);
	AtomicBoolean running = new AtomicBoolean();
	AtomicInteger overlaps = new AtomicInteger();
	int threads=10;
	ExecutorService service =
		      Executors.newFixedThreadPool(threads);
	
	Collection<Future<Integer>> futures =
	  new ArrayList<>(threads);
	for (int t = 0; t < threads; ++t) {
	  futures.add(
	    service.submit(
	      () -> {
	        latch.await();
	        if (running.get()) {
	          overlaps.incrementAndGet();
	        }
	       try {
	    	   account.withdrawal(50);
		} catch (InsufficientBalanceException ie) {
			System.out.println(ie.getMessage());
		} running.set(true);
	       
	        
	        running.set(false);
	        return account.getBalance();
	      }
	    )
	  );
	}
	latch.countDown();
	List<Integer> list=new ArrayList<>();
	for (Future<Integer> f : futures) {
	  list.add(f.get());
	}
	assertEquals(500,account.getBalance());
}

@Test
public void testConcurrentTransfer() throws InterruptedException, ExecutionException
{
	Accounts account1 = new Accounts(1000);
	Accounts account2 = new Accounts(1000);		
	final CountDownLatch latch = new CountDownLatch(1);
	final CountDownLatch doneSignal = new CountDownLatch(20);
	//account1 transfer to account2
	Runnable runner1 = new Runnable(){
	      public void run() {
	        try {
	          latch.await();
	          account1.transferFunds(1, account2);
	          doneSignal.countDown();
	        } catch (InterruptedException | InsufficientBalanceException ie) { 
	        	System.out.println(ie.getMessage());}
	      }
	    };
	 //account2 transfer to account1
	 Runnable runner2 = new Runnable(){
		      public void run() {
		        try {
		          latch.await();
		          account2.transferFunds(1,account1);
		          doneSignal.countDown();
		        } catch (InterruptedException | InsufficientBalanceException ie) { 
		        	System.out.println(ie.getMessage());
		        	}
		      }
		    };
		    
		 for (int i=0; i<10; i++) {	    
			 new Thread(runner1, "TestThread"+i).start();
		}
			  
		 for (int i=0; i<10; i++) {	 
		    new Thread(runner2, "TestThread"+i).start();
		  }
	   
	   latch.countDown();
	   doneSignal.await();
	   assertEquals(1000,account1.getBalance());
	   assertEquals(1000,account2.getBalance());
}
}
