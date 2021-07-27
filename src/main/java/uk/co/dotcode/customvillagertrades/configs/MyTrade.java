package uk.co.dotcode.customvillagertrades.configs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import uk.co.dotcode.customvillagertrades.BaseClass;
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
			if (multiOffer.length > 0) {
				offer = multiOffer[TradeUtil.random.nextInt(multiOffer.length)];
			}
		}

		if (request == null) {
			LogManager.getLogger(BaseClass.MODID).log(Level.ERROR, "Failed to add trade, the request was null!");
		}

		if (offer == null) {
			LogManager.getLogger(BaseClass.MODID).log(Level.ERROR, "Failed to add trade, the offer was null!");
		}

		if (additionalRequest != null) {
			theTrade = new MerchantOffer(request.createItemStack(offer.getPriceModifier()),
					additionalRequest.createItemStack(offer.getPriceModifier()), offer.createItemStack(0), 0, maxUses,
					tradeExp, priceMultiplier, demand);
		} else {
			theTrade = new MerchantOffer(request.createItemStack(offer.getPriceModifier()), ItemStack.EMPTY,
					offer.createItemStack(0), 0, maxUses, tradeExp, priceMultiplier, demand);
		}

		return theTrade;
	}
}
