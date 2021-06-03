package uk.co.dotcode.customvillagertrades;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.MyTradeItem;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;

public class TradeUtil {

	public static boolean checkTrades(TradeCollection coll) {
		MyTrade[] trades = coll.trades;

		boolean problem = false;

		for (int i = 0; i < trades.length; i++) {
			MyTrade trade = trades[i];

			// Items
			if (!checkItemKey(trade.offer.itemKey)) {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid offer item - " + coll.profession
								+ ", entry number = " + i + ", item = " + trade.offer.itemKey);
				problem = true;
			}

			if (!checkItemKey(trade.request.itemKey)) {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid request item - " + coll.profession
								+ ", entry number = " + i + ", item = " + trade.request.itemKey);
				problem = true;
			}

			if (trade.additionalRequest != null) {
				if (!checkItemKey(trade.additionalRequest.itemKey)) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid additional request item - " + coll.profession
									+ ", entry number = " + i + ", item = " + trade.additionalRequest.itemKey);
					problem = true;
				}
			}

			// Enchantments
			if (trade.offer.enchantmentKey != null) {
				if (!checkEnchantmentKey(trade.offer.enchantmentKey)) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid offer enchantment - " + coll.profession
									+ ", entry number = " + i + ", item = " + trade.offer.itemKey + ", enchantment = "
									+ trade.offer.enchantmentKey);
					problem = true;
				}
			}

			if (trade.request.enchantmentKey != null) {
				if (!checkEnchantmentKey(trade.request.enchantmentKey)) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid request enchantment - " + coll.profession
									+ ", entry number = " + i + ", item = " + trade.request.itemKey + ", enchantment = "
									+ trade.request.enchantmentKey);
					problem = true;
				}
			}

			if (trade.additionalRequest != null) {
				if (trade.additionalRequest.enchantmentKey != null) {
					if (!checkEnchantmentKey(trade.additionalRequest.enchantmentKey)) {
						LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
								"Unable to add a custom trade! Reason: invalid additional request enchantment - "
										+ coll.profession + ", entry number = " + i + ", item = "
										+ trade.additionalRequest.itemKey + ", enchantment = "
										+ trade.additionalRequest.enchantmentKey);
						problem = true;
					}
				}
			}

			// Trade Level
			if (trade.tradeLevel < 1 || trade.tradeLevel > 5) {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid trade level. Use a number between 1 and 5 - "
								+ coll.profession + ", entry number = " + i + ", level = " + trade.tradeLevel);
			}
		}

		return problem;
	}

	private static boolean checkItemKey(String itemKey) {
		String[] splitLocation = itemKey.split(":");

		if (splitLocation.length == 2) {
			ResourceLocation resourceLocation = getResourceLocation(itemKey);
			Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

			if (item == null || item == Items.AIR) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private static boolean checkEnchantmentKey(String enchantmentKey) {
		String[] splitLocation = enchantmentKey.split(":");
		if (splitLocation.length == 2) {
			ResourceLocation resourceLocation = getResourceLocation(enchantmentKey);
			Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation);

			if (enchantment == null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static ArrayList<MyTrade> convertOldTrades(List<? extends String> customTrades) {
		ArrayList<MyTrade> validTrades = new ArrayList<MyTrade>();

		for (int i = 0; i < customTrades.size(); i++) {
			MyTrade theTrade = new MyTrade();
			String[] data = customTrades.get(i).split(" ");

			if (checkDataNumerics(data)) {

				int tradeLevel = Integer.parseInt(data[0]);
				theTrade.tradeLevel = tradeLevel;

				if (tradeLevel < 1 || tradeLevel > 5) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add an old custom trade! Reason: invalid trade level. Use a number between 1 and 5 - "
									+ customTrades.get(i));
					continue;
				}

				if (data.length == 6) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]));
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]));
						theTrade.tradeExp = Integer.parseInt(data[5]);

						validTrades.add(theTrade);

						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully loaded old custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 7) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]));
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]));
						theTrade.tradeExp = Integer.parseInt(data[5]);
						theTrade.maxUses = Integer.parseInt(data[6]);

						validTrades.add(theTrade);

						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added old custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 8) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]));
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]));
						theTrade.additionalRequest = new MyTradeItem(data[5], Integer.parseInt(data[6]));
						theTrade.tradeExp = Integer.parseInt(data[7]);

						validTrades.add(theTrade);

						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added old custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 9) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]));
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]));
						theTrade.additionalRequest = new MyTradeItem(data[5], Integer.parseInt(data[6]));
						theTrade.tradeExp = Integer.parseInt(data[7]);
						theTrade.maxUses = Integer.parseInt(data[8]);

						validTrades.add(theTrade);

						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added old custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add an old custom trade! Reason: invalid format - " + customTrades.get(i));
					continue;
				}
			} else {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add an old custom trade! Reason: invalid format (numbers) - " + customTrades.get(i));
				continue;
			}
		}

		return validTrades;
	}

	private static boolean checkDataNumerics(String[] data) {
		if (data.length == 6) {
			if (StringUtils.isNumeric(data[0]) && StringUtils.isNumeric(data[2]) && StringUtils.isNumeric(data[4])
					&& StringUtils.isNumeric(data[5])) {
				return true;
			}
		} else if (data.length == 7) {
			if (StringUtils.isNumeric(data[0]) && StringUtils.isNumeric(data[2]) && StringUtils.isNumeric(data[4])
					&& StringUtils.isNumeric(data[5]) && StringUtils.isNumeric(data[6])) {
				return true;
			}
		} else if (data.length == 8) {
			if (StringUtils.isNumeric(data[0]) && StringUtils.isNumeric(data[2]) && StringUtils.isNumeric(data[4])
					&& StringUtils.isNumeric(data[6]) && StringUtils.isNumeric(data[7])) {
				return true;
			}
		} else if (data.length == 9) {
			if (StringUtils.isNumeric(data[0]) && StringUtils.isNumeric(data[2]) && StringUtils.isNumeric(data[4])
					&& StringUtils.isNumeric(data[6]) && StringUtils.isNumeric(data[7])
					&& StringUtils.isNumeric(data[8])) {
				return true;
			}
		}
		return false;
	}

	private static boolean doItemsExist(String[] toCheck) {
		String problems = "";

		for (int i = 0; i < toCheck.length; i++) {
			String[] splitLocation = toCheck[i].split(":");
			if (splitLocation.length == 2) {
				ResourceLocation resourceLocation = getResourceLocation(toCheck[i]);
				Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

				if (item == null || item == Items.AIR) {
					problems += toCheck[i] + " ";
					continue;
				}

			} else {
				problems += toCheck[i] + " ";
				continue;
			}

		}

		if (problems.length() > 0) {
			LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
					"Unable to add an old custom trade! Reason: item/block does not exist - " + problems);
			return false;
		}
		return true;
	}

	public static ResourceLocation getResourceLocation(String key) {
		String[] splitLocation = key.split(":");
		return new ResourceLocation(splitLocation[0], splitLocation[1]);
	}

}
