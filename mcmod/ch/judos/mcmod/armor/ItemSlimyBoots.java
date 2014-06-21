package ch.judos.mcmod.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("javadoc")
public class ItemSlimyBoots extends ItemArmor {

	public static final int ARMOR_HELMET_ID = 0;
	public static final int ARMOR_CHESTPLATE_ID = 1;
	public static final int ARMOR_LEGGINS_ID = 2;
	public static final int ARMOR_BOOTS_ID = 3;

	public static final int EQUIPPED_BOOTS_SLOT_NR = 1;

	public ItemSlimyBoots() {
		super(TutorialMod.armorSlimeMaterial, TutorialMod.slimyBootsID,
				ARMOR_BOOTS_ID);
		this.setMaxDamage(64);
		this.setUnlocalizedName(Names.SlimyBoots);
		this.setTextureName(References.MOD_ID + ":" + Names.SlimyBoots);
		this.setCreativeTab(TutorialMod.modTab);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		// we only have boots here, so we don't care for slot
		return References.MOD_ID + ":textures/models/armor/slimy_layer_1.png";
	}

	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		EntityLivingBase living = event.entityLiving;
		ItemStack equipped = living.getEquipmentInSlot(EQUIPPED_BOOTS_SLOT_NR);
		if (equipped != null) {
			if (equipped.getItem() == this) {

				if (!event.entity.worldObj.isRemote) {
					equipped.damageItem(10, living);
					if (equipped.stackSize == 0) {
						if (living instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) living;
							int slot = player.inventory.mainInventory.length;
							player.inventory.setInventorySlotContents(slot,
									null);
						}
					}
				}
				living.motionY *= 1.4;
			}
		}
	}
}
