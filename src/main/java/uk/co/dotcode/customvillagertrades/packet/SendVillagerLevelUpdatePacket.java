package uk.co.dotcode.customvillagertrades.packet;

import java.util.UUID;

import net.minecraft.network.PacketBuffer;

public class SendVillagerLevelUpdatePacket {

	public final UUID uuid;
	public final int data;

	public SendVillagerLevelUpdatePacket(PacketBuffer buf) {
		this.uuid = buf.readUUID();
		this.data = buf.readInt();

	}

	public SendVillagerLevelUpdatePacket(UUID uuid, int data) {
		this.uuid = uuid;
		this.data = data;
	}

	public void encode(PacketBuffer buf) {
		buf.writeUUID(uuid);
		buf.writeInt(data);
	}
}
