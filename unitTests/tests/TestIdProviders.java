package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import ch.modjam.generic.identification.IDProvider;

/**
 * @author judos
 *
 */
public class TestIdProviders {

	/**
	 * 
	 */
	@Test
	public void test() {
		int[] n = { 10, 100, 1000, 10000 };
		for (int highestNumber : n) {
			for (int amountOfTestNumbers : n) {
				test1(highestNumber, amountOfTestNumbers);
			}
		}
	}

	/**
	 * @param highestNumber
	 * @param amountOfTestNumbers
	 * @param args
	 */
	public void test1(int highestNumber, int amountOfTestNumbers) {
		IDProvider f = new IDProvider();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random r = new Random();
		for (int i = 0; i < amountOfTestNumbers; i++) {
			int id = r.nextInt(highestNumber);
			if (!numbers.contains(id)) {
				f.useID(id);
				numbers.add(id);
			}
		}
		// Test that numbers that are used are not free
		for (int nr : numbers) {
			assertTrue(!f.isFreeID(nr));
		}
		// Test that numbers that weren't used are not free
		for (int i = 0; i < amountOfTestNumbers; i++) {
			int id = r.nextInt(highestNumber);
			if (!numbers.contains(id))
				assertTrue(f.isFreeID(id));
		}
	}

}
