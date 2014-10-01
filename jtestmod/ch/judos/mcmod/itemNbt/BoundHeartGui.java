package ch.judos.mcmod.itemNbt;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 */
public class BoundHeartGui extends GuiContainer {

	private ItemStack	heart;
	private int			slot;

	/**
	 * @param inventory
	 * @param stack
	 * @param slot
	 */
	public BoundHeartGui(InventoryPlayer inventory, ItemStack stack, int slot) {
		super(new BoundHeartContainer(inventory, stack, slot));
		this.xSize = 176;
		this.ySize = 200;
		this.slot = slot;
		this.heart = stack;

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		try {
			int slotForKey = Integer.parseInt("" + par1) - 1;
			if (slotForKey == this.slot)
				return;
		} catch (Exception e) {
			// escape or some other key was typed, do nothing.
		}
		super.keyTyped(par1, par2);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile." + Names.BoundHeart + ".name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6 + 33, color);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			this.ySize - 128, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			References.MOD_ID + ":textures/gui/" + Names.BoundHeart + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y + 33, 0, 0, this.xSize, this.ySize);

		int slots = this.heart.stackTagCompound.getInteger(BoundHeart.NBT_SLOTS);
		for (int i = 0; i < 5; i++) {
			if (slots - 1 < i)
				this.drawTexturedModalRect(x + 43 + 18 * i, y + 33 + 19, 176, 0, 18, 18);
		}
	}
}
