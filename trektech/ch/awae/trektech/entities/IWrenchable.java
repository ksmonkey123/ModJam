package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IWrenchable {

	public boolean wrench(EntityPlayer player);

	public boolean wrenchSneaking(EntityPlayer player);

}
