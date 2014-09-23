/*
 * The MIT License (MIT) Copyright (c) 2014 Ordinastie Changes made 2014 judos Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions: The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED
 * "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ch.modjam.generic.rendering;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.IIcon;
import ch.modjam.generic.blocks.BlockCoordinates;
import cpw.mods.fml.relauncher.ReflectionHelper;

public abstract class BaseTESRenderer extends TileEntitySpecialRenderer {

	public static boolean								isObfEnv				= !(Boolean) Launch.blackboard
																					.get("fml.deobfuscatedEnvironment");

	// Reference to Minecraft.renderGlobal.damagedBlocks (lazy loaded)
	private static Map<Integer, DestroyBlockProgress>	damagedBlocks;
	protected static IIcon[]							damagedIcons;

	/**
	 * Get the damage for the block (for TESR)
	 */
	@Deprecated
	protected boolean									getBlockDamage			= false;
	/**
	 * Current block destroy progression (for TESR)
	 */
	@Deprecated
	protected DestroyBlockProgress						destroyBlockProgress	= null;

	/**
	 * Gets and hold reference to damagedBlocks from Minecraft.renderGlobal via reflection.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected void loadDamagedBlocks() {
		if (damagedBlocks != null)
			return;
		try {
			Field modifiers = Field.class.getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			Field f = ReflectionHelper.findField(RenderGlobal.class,
				isObfEnv ? "field_94141_F" : "destroyBlockIcons");
			modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			damagedIcons = (IIcon[]) f.get(Minecraft.getMinecraft().renderGlobal);
			f = ReflectionHelper.findField(RenderGlobal.class,
				isObfEnv ? "field_72738_E" : "damagedBlocks");
			modifiers.setInt(f, f.getModifiers());
			damagedBlocks = (HashMap<Integer, DestroyBlockProgress>) f
				.get(Minecraft.getMinecraft().renderGlobal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Gets the destroy block progress for this rendering. Only used for TESR.
	 *
	 * @return
	 */
	protected DestroyBlockProgress getBlockDestroyProgress(BlockCoordinates coord) {
		// if (renderType != TYPE_TESR_WORLD)
		// return null;
		loadDamagedBlocks();
		if (damagedBlocks == null || damagedBlocks.isEmpty())
			return null;
		Iterator<DestroyBlockProgress> iterator = damagedBlocks.values().iterator();
		while (iterator.hasNext()) {
			DestroyBlockProgress dbp = iterator.next();
			if (coord.equalCoordinates(dbp))
				return dbp;
		}
		return null;
	}

}
