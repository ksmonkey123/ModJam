package testj.customrender;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import testj.TutorialMod;
import testj.lib.ClientProxy;

public class TECarvedDirtItemRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return Block.getBlockFromItem(item.getItem()) == TutorialMod.blockCarvedDirt;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (Block.getBlockFromItem(item.getItem()) == TutorialMod.blockCarvedDirt) {
			GL11.glPushMatrix();
			// GL11.glRotatef(225, 0.0F, 1.0F, 0.0F);
			// GL11.glRotatef(45, -1.0F, 0.0F, -1.0F);
			// GL11.glScalef(0.6F, 0.6F, 0.6F);
			if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type==ItemRenderType.EQUIPPED)
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			RenderHelper.disableStandardItemLighting();
			
			ClientProxy.renderer.renderBlock(new IConnecting() {
				@Override
				public boolean connectsTo(ForgeDirection dir) {
					return false;
				}
			});
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
	}

}
