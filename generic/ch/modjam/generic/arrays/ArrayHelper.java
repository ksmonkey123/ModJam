package ch.modjam.generic.arrays;

/**
 * @author j
 * @source http://stackoverflow.com/a/12635769
 */
public class ArrayHelper {
	/**
	 * @param array
	 * @param value
	 * @return true if the value is contained in the array
	 */
	public static <T> boolean contains(final T[] array, final T value) {
		for (final T e : array)
			if (e == value || value != null && value.equals(e))
				return true;

		return false;
	}
}
