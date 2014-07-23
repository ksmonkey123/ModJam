import java.util.ArrayList;
import java.util.Random;

import ch.modjam.generic.identification.IDProvider;

/**
 * @author judos
 *
 */
public class Test {

	private static final int	max_nr		= 1000;
	private static final int	test_amount	= 1000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		checkAssertEnabled();

		IDProvider f = new IDProvider();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random r = new Random();
		for (int i = 0; i < test_amount; i++) {
			int id = r.nextInt(max_nr);
			if (!numbers.contains(id)) {
				f.useID(id);
				numbers.add(id);
			}
		}

		for (int nr : numbers) {
			assert (!f.isFreeID(nr));
		}
		for (int i = 0; i < test_amount; i++) {
			int id = r.nextInt(max_nr);
			if (!numbers.contains(id))
				assert (f.isFreeID(id));
		}
		System.out.println("Test run successful");
	}

	private static void checkAssertEnabled() {
		boolean assertionsActive = false;
		try {
			assert (false);
		} catch (AssertionError e) {
			assertionsActive = true;
		}
		if (!assertionsActive)
			throw new RuntimeException("Assertions not enabled by the VM. Add \"-ea\" as argument.");

	}

}
