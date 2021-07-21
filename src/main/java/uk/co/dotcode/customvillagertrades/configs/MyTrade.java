package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTrade {

	public MyTradeItem offer;
	public MyTradeItem[] multiOffer;
	public MyTradeItem request;
	public MyTradeItem additionalRequest;

	public int tradeExp;
	public int maxUses;
	public float priceMultiplier;
	public int demand;

	public int tradeLevel;

	public MerchantOffer createTrade() {

		MerchantOffer theTrade;

		if (multiOffer != null) {
			offer = multiOffer[TradeUtil.random.nextInt(multiOffer.length)];
		}

		if (additionalRequest != null) {
			theTrade = new MerchantOffer(request.createItemStack(offer.priceModifier),
					additionalRequest.createItemStack(offer.priceModifier), offer.createItemStack(0), 0, maxUses,
					tradeExp, priceMultiplier, demand);
		} else {
			theTrade = new MerchantOffer(request.createItemStack(offer.priceModifier), ItemStack.EMPTY,
					offer.createItemStack(0), 0, maxUses, tradeExp, priceMultiplier, demand);
		}

		return theTrade;
	}
}
