package uk.co.dotcode.customvillagertrades;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class CustomTrade implements ITrade {

	private MerchantOffer merchantOffer;

	// 2 requested items plus max uses
	public CustomTrade(String offer, String request1, String request2, int offerAmount, int requestAmount1,
			int requestAmount2, int tradeExp, int maxUses) {
		Item itemOffer = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(offer));
		Item itemRequest1 = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(request1));

		ItemStack offerStack = ItemHandlerHelper.copyStackWithSize(new ItemStack(itemOffer), offerAmount);
		ItemStack requestStack1 = ItemHandlerHelper.copyStackWithSize(new ItemStack(itemRequest1), requestAmount1);

		if (request2 != null) {
			Item itemRequest2 = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(request2));
			ItemStack requestStack2 = ItemHandlerHelper.copyStackWithSize(new ItemStack(itemRequest2), requestAmount2);

			this.merchantOffer = new MerchantOffer(requestStack1, requestStack2, offerStack, maxUses, tradeExp, 0.2f);
		} else {
			this.merchantOffer = new MerchantOffer(requestStack1, offerStack, maxUses, tradeExp, 0.2f);
		}
	}

	// 2 requested items
	public CustomTrade(String offer, String request1, String request2, int offerAmount, int requestAmount1,
			int requestAmount2, int tradeExp) {
		this(offer, request1, request2, offerAmount, requestAmount1, requestAmount2, tradeExp, 10);
	}

	// 1 requested item
	public CustomTrade(String offer, String request, int offerAmount, int requestAmount, int tradeExp) {
		this(offer, request, null, offerAmount, requestAmount, 0, tradeExp);
	}

	// 1 requested item plus max uses
	public CustomTrade(String offer, String request, int offerAmount, int requestAmount, int tradeExp, int maxUses) {
		this(offer, request, null, offerAmount, requestAmount, 0, tradeExp, maxUses);
	}

	@Override
	public MerchantOffer getOffer(Entity entity, Random rand) {
		return merchantOffer;
	}
}