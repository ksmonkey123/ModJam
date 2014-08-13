package ch.judos.at.te;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import ch.judos.at.ModMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.lib.ATReferences;
import ch.modjam.generic.tileEntity.GenericTileEntity;

public class TEStation extends GenericTileEntity {

	public EntityPlayer	connectTo;

	@Override
	public void tick() {}

	@Override
	public void writeNBT(NBTTagCompound tag) {}

	@Override
	public void readNBT(NBTTagCompound tag) {}

	public static String getTextureName() {
		return ATReferences.MOD_ID + ":textures/blocks/" + ATNames.station + ".png";
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		super.getRenderBoundingBox()
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1,
			this.yCoord + 2, this.zCoord + 1);
	}

	public void onBlockActivated(EntityPlayer player) {
		ModMain.logger.error(player);
		this.connectTo = player;
		if (this.worldObj.isRemote)
			player.addChatMessage(new ChatComponentText("Connected rope to player " + player
				.getCommandSenderName()));
	}

}
