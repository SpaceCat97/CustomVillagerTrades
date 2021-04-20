package uk.co.dotcode.customvillagertrades;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.codehaus.plexus.util.StringUtils;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class TradeUtil {

	public static void checkTrades(List<? extends String> customTrades) {
		for (int i = 0; i < customTrades.size(); i++) {
			String[] data = customTrades.get(i).split(" ");

			if (checkDataNumerics(data)) {

				int tradeLevel = Integer.parseInt(data[0]);

				if (tradeLevel < 1 || tradeLevel > 5) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid trade level. Use a number between 1 and 5 - "
									+ customTrades.get(i));
					continue;
				}

				if (data.length == 6) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
					} else {
						continue;
					}

				} else if (data.length == 7) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
					} else {
						continue;
					}

				} else if (data.length == 8) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
					} else {
						continue;
					}

				} else if (data.length == 9) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
					} else {
						continue;
					}

				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid format - " + customTrades.get(i));
					continue;
				}
			} else {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid format (numbers) - " + customTrades.get(i));
				continue;
			}
		}
	}

	public static void processTradesWithEvent(VillagerTradesEvent event, List<? extends String> customTrades) {
		for (int i = 0; i < customTrades.size(); i++) {
			String[] data = customTrades.get(i).split(" ");

			if (checkDataNumerics(data)) {

				int tradeLevel = Integer.parseInt(data[0]);

				if (tradeLevel < 1 || tradeLevel > 5) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid trade level. Use a number between 1 and 5 - "
									+ customTrades.get(i));
					continue;
				}

				if (data.length == 6) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
						event.getTrades().get(tradeLevel).add(new CustomTrade(data[1], data[3],
								Integer.parseInt(data[2]), Integer.parseInt(data[4]), Integer.parseInt(data[5])));
						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 7) {
					String[] dataToCheck = new String[] { data[1], data[3] };

					if (doItemsExist(dataToCheck)) {
						event.getTrades().get(tradeLevel)
								.add(new CustomTrade(data[1], data[3], Integer.parseInt(data[2]),
										Integer.parseInt(data[4]), Integer.parseInt(data[5]),
										Integer.parseInt(data[6])));
						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 8) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
						event.getTrades().get(tradeLevel)
								.add(new CustomTrade(data[1], data[3], data[5], Integer.parseInt(data[2]),
										Integer.parseInt(data[4]), Integer.parseInt(data[6]),
										Integer.parseInt(data[7])));
						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else if (data.length == 9) {
					String[] dataToCheck = new String[] { data[1], data[3], data[5] };

					if (doItemsExist(dataToCheck)) {
						event.getTrades().get(tradeLevel)
								.add(new CustomTrade(data[1], data[3], data[5], Integer.parseInt(data[2]),
										Integer.parseInt(data[4]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
										Integer.parseInt(data[8])));
						LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
								"Successfully added custom trade - " + customTrades.get(i));
					} else {
						continue;
					}

				} else {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Unable to add a custom trade! Reason: invalid format - " + customTrades.get(i));
					continue;
				}
			} else {
				LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
						"Unable to add a custom trade! Reason: invalid format (numbers) - " + customTrades.get(i));
				continue;
			}
		}
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
					"Unable to add a custom trade! Reason: item/block does not exist - " + problems);
			return false;
		}
		return true;
	}

	public static ResourceLocation getResourceLocation(String key) {
		String[] splitLocation = key.split(":");
		return new ResourceLocation(splitLocation[0], splitLocation[1]);
	}

}
