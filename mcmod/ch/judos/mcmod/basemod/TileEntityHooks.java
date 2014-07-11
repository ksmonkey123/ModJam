package ch.judos.mcmod.basemod;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;

/**
 * @author judos
 *
 */
public class TileEntityHooks {

	/**
	 * stores all the mappings between tileEntities and listeners
	 */
	public static HashMap<Class<? extends TileEntity>, ArrayList<EntityUpdateListener>>	mapping	= new HashMap<Class<? extends TileEntity>, ArrayList<EntityUpdateListener>>();

	/**
	 * @param cl the class of the tileEntity you want to listen
	 * @param listener
	 */
	public static void registerUpdateListenerForTE(Class<? extends TileEntity> cl,
			EntityUpdateListener listener) {
		if (!mapping.containsKey(cl))
			mapping.put(cl, new ArrayList<EntityUpdateListener>());

		mapping.get(cl).add(listener);
	}

	/**
	 * called when a tileEntity is updated
	 * 
	 * @param te
	 */
	public static void updateEntity(TileEntity te) {
		ArrayList<EntityUpdateListener> list = mapping.get(te.getClass());
		if (list == null)
			return;
		for (EntityUpdateListener x : list)
			x.updateEntity(te);
	}

}
