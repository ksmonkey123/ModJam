package ch.judos.at.station;

import java.util.Set;

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
import ch.judos.at.station.rope.ItemRope;
import ch.modjam.generic.blocks.BlockCoordinates;
import ch.modjam.generic.blocks.Collision;
import ch.modjam.generic.blocks.Vec3C;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.rendering.customRenderer.RenderType;

public class BlockStation extends BlockContainer {

	public BlockStation() {
		super(Material.wood);
		this.setUnlocalizedName(ATNames.station);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.station + "_particles");
		this.setCreativeTab(ATMain.modTab);
		this.setHardness(0.5f);
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
				Collision c = new Collision(w, true, true);
				Vec3C start = new Vec3C(stationStart.xCoord + 0.5, stationStart.yCoord + 0.5,
					stationStart.zCoord + 0.5);
				Vec3C end = new Vec3C(stationEnd.xCoord + 0.5, stationEnd.yCoord + 0.5,
					stationEnd.zCoord + 0.5);

				// detects liquids and excludes both, start and end stations
				Set<BlockCoordinates> intermediate = c.detectCollisionsInVolume(start, end, true,
					true, 0.3f);
				if (intermediate.isEmpty()) {
					for (BlockCoordinates r : c.getAllBlocks())
						w.setBlock(r.x, r.y, r.z, ATMain.ropeBlock);

					int currentSlotHeld = player.inventory.currentItem;
					player.inventory.setInventorySlotContents(currentSlotHeld, null);
					stationStart.finishRopeConnection(stationEnd, player, c.getAllBlocks());
				} else {
					stationStart.setBlockingBlocks(intermediate);
					if (w.isRemote)
						player.addChatMessage(new ChatComponentText(
							"The rope is blocked, can't connect it."));
				}
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
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
