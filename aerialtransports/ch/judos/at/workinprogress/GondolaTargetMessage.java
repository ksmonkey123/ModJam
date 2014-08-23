package ch.judos.at.workinprogress;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import ch.modjam.generic.Vec3Helper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class GondolaTargetMessage implements IMessage {

	private Vec3	start;
	private Vec3	end;
	private int		entityId;

	public GondolaTargetMessage() {}

	public GondolaTargetMessage(Vec3 start, Vec3 end, Entity entity) {
		this.start = start;
		this.end = end;
		this.entityId = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound data = ByteBufUtils.readTag(buf);
		this.start = Vec3Helper.readVecFromNBT(data, "start");
		this.end = Vec3Helper.readVecFromNBT(data, "end");
		this.entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound data = new NBTTagCompound();
		Vec3Helper.writeVecToNBT(data, this.start, "start");
		Vec3Helper.writeVecToNBT(data, this.end, "end");
		ByteBufUtils.writeTag(buf, data);
		buf.writeInt(this.entityId);
	}

	public static class GondolaTargetHandler implements
			IMessageHandler<GondolaTargetMessage, GondolaTargetMessage> {

		@Override
		public GondolaTargetMessage onMessage(GondolaTargetMessage message, MessageContext ctx) {

			return null;
		}

	}
}
