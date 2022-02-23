package uk.co.dotcode.customvillagertrades.events;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.dotcode.customvillagertrades.CommandRefresh;

public class MyCommandEvent {

	@SubscribeEvent
	public void imcProcess(final RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

		CommandRefresh.register(dispatcher);
	}

}
