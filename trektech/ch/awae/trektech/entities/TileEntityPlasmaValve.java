package ch.awae.trektech.entities;

import java.nio.ByteBuffer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;

/**
 * This valve limits flow-through pressure to 10bar
 * 
 * TODO: make pressure level customizable
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class TileEntityPlasmaValve extends TileEntityPlasmaPipe {

	private float pressureThreshold;

	/**
	 * Full-Fledged Constructor
	 * 
	 * @param plasmaType
	 *            the plasma Type for this valve
	 * @param pressureThreshold
	 *            the pressure to block at
	 */
	public TileEntityPlasmaValve(EnumPlasmaTypes plasmaType,
			float pressureThreshold) {
		super(plasmaType, null, 0);
		this.pressureThreshold = 5f;
	}

	/**
	 * raw constructor. for reconstruction from save-file only
	 */
	public TileEntityPlasmaValve() {
	}

	@Override
	public int getMaxAcceptance(EnumPlasmaTypes plasma, ForgeDirection direction) {
		return (int) ((this.pressureThreshold - (this.getParticleCount(plasma,
				direction) / PARTICLES_PER_BAR)) * PARTICLES_PER_BAR);
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		super.writeNBT(tag);
		tag.setFloat("Treshold", this.pressureThreshold);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		super.readNBT(tag);
		this.pressureThreshold = tag.getFloat("Treshold");
	}

	/**
	 * @return the pressure threshold
	 */
	public float getPressureThreshold() {
		return this.pressureThreshold;
	}

	/**
	 * set the pressure threshold
	 * 
	 * @param threshold
	 *            the new threshold
	 */
	public void setPressureThreshold(float threshold) {
		this.pressureThreshold = threshold;
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			this.sendNetworkCommand("set",
					ByteBuffer.allocate(4).putFloat(this.pressureThreshold)
							.array());
		this.markDirty();
	}

	@Override
	public void onNetworkCommand(String command, byte[] data) {
		if (command.equals("set")) {
			this.setPressureThreshold(ByteBuffer.wrap(data).getFloat());
		}
	}

	@Override
	public boolean onWrench(EntityPlayer player) {
		if (this.worldObj.isRemote && !player.isSneaking()) {
			player.addChatMessage(new ChatComponentText(
					"Current Plasma Pressure Threshold: "
							+ this.pressureThreshold + " bar"));
			return true;
		}
		return false;
	}

}
