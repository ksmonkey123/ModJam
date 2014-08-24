package ch.judos.at.station.gondola;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;

public class ItemRendererGondola implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		return stack.getItem() == ATMain.gondola;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if (stack.getItem() != ATMain.gondola)
			return;

		GL11.glPushMatrix();
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		RenderHelper.disableStandardItemLighting();

		final float degreeToRadianF = (float) (Math.PI / 180);

		RendererGondola.renderGondola(degreeToRadianF * 90, degreeToRadianF * 20, null, 0);

		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}

}
