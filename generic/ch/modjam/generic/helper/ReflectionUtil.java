package ch.modjam.generic.helper;

import java.lang.reflect.Field;

/**
 * @author judos
 * 
 */
public class ReflectionUtil {

	/**
	 * @param c
	 * @param fieldName
	 * @param fieldNameObj
	 * @return the value of the static field
	 */
	public static Object getStaticField(Class<?> c, String fieldName, String fieldNameObj) {
		try {
			Field field = null;
			try {
				field = c.getDeclaredField(fieldName);
			} catch (Exception e) {
				field = c.getDeclaredField(fieldNameObj);
			}

			if (field != null) {
				field.setAccessible(true);
				return field.get(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param instance
	 * @param fieldName
	 * @param fieldNameObf
	 * @return the value of the field
	 */
	public static Object getInstanceField(Object instance, String fieldName, String fieldNameObf) {
		try {
			Class<?> c = instance.getClass();
			Field field = null;
			try {
				field = c.getDeclaredField(fieldName);
			} catch (Exception e) {
				field = c.getDeclaredField(fieldNameObf);
			}

			if (field != null) {
				field.setAccessible(true);
				return field.get(instance);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
