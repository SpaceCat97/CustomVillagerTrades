package uk.co.dotcode.customvillagertrades.configs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.Entity;
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
	public Integer demand;

	public int tradeLevel;

	public MerchantOffer createTrade(Entity entity) {

		MerchantOffer theTrade;

		if (demand == null) {
			demand = 0;
		}

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
			theTrade = new MerchantOffer(request.createItemStack(offer.getPriceModifier(), entity),
					additionalRequest.createItemStack(offer.getPriceModifier(), entity),
					offer.createItemStack(0, entity), 0, maxUses, tradeExp, priceMultiplier, demand);
		} else {
			theTrade = new MerchantOffer(request.createItemStack(offer.getPriceModifier(), entity), ItemStack.EMPTY,
					offer.createItemStack(0, entity), 0, maxUses, tradeExp, priceMultiplier, demand);
		}

		return theTrade;
	}
}
