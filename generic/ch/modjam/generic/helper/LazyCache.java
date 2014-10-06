package ch.modjam.generic.helper;

import net.minecraft.world.World;

/**
 * stores only the key with which the object itself can be loaded from the world.<br>
 * the object is then loaded on use and stored for efficiency.<br>
 * This is done because the world object or single chunks might not be available at any time.
 * 
 * @author j
 *
 * @param <K>
 * @param <V>
 */
public abstract class LazyCache<K, V> {

	private K	keyX;
	private V	value;

	public void setKey(K k) {
		this.keyX = k;
		this.value = null; // force reload of value
	}

	public K getKey() {
		return this.keyX;
	}

	public V getValue(World w) {
		if (this.value != null)
			return this.value;
		if (w == null)
			return null;
		if (this.keyX == null)
			return null;
		this.value = this.loadValue(w, this.keyX);
		return this.value;
	}

	/**
	 * @param w will not be null
	 * @param key
	 * @return
	 */
	protected abstract V loadValue(World w, K key);

}
