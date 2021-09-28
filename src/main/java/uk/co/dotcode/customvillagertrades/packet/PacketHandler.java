package uk.co.dotcode.customvillagertrades.packet;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import uk.co.dotcode.customvillagertrades.BaseClass;

public class PacketHandler {

	public static int index = 0;

	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(BaseClass.MODID, "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

	public static void register() {
		HANDLER.registerMessage(index++, SendVillagerLevelUpdatePacket.class, SendVillagerLevelUpdatePacket::encode,
				SendVillagerLevelUpdatePacket::new, PacketHandler::handleVillagerLevelUpdate);
	}

	public static void sendToClient(Object message, ServerPlayerEntity player) {
		HANDLER.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToServer(Object message) {
		HANDLER.sendToServer(message);
	}

	public static void handleVillagerLevelUpdate(SendVillagerLevelUpdatePacket message,
			Supplier<NetworkEvent.Context> ctx) {
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
			ctx.get().enqueueWork(() -> {
				UUID uuid = message.uuid;
				Entity e = ctx.get().getSender().getLevel().getEntity(uuid);

				if (e != null && e instanceof VillagerEntity) {
					VillagerEntity v = (VillagerEntity) e;
					int data = message.data;
					v.setVillagerXp(getXpToSet(v.getVillagerData().getLevel() + data));
					v.notifyTrade(v.getOffers().get(0)); // I have to do this, because there's no other way.
				}
			});
		} else if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			// Do nothing at all, because why would the client even be allowed to modify
			// this data? That makes no sense at all!
		}
		ctx.get().setPacketHandled(true);
	}

	private static int getXpToSet(int targetLevel) {
		switch (targetLevel) {
		case 1:
			return 0;
		case 2:
			return 11;
		case 3:
			return 71;
		case 4:
			return 151;
		case 5:
			return 251;
		default:
			return 0;
		}
	}
}
