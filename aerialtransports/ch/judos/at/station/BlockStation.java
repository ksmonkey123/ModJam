package ch.judos.at.station;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.items.ItemRope;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.rendering.customRenderer.RenderType;

public class BlockStation extends BlockContainer {

	public BlockStation() {
		super(Material.wood);
		this.setBlockName(ATNames.station);
		this.setBlockTextureName(ATMain.MOD_ID + ":" + ATNames.station);
		this.setCreativeTab(ATMain.modTab);
		this.setHardness(0.3f);
		this.setHarvestLevel("axe", 0);
		// this.setLightOpacity(0);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TEStation) {
			TEStation station = (TEStation) tileEntity;

			ItemStack held = player.getHeldItem();
			if (held != null && ATMain.ropeOfStation == held.getItem()) {
				if (connectStationToStation(world, station, held, player))
					return true;
			}
			return GenericGuiHandler.openGUI(player, world, x, y, z);
		}
		return false;
	}

	private static boolean connectStationToStation(World w, TEStation stationEnd, ItemStack held,
			EntityPlayer player) {
		if (held.stackTagCompound == null)
			return false;
		int[] pos = held.stackTagCompound.getIntArray(ItemRope.nbtStationCoordinates);
		TileEntity te = w.getTileEntity(pos[0], pos[1], pos[2]);

		if (te == null)
			return false;

		if (te instanceof TEStation) {
			TEStation stationStart = (TEStation) te;
			if (stationStart.isOnTheSameBlockPosition(stationEnd)) {
				if (w.isRemote)
					player.addChatMessage(new ChatComponentText(
						"Can't connect a station to itself."));
			} else {
				int currentSlotHeld = player.inventory.currentItem;
				player.inventory.setInventorySlotContents(currentSlotHeld, null);
				stationStart.finishRopeConnection(stationEnd, player);
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		ATMain.logger.error("breakBlock");
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null || !(te instanceof TEStation))
			return;
		TEStation stationStart = (TEStation) te;

		if (!world.isRemote)
			stationStart.disconnectStation();
		else
			stationStart.disconnectStation();

		super.breakBlock(world, x, y, z, block, meta);
	}

	public IIcon getBlockIcon() {
		return this.blockIcon;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderType.CUSTOM;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TEStation();
	}

}
