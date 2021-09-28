package uk.co.dotcode.customvillagertrades;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;
import uk.co.dotcode.customvillagertrades.configs.WandererTradeCollection;

public class TradeEvent {

	private TradeCollection tradeCollection;
	private TradeCollection tradeCollectionAll;
	private WandererTradeCollection tradeCollectionWanderer;

	@SubscribeEvent
	public void registerWanderingTrades(WandererTradesEvent event) {
		tradeCollectionWanderer = TradeHandler.loadWandererTrades("wanderer");

		if (tradeCollectionWanderer != null) {
			boolean problem = TradeUtil.checkTradeCollection(tradeCollectionWanderer);

			if (tradeCollectionWanderer.removeOtherTrades) {
				if (tradeCollectionWanderer.numberOfGenericTrades() < 5) {
					LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
							"You must have at least 5 'not-rare' trades for the wanderer!");
					problem = true;
				}

				if (tradeCollectionWanderer.numberOfRareTrades() < 1) {
					LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
							"You must have at least 1 'rare' trade for the wanderer!");
					problem = true;
				}
			}

			if (!problem) {
				ArrayList<ITrade> genericArray = new ArrayList<ITrade>();
				ArrayList<ITrade> rareArray = new ArrayList<ITrade>();

				if (tradeCollectionWanderer.removeOtherTrades) {
					while (event.getGenericTrades().size() > 0) {
						event.getGenericTrades().remove(0);
					}

					while (event.getRareTrades().size() > 0) {
						event.getRareTrades().remove(0);
					}
				} else {
					genericArray = new ArrayList<ITrade>(Arrays.asList(VillagerTrades.WANDERING_TRADER_TRADES.get(1)));
					rareArray = new ArrayList<ITrade>(Arrays.asList(VillagerTrades.WANDERING_TRADER_TRADES.get(2)));
				}

				for (int i = 0; i < tradeCollectionWanderer.trades.length; i++) {
					MyTradeConverted convertedTrade = new MyTradeConverted(tradeCollectionWanderer.trades[i]);

					if (tradeCollectionWanderer.trades[i].isRare) {
						event.getRareTrades().add(convertedTrade);
						rareArray.add(convertedTrade);
					} else {
						event.getGenericTrades().add(convertedTrade);
						genericArray.add(convertedTrade);
					}
				}

				ITrade[] genericTradeArray = new ITrade[genericArray.size()];
				ITrade[] rareTradeArray = new ITrade[rareArray.size()];

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
			tradeCollection = TradeHandler.loadTrades(event.getType().toString());
			tradeCollectionAll = TradeHandler.loadTrades("all");

			if (tradeCollection != null) {
				boolean problem = TradeUtil.checkTradeCollection(tradeCollection);

				if (!problem) {
					if (tradeCollection.removeOtherTrades) {
						for (int level = 1; level <= 5; level++) {
							while (event.getTrades().get(level).size() > 0) {
								event.getTrades().get(level).remove(0);
							}
						}
					}

					for (int i = 0; i < tradeCollection.trades.length; i++) {
						MyTrade currentTrade = tradeCollection.trades[i];

						event.getTrades().get(currentTrade.tradeLevel).add(new MyTradeConverted(currentTrade));
					}
				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"There is a problem with the trade config for " + event.getType().toString() + " file!");
				}
			}

			if (tradeCollectionAll != null) {
				boolean problemAll = TradeUtil.checkTradeCollection(tradeCollectionAll);

				if (!problemAll) {
					for (int i = 0; i < tradeCollectionAll.trades.length; i++) {
						MyTrade currentTrade = tradeCollectionAll.trades[i];

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
