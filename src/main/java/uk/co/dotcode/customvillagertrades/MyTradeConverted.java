package uk.co.dotcode.customvillagertrades;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.MerchantOffer;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;

public class MyTradeConverted implements ITrade {

	private MyTrade trade;

	public MyTradeConverted(MyTrade trade) {
		this.trade = trade;
	}
	
	@Override
	public MerchantOffer getOffer(Entity entity, Random rand) {
		return trade.createTrade();
	}

}
