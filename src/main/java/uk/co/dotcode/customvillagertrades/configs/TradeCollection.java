package uk.co.dotcode.customvillagertrades.configs;

public class TradeCollection {

	public String profession;
	public boolean removeOtherTrades = false;
	public MyTrade[] trades;
	
	public TradeCollection build() {
		return this;
	}
}
