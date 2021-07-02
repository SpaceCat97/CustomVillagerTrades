package uk.co.dotcode.customvillagertrades.configs;

public class WandererTradeCollection {

	public String profession;
	public boolean removeOtherTrades = false;
	public MyWandererTrade[] trades;

	public void loadTrades() {
		for (int i = 0; i < trades.length; i++) {
			trades[i].createTrade();
		}
	}

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

}
