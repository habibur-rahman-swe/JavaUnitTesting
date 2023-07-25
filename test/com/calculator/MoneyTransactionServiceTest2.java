/**
 * 
 */
package com.calculator;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.exceptions.NotEnoughMoneyException;

/**
 * 
 */
class MoneyTransactionServiceTest2 {

	private static final String MONEY_AMOUNT_EXCEPTION_MSG = "Money amount should be greater than 0";
	private static final String ACCOUNT_EXCEPTION_MSG = "Accounts shouldn't be null";
	private static final double RANDOM_MONEY_AMOUNT = 100;
	private static final double ZERO_MONEY_AMOUNT = 0;
	private static final double MORE_THAN_RANDOM_MONEY_AMOUNT = 200;
	private static final double NEGATIVE_MONEY_AMOUNT = -1;

	private MoneyTransactionService testInstance;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		testInstance = new MoneyTransactionService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	@DisplayName("Verify money transaction from one account to another")
	void shouldTransferMoneyFromOneAccountToAnother() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
		
		assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount());
		assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount());
	}
	
	@Test
	void should_Throw_Exception_If_Account_From_Is_Null() {
		Account account1 = null;
		Account account2 = new Account(RANDOM_MONEY_AMOUNT);
		
		var exception = assertThrows(IllegalArgumentException.class, () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
		
		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	
	@Test
	void shouldThrowExceptionIfAccountToIsNull() {
		Account account1 = new Account(RANDOM_MONEY_AMOUNT);
		Account account2 = null;
		
		var exception = assertThrows(IllegalArgumentException.class, () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
		
		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}
	
	@Test
	void shouldThorwNotEngoughMoneyExceptionWhenTransferMoreMoney() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		assertThrows(NotEnoughMoneyException.class, () -> testInstance.transferMoney(account1, account2, MORE_THAN_RANDOM_MONEY_AMOUNT));
		
	}
	
	@Test
	void shouldThrowExceptionWhenTransferNegativeAmount() {
		var account1 = new Account();
		var account2 = new Account();
		
		var exception = assertThrows(IllegalArgumentException.class, () -> testInstance.transferMoney(account1, account2, NEGATIVE_MONEY_AMOUNT));
		
		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}
	
	@Test
	void shouldThrowExceptionWhenTransferZeroMoneyAmount() {
		var account1 = new Account();
		var account2 = new Account();
		
		var exception = assertThrows(IllegalArgumentException.class, () -> testInstance.transferMoney(account1, account2, ZERO_MONEY_AMOUNT));
		
		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}
	
	@Test
	void groupedAssertionsExamples() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
		
		
		assertAll("money transaction", 
				() -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
				() -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
				);
	}
	
	@Test
	void dependentAssertionsExample() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		assertAll("Money Transaction", () -> {
			boolean isTransactionSucced = testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
			assertTrue(isTransactionSucced);
			
			assertAll("Money amount is changed on the accounts", 
					() -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
					() -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
			);
		});
	}
	
	@Test
	void testWithTimeoutExample() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		assertTimeout(Duration.ofSeconds(1), () -> 
				testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT)
				);
	}
	
	@Test
	@Timeout(value = 1, unit = TimeUnit.MILLISECONDS)
	void timeoutNotExceededWithResult() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		boolean actualResult = assertTimeout(Duration.ofMillis(1), () -> {
			return testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
		});
		assertTrue(actualResult);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {100, 200, 50, -10})
	void parameterizedTestExample(int moneyAmount) {
		var account1 = new Account(moneyAmount);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		assertTrue(testInstance.transferMoney(account1, account2, moneyAmount));
	}
	
	@ParameterizedTest
	@NullSource
	@EmptySource
	@NullAndEmptySource
	void nullAndEmptySource(String text) {
		assertTrue(text == null || text.trim().isEmpty());
	}
	
	@ParameterizedTest
	@MethodSource("sourceMethod")
	void testMethodSource(String arg) {
		assertNotNull(arg);
	}
	
	static Stream<String> sourceMethod() {
		return Stream.of("John", "Water", "Derek");
	}
	
	@ParameterizedTest
	@CsvSource({
		"apple, 1",
		"banana, 2",
		"'lemon, lime', 0xf1"
	})
	void testWithCsvsource(String fruit, int rank) {
		assertNotNull(fruit);
		assertNotEquals(0, rank);
	}
}
