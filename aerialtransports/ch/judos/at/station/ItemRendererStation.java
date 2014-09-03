package ch.judos.at.station;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;

public class ItemRendererStation implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() == Item.getItemFromBlock(ATMain.station);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if (Block.getBlockFromItem(stack.getItem()) != ATMain.station)
			return;

		GL11.glPushMatrix();
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		RenderHelper.disableStandardItemLighting();

		final float degreeToRadianF = (float) (Math.PI / 180);
		float rot = degreeToRadianF * 180;
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			rot = 90;

		RendererStation.renderStation(rot, degreeToRadianF * 0, 0);

		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}

}
