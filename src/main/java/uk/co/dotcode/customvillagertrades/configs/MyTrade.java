package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;

public class MyTrade {

	public MyTradeItem offer;
	public MyTradeItem request;
	public MyTradeItem additionalRequest;

	public int tradeExp;
	public int maxUses;
	public float priceMultiplier;
	public int demand;

	public int tradeLevel;

	public MerchantOffer createTrade() {

		MerchantOffer theTrade;

		if (additionalRequest != null) {
			theTrade = new MerchantOffer(request.createItemStack(), additionalRequest.createItemStack(),
					offer.createItemStack(), 0, maxUses, tradeExp, priceMultiplier, demand);
		} else {
			theTrade = new MerchantOffer(request.createItemStack(), ItemStack.EMPTY, offer.createItemStack(), 0,
					maxUses, tradeExp, priceMultiplier, demand);
		}

		return theTrade;
	}
}
