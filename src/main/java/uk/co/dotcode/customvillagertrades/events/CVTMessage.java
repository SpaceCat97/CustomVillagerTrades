package uk.co.dotcode.customvillagertrades.events;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.MyTradeEnchantment;
import uk.co.dotcode.customvillagertrades.configs.MyTradeItem;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;

public class CVTMessage {

	public static final String ADD_VILLAGER_TRADES = "add_villager_trades";
	public static final String ADD_WANDERER_TRADES = "add_wanderer_trades";

	public static TradeCollection exampleTradeCollection() {
		TradeCollection exampleTrades = new TradeCollection();

		MyTrade exampleOne = new MyTrade();
		exampleOne.offer = new MyTradeItem("minecraft:dirt", 3, 0);

		exampleOne.offer.enchantments = new MyTradeEnchantment[2];
		exampleOne.offer.enchantments[0] = new MyTradeEnchantment("minecraft:knockback", 2);
		exampleOne.offer.enchantments[1] = new MyTradeEnchantment("minecraft:sharpness", -1, 5);
		exampleOne.request = new MyTradeItem("minecraft:cobblestone", 1, 0);
		exampleOne.tradeExp = 3;
		exampleOne.maxUses = 10;
		exampleOne.priceMultiplier = 0.1f;
		exampleOne.tradeLevel = 1;

		MyTrade exampleTwo = new MyTrade();
		exampleTwo.offer = new MyTradeItem("minecraft:emerald", 2, 0);
		exampleTwo.request = new MyTradeItem("minecraft:iron_ingot", 2, 0);
		exampleTwo.additionalRequest = new MyTradeItem("minecraft:gold_ingot", 1, 0);
		exampleTwo.tradeExp = 6;
		exampleTwo.maxUses = 10;
		exampleTwo.priceMultiplier = 0.1f;
		exampleTwo.tradeLevel = 2;

		MyTrade exampleThree = new MyTrade();
		exampleThree.multiOffer = new MyTradeItem[3];
		exampleThree.multiOffer[0] = new MyTradeItem("minecraft:iron_axe", 1, -3);
		exampleThree.multiOffer[1] = new MyTradeItem("minecraft:golden_axe", 1, 0);
		exampleThree.multiOffer[2] = new MyTradeItem("minecraft:diamond_axe", 1, 4);
		exampleThree.request = new MyTradeItem("minecraft:iron_ingot", 6, 0);
		exampleThree.tradeExp = 15;
		exampleThree.maxUses = 5;
		exampleThree.priceMultiplier = 0.1f;
		exampleThree.tradeLevel = 3;

		MyTrade exampleFour = new MyTrade();
		exampleFour.offer = new MyTradeItem("minecraft:wooden_sword", 1, 0);
		exampleFour.offer.semiRandomEnchantments = new MyTradeEnchantment[3];
		exampleFour.offer.semiRandomEnchantments[0] = new MyTradeEnchantment("minecraft:mending", 1);
		exampleFour.offer.semiRandomEnchantments[1] = new MyTradeEnchantment("minecraft:looting", 3);
		exampleFour.offer.semiRandomEnchantments[2] = new MyTradeEnchantment("minecraft:sweeping", 2);
		exampleFour.request = new MyTradeItem("minecraft:quartz", 5, 0);
		exampleFour.tradeExp = 20;
		exampleFour.maxUses = 5;
		exampleFour.priceMultiplier = 0;
		exampleFour.tradeLevel = 4;

		MyTrade exampleFive = new MyTrade();
		exampleFive.offer = new MyTradeItem("minecraft:enchanted_book", 1, 0);
		exampleFive.offer.enchantments = new MyTradeEnchantment[1];
		exampleFive.offer.enchantments[0] = new MyTradeEnchantment("random", 2);
		exampleFive.offer.blacklistedEnchantments = new String[2];
		exampleFive.offer.blacklistedEnchantments[0] = "minecraft:protection";
		exampleFive.offer.blacklistedEnchantments[1] = "minecraft:fire_protection";
		exampleFive.request = new MyTradeItem("minecraft:emerald", 3, 0);
		exampleFive.tradeExp = 3;
		exampleFive.maxUses = 10;
		exampleFive.priceMultiplier = 0.1f;
		exampleFive.tradeLevel = 1;

		MyTrade[] exampleTradeList = new MyTrade[5];
		exampleTradeList[0] = exampleOne;
		exampleTradeList[1] = exampleTwo;
		exampleTradeList[2] = exampleThree;
		exampleTradeList[3] = exampleFour;
		exampleTradeList[4] = exampleFive;

		exampleTrades.profession = VillagerProfession.ARMORER.toString();
		exampleTrades.trades = exampleTradeList;
		exampleTrades.removeOtherTrades = true;

		return exampleTrades;
	}

}
