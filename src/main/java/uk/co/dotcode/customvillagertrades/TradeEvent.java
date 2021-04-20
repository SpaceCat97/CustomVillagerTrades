package uk.co.dotcode.customvillagertrades;

import java.util.List;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TradeEvent {

	@SubscribeEvent
	public void registerTrades(VillagerTradesEvent event) {
		List<? extends String> customTrades;

		if (event.getType() == VillagerProfession.ARMORER) {
			customTrades = CustomTradeConfig.armorerTrades.get();
		} else if (event.getType() == VillagerProfession.BUTCHER) {
			customTrades = CustomTradeConfig.butcherTrades.get();
		} else if (event.getType() == VillagerProfession.CARTOGRAPHER) {
			customTrades = CustomTradeConfig.cartographerTrades.get();
		} else if (event.getType() == VillagerProfession.CLERIC) {
			customTrades = CustomTradeConfig.clericTrades.get();
		} else if (event.getType() == VillagerProfession.FARMER) {
			customTrades = CustomTradeConfig.farmerTrades.get();
		} else if (event.getType() == VillagerProfession.FISHERMAN) {
			customTrades = CustomTradeConfig.fishermanTrades.get();
		} else if (event.getType() == VillagerProfession.FLETCHER) {
			customTrades = CustomTradeConfig.fletcherTrades.get();
		} else if (event.getType() == VillagerProfession.LEATHERWORKER) {
			customTrades = CustomTradeConfig.leatherworkerTrades.get();
		} else if (event.getType() == VillagerProfession.LIBRARIAN) {
			customTrades = CustomTradeConfig.librarianTrades.get();
		} else if (event.getType() == VillagerProfession.MASON) {
			customTrades = CustomTradeConfig.masonTrades.get();
		} else if (event.getType() == VillagerProfession.NITWIT) {
			customTrades = CustomTradeConfig.nitwitTrades.get();
		} else if (event.getType() == VillagerProfession.SHEPHERD) {
			customTrades = CustomTradeConfig.shepherdTrades.get();
		} else if (event.getType() == VillagerProfession.TOOLSMITH) {
			customTrades = CustomTradeConfig.toolsmithTrades.get();
		} else if (event.getType() == VillagerProfession.WEAPONSMITH) {
			customTrades = CustomTradeConfig.weaponsmithTrades.get();
		} else {
			return;
		}

		if (event.getType() != VillagerProfession.NONE) {
			TradeUtil.processTradesWithEvent(event, customTrades);
			TradeUtil.processTradesWithEvent(event, CustomTradeConfig.allVillagerTrades.get());
		}

	}
}
