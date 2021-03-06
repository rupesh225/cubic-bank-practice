package com.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author javahunk This is my JUnit class
 *
 */
public class PalindromeTest {

	private static final String PALINDROME = "madam";
	private static final String NOT_PALINDROME = "nagen";

	private Palindrome palindrome = new Palindrome();

	@Test
	public void testWhenPalindomeInput() {
		boolean result = palindrome.isPal(PALINDROME);
		assertEquals(true, result);
	}

	@Test
	public void testWhenNotPalindomeInput() {
		boolean result = palindrome.isPal(NOT_PALINDROME);
		assertEquals(false, result);
	}

	@Test(expected = RuntimeException.class)
	public void testWhenPalindomeInputLenZero() {
		palindrome.isPal("");
	}

	@Test(expected = RuntimeException.class)
	public void testWhenPalindomeInputLenOne() {
		palindrome.isPal("a");
	}

}
