package uk.co.dotcode.customvillagertrades;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;

public class TradeEvent {

	@SubscribeEvent
	public void registerTrades(VillagerTradesEvent event) {
		if (event.getType() != VillagerProfession.NONE) {
			TradeCollection collection = TradeHandler.loadTrades(event.getType().toString());
			TradeCollection collectionAll = TradeHandler.loadTrades("all");

			if (collection != null) {
				boolean problem = TradeUtil.checkTrades(collection);

				if (!problem) {
					for (int i = 0; i < collection.trades.length; i++) {
						MyTrade currentTrade = collection.trades[i];

						event.getTrades().get(currentTrade.tradeLevel).add(new MyTradeConverted(currentTrade));
					}
				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"There is a problem with the trade config for " + event.getType().toString() + "file!");
				}
			}

			if (collectionAll != null) {
				boolean problemAll = TradeUtil.checkTrades(collectionAll);

				if (!problemAll) {
					for (int i = 0; i < collectionAll.trades.length; i++) {
						MyTrade currentTrade = collectionAll.trades[i];

						event.getTrades().get(currentTrade.tradeLevel).add(new MyTradeConverted(currentTrade));
					}
				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"There is a problem with the trade config for all file!");
				}
			}
		}
	}
}
