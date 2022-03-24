package uk.co.dotcode.customvillagertrades;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.configs.MyTrade;
import uk.co.dotcode.customvillagertrades.configs.MyTradeItem;
import uk.co.dotcode.customvillagertrades.configs.TradeCollection;
import uk.co.dotcode.customvillagertrades.configs.WandererTradeCollection;

public class TradeUtil {

	public static Random random = new Random();

	public static boolean checkTradeCollection(TradeCollection coll) {
		return checkTrade(coll.trades, coll.profession);
	}

	public static boolean checkTradeCollection(WandererTradeCollection coll) {
		return checkTrade(coll.trades, coll.profession);

	}

	public static boolean isJsonFile(File f) {
		String extension = f.getPath().substring(f.getPath().lastIndexOf("."));
		if (extension.equalsIgnoreCase(".json")) {
			return true;
		}
		return false;
	}

	private static boolean checkTrade(MyTrade[] trades, String profession) {
		boolean problem = false;

		for (int i = 0; i < trades.length; i++) {
			MyTrade trade = trades[i];

			// Items
			if (trade.offer != null) {
				if (!checkItemKey(trade.offer.itemKey)) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid offer item - " + profession
									+ ", entry number = " + i + ", item = " + trade.offer.itemKey);
					problem = true;
				}
			}

			if (trade.multiOffer != null) {
				for (MyTradeItem t : trade.multiOffer) {
					if (!checkItemKey(t.itemKey)) {
						LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
								"Unable to add a custom trade! Reason: invalid multiOffer item - " + profession
										+ ", entry number = " + i + ", item = " + t.itemKey);
						problem = true;
					}
				}
			}

			if (!checkItemKey(trade.request.itemKey)) {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid request item - " + profession
								+ ", entry number = " + i + ", item = " + trade.request.itemKey);
				problem = true;
			}

			if (trade.additionalRequest != null) {
				if (!checkItemKey(trade.additionalRequest.itemKey)) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid additional request item - " + profession
									+ ", entry number = " + i + ", item = " + trade.additionalRequest.itemKey);
					problem = true;
				}
			}

			// Enchantments

			boolean offerEnchantProblem = false;
			boolean multiOfferEnchantProblem = false;
			boolean requestEnchantProblem = trade.request.checkEnchantments();
			boolean additionalRequestEnchantProblem = false;

			if (trade.offer != null) {
				offerEnchantProblem = trade.offer.checkEnchantments();
			}

			if (trade.multiOffer != null) {
				for (MyTradeItem t : trade.multiOffer) {
					if (t.checkEnchantments()) {
						multiOfferEnchantProblem = true;
					}
				}
			}

			if (trade.additionalRequest != null) {
				additionalRequestEnchantProblem = trade.additionalRequest.checkEnchantments();
			}

			if (offerEnchantProblem || multiOfferEnchantProblem || requestEnchantProblem
					|| additionalRequestEnchantProblem) {
				String key = "";

				if (offerEnchantProblem) {
					key = trade.offer.itemKey;
				}

				if (multiOfferEnchantProblem) {
					key = "multiOffer: ";
					for (MyTradeItem t : trade.multiOffer) {
						key += t.itemKey + ",";
					}
				}

				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid enchantment (listed above) - " + profession
								+ ", entry number = " + i + ", item = " + key);

				problem = true;
			}

			// Effects
			boolean offerEffectProblem = false;
			boolean multiOfferEffectProblem = false;
			boolean requestEffectProblem = trade.request.checkEffects();
			boolean additionalRequestEffectProblem = false;

			if (trade.offer != null) {
				offerEffectProblem = trade.offer.checkEffects();
			}

			if (trade.multiOffer != null) {
				for (MyTradeItem t : trade.multiOffer) {
					if (t.checkEffects()) {
						multiOfferEffectProblem = true;
					}
				}
			}

			if (trade.additionalRequest != null) {
				additionalRequestEffectProblem = trade.additionalRequest.checkEffects();
			}

			if (offerEffectProblem || multiOfferEffectProblem || requestEffectProblem
					|| additionalRequestEffectProblem) {
				String key = "";

				if (offerEffectProblem) {
					key = trade.offer.itemKey;
				}

				if (multiOfferEffectProblem) {
					key = "multiOffer: ";
					for (MyTradeItem t : trade.multiOffer) {
						key += t.itemKey + ",";
					}
				}

				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid effect (listed above) - " + profession
								+ ", entry number = " + i + ", item = " + key);

				problem = true;
			}

			// Trade Level
			if (trade.tradeLevel < 1 || trade.tradeLevel > 5) {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid trade level. Use a number between 1 and 5 - "
								+ profession + ", entry number = " + i + ", level = " + trade.tradeLevel);
				problem = true;
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

	public static boolean isEnchantmentKeyReal(String enchantmentKey) {
		if (enchantmentKey.equalsIgnoreCase("random")) {
			return true;
		}

		if (enchantmentKey.contains("#")) {
			String[] splitEnchantments = enchantmentKey.split("#");

			boolean problem = false;

			for (String s : splitEnchantments) {
				if (isSpecificEnchantmentKeyReal(s)) {
					problem = true;
				}
			}

			if (problem) {
				return false;
			} else {
				return true;
			}

		}

		return isSpecificEnchantmentKeyReal(enchantmentKey);

	}

	private static boolean isSpecificEnchantmentKeyReal(String enchantmentKey) {
		if (enchantmentKey == null) {
			LogManager.getLogger(BaseClass.MODID).log(Level.WARN, "Enchantment invalid - There's no enchantment key!");
			return false;
		}

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

	public static boolean isEffectReal(String effectKey) {
		if (effectKey == null) {
			LogManager.getLogger(BaseClass.MODID).log(Level.WARN, "Effect invalid - There's no effect key!");
			return false;
		}

		if (effectKey.equalsIgnoreCase("random")) {
			return true;
		}

		String[] splitLocation = effectKey.split(":");
		if (splitLocation.length == 2) {
			ResourceLocation resourceLocation = getResourceLocation(effectKey);
			Effect effect = ForgeRegistries.POTIONS.getValue(resourceLocation);

			if (effect == null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean isPotionReal(String potionKey) {
		if (potionKey == null) {
			LogManager.getLogger(BaseClass.MODID).log(Level.WARN, "Potion invalid - There's no potion key!");
			return false;
		}

		if (potionKey.equalsIgnoreCase("random")) {
			return true;
		}

		String[] splitLocation = potionKey.split(":");
		if (splitLocation.length == 2) {
			ResourceLocation resourceLocation = getResourceLocation(potionKey);
			Potion potion = ForgeRegistries.POTION_TYPES.getValue(resourceLocation);

			if (potion.getRegistryName().toString().equalsIgnoreCase("minecraft:empty")) {
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
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]), 0);
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]), 0);
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
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]), 0);
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]), 0);
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
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]), 0);
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]), 0);
						theTrade.additionalRequest = new MyTradeItem(data[5], Integer.parseInt(data[6]), 0);
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
						theTrade.offer = new MyTradeItem(data[1], Integer.parseInt(data[2]), 0);
						theTrade.request = new MyTradeItem(data[3], Integer.parseInt(data[4]), 0);
						theTrade.additionalRequest = new MyTradeItem(data[5], Integer.parseInt(data[6]), 0);
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

	public static int getIntFromColor(int r, int g, int b) {
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;

		return rgb;
	}
}
