package uk.co.dotcode.customvillagertrades.events;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.dotcode.customvillagertrades.packet.PacketHandler;
import uk.co.dotcode.customvillagertrades.packet.SendVillagerLevelUpdatePacket;

public class ModifyGuiEvent {

	private boolean upPressed = false;

	private VillagerEntity lastInteractedVillager = null;
	private PlayerEntity player = null;

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getTarget() instanceof VillagerEntity) {
			VillagerEntity villager = (VillagerEntity) event.getTarget();

			lastInteractedVillager = villager;
			player = event.getPlayer();
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void modifyVillagerGui(GuiContainerEvent event) {
		if (event.getGuiContainer() instanceof MerchantScreen) {
			if (player != null && player.isCreative()) {
				if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_UP)) {
					upPressed = true;
				} else if (upPressed) {
					upPressed = false;

					if (lastInteractedVillager.getVillagerData().getLevel() < 5) {
						PacketHandler
								.sendToServer(new SendVillagerLevelUpdatePacket(lastInteractedVillager.getUUID(), 1));
					}
				}
			}
		}
	}
}
