package ch.judos.mcmod.potions;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import ch.judos.mcmod.TutorialMod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OnEntityLivingHook {

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		// entityLiving in fact refers to EntityLivingBase so to understand
		// everything about this part go to EntityLivingBase instead
		if (event.entityLiving.isPotionActive(TutorialMod.potionTest.id)) {
			if (event.entityLiving.worldObj.rand.nextInt(20) == 0) {

				event.entityLiving.attackEntityFrom(DamageSource.generic, 1);
				if (event.entityLiving.isDead) {
					EntityZombie entityzombie =
							new EntityZombie(event.entityLiving.worldObj);
					entityzombie.copyLocationAndAnglesFrom(event.entityLiving);
					// entityzombie.func_110161_a((EntityLivingData)null);
					// entityzombie.func_82187_q();

					event.entityLiving.worldObj
							.removeEntity(event.entityLiving);
					event.entityLiving.worldObj
							.spawnEntityInWorld(entityzombie);

				}
			}
			if (event.entityLiving
					.getActivePotionEffect(TutorialMod.potionTest)
					.getDuration() == 0) {
				event.entityLiving
						.removePotionEffect(TutorialMod.potionTest.id);
			}
		}
	}
}
