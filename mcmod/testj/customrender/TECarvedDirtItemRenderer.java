package testj.customrender;

import org.lwjgl.opengl.GL11;

import testj.TutorialMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;

public class TECarvedDirtItemRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (Block.getBlockFromItem(item.getItem()) == TutorialMod.blockCarvedDirt) {
			if (type == ItemRenderType.INVENTORY)
				return true;
		}

		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (Block.getBlockFromItem(item.getItem()) == TutorialMod.blockCarvedDirt) {
			if (type == ItemRenderType.INVENTORY) {
				GL11.glPushMatrix();
//				GL11.glRotatef(225, 0.0F, 1.0F, 0.0F);
//				GL11.glRotatef(45, -1.0F, 0.0F, -1.0F);
//				GL11.glScalef(0.6F, 0.6F, 0.6F);
//				GL11.glTranslatef(0.0F, -0.2F, 0.0F);
				
				TECarvedDirtRenderer.renderBlock(new IConnecting() {
					@Override
					public boolean connectsTo(ForgeDirection dir) {
						return false;
					}
				});
				
				GL11.glPopMatrix();
			}
		}
	}

}
