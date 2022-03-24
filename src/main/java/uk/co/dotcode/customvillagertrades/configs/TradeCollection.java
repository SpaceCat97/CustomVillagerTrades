package uk.co.dotcode.customvillagertrades.configs;

public class TradeCollection {

	public transient String source = "Local Config file";

	public String profession;
	public boolean removeOtherTrades = false;
	public MyTrade[] trades;

	public TradeCollection build() {
		return this;
	}
}
