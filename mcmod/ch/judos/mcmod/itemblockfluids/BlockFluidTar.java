package ch.judos.mcmod.itemblockfluids;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

@SuppressWarnings("javadoc")
public class BlockFluidTar extends BlockFluidClassic {

	public BlockFluidTar(Fluid f) {
		super(f, Material.water);
		this.setCreativeTab(TutorialMod.modTab);
		this.setBlockName(Names.TarBlock);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.TarBlock);
		this.setHardness(0.5f);
		this.setResistance(0.5f);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z,
			Entity entity) {

		// if (world.isRemote)
		// return;

		if (entity instanceof EntityPlayer || entity instanceof EntityMob
				&& !((EntityLiving) entity).isEntityUndead()) {
			EntityLivingBase ent = (EntityLivingBase) entity;
			System.out.println(ent);
			System.out.println(ent.motionX + "," + ent.motionY + ","
					+ ent.motionZ);
			ent.motionX *= 0.2;
			ent.motionZ *= 0.2;
			ent.motionY *= 0.2;

			// ent.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 0,
			// 3));
		}
		super.onEntityCollidedWithBlock(world, x, y, z, entity);

	}

}
