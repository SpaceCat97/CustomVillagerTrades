package uk.co.dotcode.customvillagertrades.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import uk.co.dotcode.customvillagertrades.BaseClass;
import uk.co.dotcode.customvillagertrades.TradeUtil;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.MyWandererTrade;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;
import uk.co.dotcode.customvillagertrades.configs.WandererTradeCollection;

public class MyIMCEvent {

	@SubscribeEvent
	public void imcProcess(final InterModProcessEvent event) {
		LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Looking for extra trades to add...");

		addTradeCollection(event.getIMCStream(CVTMessage.ADD_VILLAGER_TRADES::equals));
		addTradeCollection(event.getIMCStream(CVTMessage.ADD_WANDERER_TRADES::equals));
	}

	private void addTradeCollection(Stream<InterModComms.IMCMessage> messages) {

		List<IMCMessage> messageList = messages.collect(Collectors.toList());

		messageList.forEach(msg -> {
			LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Adding trades from modid " + msg.getSenderModId());

			Object messageObject = msg.getMessageSupplier().get();

			if (messageObject instanceof TradeCollection) {
				TradeCollection toAdd = (TradeCollection) messageObject;

				toAdd.source = msg.getSenderModId();

				// This is correct, because the method below returns false if no problems.
				// Yes, it is sloppy. No, I dont care.
				if (!TradeUtil.checkTradeCollection(toAdd)) {
					if (TradeHandler.customTrades.containsKey(toAdd.profession)) {
						ArrayList<MyTrade> list = new ArrayList<MyTrade>();
						Collections.addAll(list, TradeHandler.customTrades.get(toAdd.profession).trades);
						Collections.addAll(list, toAdd.trades);

						TradeHandler.customTrades.get(toAdd.profession).trades = list.toArray(new MyTrade[list.size()]);
					} else {
						TradeHandler.customTrades.put(toAdd.profession, toAdd);
					}
				}
			} else if (messageObject instanceof WandererTradeCollection) {
				WandererTradeCollection toAdd = (WandererTradeCollection) messageObject;

				toAdd.source = msg.getSenderModId();

				// See comment above...
				if (!TradeUtil.checkTradeCollection(toAdd)) {
					if (TradeHandler.customWandererTrades.containsKey(toAdd.profession)) {
						ArrayList<MyTrade> list = new ArrayList<MyTrade>();
						Collections.addAll(list, TradeHandler.customWandererTrades.get(toAdd.profession).trades);
						Collections.addAll(list, toAdd.trades);

						TradeHandler.customWandererTrades.get(toAdd.profession).trades = list
								.toArray(new MyWandererTrade[list.size()]);
					} else {
						TradeHandler.customWandererTrades.put(toAdd.profession, toAdd);
					}
				}
			}
		});
	}
}
