package uk.co.dotcode.customvillagertrades.configs;

public class TradeCollection {

	public String profession;
	public boolean removeOtherTrades = false;
	public MyTrade[] trades;

	public void loadTrades() {
		for (int i = 0; i < trades.length; i++) {
			trades[i].createTrade();
		}
	}
}
