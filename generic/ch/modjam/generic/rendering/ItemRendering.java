package ch.modjam.generic.rendering;

import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemRendering {

	// public static void render3DItem(ItemStack items,float partialTickTime,boolean rotate) {
	//
	// }

	// public static void render3DItem(EntityItem item, float partialTickTime, boolean rotate) {
	// float time = Minecraft.getMinecraft().theWorld.getTotalWorldTime() + partialTickTime;
	// float bouncetime = time % 63;
	//
	// float rot = 1 * (time % 180) - 90;
	// glPushMatrix();
	// glDepthMask(true);
	// if (rotate) {
	// glRotatef(rot, 0, 1, 0);
	// }
	// item.hoverStart = 0.0F;
	// RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 1.0D, 0.0D, 0, bouncetime);
	// glPopMatrix();
	// }

	public static void render3DItem(ItemStack s, float partialTickTime, boolean rotate) {
		render3DItem(new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0, s),
			partialTickTime, rotate);
	}

	public static void render3DItem(EntityItem item, float partialTickTime, boolean rotate) {
		glPushMatrix();
		glDepthMask(true);
		rotate &= Minecraft.getMinecraft().gameSettings.fancyGraphics;
		if (rotate) {
			float rot = (Minecraft.getMinecraft().theWorld.getTotalWorldTime() + partialTickTime) % 360 * 1.5f;
			glRotatef(rot, 0, 1, 0);
		}
		item.hoverStart = 0.0F;
		// correct some buggy translation offset:
		if (item.getEntityItem().getItem() instanceof ItemBlock)
			RenderManager.instance.renderEntityWithPosYaw(item, 0D, 0.0D, -0.08D, 0f, 0f);
		else
			RenderManager.instance.renderEntityWithPosYaw(item, -0.08D, 0.0D, 0D, 0f, 0f);
		glPopMatrix();
	}
}
