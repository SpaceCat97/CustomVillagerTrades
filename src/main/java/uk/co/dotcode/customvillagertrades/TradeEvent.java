package uk.co.dotcode.customvillagertrades;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;
import uk.co.dotcode.customvillagertrades.configs.WandererTradeCollection;

public class TradeEvent {

	@SubscribeEvent
	public void registerWanderingTrades(WandererTradesEvent event) {
		WandererTradeCollection collection = TradeHandler.loadWandererTrades("wanderer");

		if (collection != null) {
			boolean problem = TradeUtil.checkTradeCollection(collection);

			if (collection.numberOfGenericTrades() < 5) {
				LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
						"You must have at least 5 'not-rare' trades for the wanderer!");
				problem = true;
			}

			if (collection.numberOfRareTrades() < 1) {
				LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
						"You must have at least 1 'rare' trade for the wanderer!");
				problem = true;
			}

			if (!problem) {
				ArrayList<VillagerTrades.ITrade> genericArray = new ArrayList<VillagerTrades.ITrade>();
				ArrayList<VillagerTrades.ITrade> rareArray = new ArrayList<VillagerTrades.ITrade>();

				if (collection.removeOtherTrades) {
					while (event.getGenericTrades().size() > 0) {
						event.getGenericTrades().remove(0);
					}

					while (event.getRareTrades().size() > 0) {
						event.getRareTrades().remove(0);
					}
				} else {
					genericArray = new ArrayList<VillagerTrades.ITrade>(
							Arrays.asList(VillagerTrades.WANDERING_TRADER_TRADES.get(1)));
					rareArray = new ArrayList<VillagerTrades.ITrade>(
							Arrays.asList(VillagerTrades.WANDERING_TRADER_TRADES.get(2)));
				}

				for (int i = 0; i < collection.trades.length; i++) {
					MyTradeConverted convertedTrade = new MyTradeConverted(collection.trades[i]);

					if (collection.trades[i].isRare) {
						event.getRareTrades().add(convertedTrade);
						rareArray.add(convertedTrade);
					} else {
						event.getGenericTrades().add(convertedTrade);
						genericArray.add(convertedTrade);
					}
				}

				VillagerTrades.ITrade[] genericTradeArray = new VillagerTrades.ITrade[genericArray.size()];
				VillagerTrades.ITrade[] rareTradeArray = new VillagerTrades.ITrade[rareArray.size()];

				for (int i = 0; i < genericArray.size(); i++) {
					genericTradeArray[i] = genericArray.get(i);
				}

				for (int i = 0; i < rareArray.size(); i++) {
					rareTradeArray[i] = rareArray.get(i);
				}

				VillagerTrades.WANDERING_TRADER_TRADES.remove(1);
				VillagerTrades.WANDERING_TRADER_TRADES.remove(2);

				VillagerTrades.WANDERING_TRADER_TRADES.put(1, genericTradeArray);
				VillagerTrades.WANDERING_TRADER_TRADES.put(2, rareTradeArray);
			} else {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"There is a problem with the trade config for wanderer file!");
			}
		}
	}

	@SubscribeEvent
	public void registerTrades(VillagerTradesEvent event) {
		if (event.getType() != VillagerProfession.NONE) {
			TradeCollection collection = TradeHandler.loadTrades(event.getType().toString());
			TradeCollection collectionAll = TradeHandler.loadTrades("all");

			if (collection != null) {
				boolean problem = TradeUtil.checkTradeCollection(collection);

				if (!problem) {
					if (collection.removeOtherTrades) {
						for (int level = 1; level <= 5; level++) {
							while (event.getTrades().get(level).size() > 0) {
								event.getTrades().get(level).remove(0);
							}
						}
					}

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
				boolean problemAll = TradeUtil.checkTradeCollection(collectionAll);

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
