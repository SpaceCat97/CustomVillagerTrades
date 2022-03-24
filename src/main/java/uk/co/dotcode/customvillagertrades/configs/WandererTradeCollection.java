package uk.co.dotcode.customvillagertrades.configs;

public class WandererTradeCollection {

	public transient String source = "Local Config file";
	
	public String profession;
	public boolean removeOtherTrades = false;
	public MyWandererTrade[] trades;

	public int numberOfGenericTrades() {
		int count = 0;

		for (MyWandererTrade t : trades) {
			if (!t.isRare) {
				count++;
			}
		}
		return count;
	}

	public int numberOfRareTrades() {
		int count = 0;

		for (MyWandererTrade t : trades) {
			if (t.isRare) {
				count++;
			}
		}
		return count;
	}

	public WandererTradeCollection build() {
		return this;
	}

}
