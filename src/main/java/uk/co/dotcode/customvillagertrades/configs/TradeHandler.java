package uk.co.dotcode.customvillagertrades.configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.fml.loading.FMLPaths;
import uk.co.dotcode.customvillagertrades.BaseClass;
import uk.co.dotcode.customvillagertrades.CustomTradeConfigOld;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class TradeHandler {

	public static File folder = FMLPaths.CONFIGDIR.get().resolve("custom trades").toFile();
	public static File fileOld = FMLPaths.CONFIGDIR.get().resolve("customvillagertrades-common.toml").toFile();

	public static HashMap<String, TradeCollection> customTrades = new HashMap<String, TradeCollection>();

	public static void init() {

		if (!folder.exists()) {
			folder.mkdir();
			LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "No config found, creating");

			if (fileOld.exists()) {
				LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
						"Found old config, porting to new config layouts");

				portOldConfig();
			}
		}

		File[] fileArray = folder.listFiles();

		if (fileArray == null || fileArray.length < 1) {
			generateExampleTrade();
		} else {
			Gson gson = new Gson();

			for (File f : fileArray) {

				String profession = FilenameUtils.removeExtension(f.getName());

				LogManager.getLogger(BaseClass.MODID).log(Level.INFO,
						"Loading custom villager trades for: " + profession);

				try (Reader reader = new FileReader(f)) {
					TradeCollection coll = gson.fromJson(reader, TradeCollection.class);

					customTrades.put(profession, coll);

				} catch (IOException e) {
					LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
							"A problem has been found with the config file for '" + profession
									+ "'! This is most likely an issue where the file can not be found/accessed (which should never happen...)");
					e.printStackTrace();
				} catch (JsonSyntaxException e) {
					LogManager.getLogger(BaseClass.MODID).log(Level.ERROR,
							"A problem has been found with the config file for '" + profession
									+ "'! This is most likely a formatting issue - take a look over the config for anything that seems out of place (or use a JSON verifier).");
				}

				Iterator<Entry<String, TradeCollection>> it = customTrades.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, TradeCollection> entry = (Map.Entry<String, TradeCollection>) it.next();
					TradeUtil.checkTrades(entry.getValue());
				}
			}
		}
	}

	public static TradeCollection loadTrades(String profession) {
		return customTrades.get(profession);
	}

	private static void portOldConfig() {
		LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Checking old custom villager trades");

		if (CustomTradeConfigOld.allVillagerTrades.get().size() > 0) {
			ArrayList<MyTrade> allVillagerTrades = TradeUtil
					.convertOldTrades(CustomTradeConfigOld.allVillagerTrades.get());

			saveOldToNewFile(allVillagerTrades, "all");
		}

		if (CustomTradeConfigOld.armorerTrades.get().size() > 0) {
			ArrayList<MyTrade> armorerTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.armorerTrades.get());

			saveOldToNewFile(armorerTrades, VillagerProfession.ARMORER.toString());
		}

		if (CustomTradeConfigOld.butcherTrades.get().size() > 0) {
			ArrayList<MyTrade> butcherTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.butcherTrades.get());

			saveOldToNewFile(butcherTrades, VillagerProfession.BUTCHER.toString());
		}

		if (CustomTradeConfigOld.cartographerTrades.get().size() > 0) {
			ArrayList<MyTrade> cartographerTrades = TradeUtil
					.convertOldTrades(CustomTradeConfigOld.cartographerTrades.get());

			saveOldToNewFile(cartographerTrades, VillagerProfession.CARTOGRAPHER.toString());
		}

		if (CustomTradeConfigOld.clericTrades.get().size() > 0) {
			ArrayList<MyTrade> clericTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.clericTrades.get());

			saveOldToNewFile(clericTrades, VillagerProfession.CLERIC.toString());
		}

		if (CustomTradeConfigOld.farmerTrades.get().size() > 0) {
			ArrayList<MyTrade> farmerTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.farmerTrades.get());

			saveOldToNewFile(farmerTrades, VillagerProfession.FARMER.toString());
		}

		if (CustomTradeConfigOld.fishermanTrades.get().size() > 0) {
			ArrayList<MyTrade> fishermanTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.fishermanTrades.get());

			saveOldToNewFile(fishermanTrades, VillagerProfession.FISHERMAN.toString());
		}

		if (CustomTradeConfigOld.fletcherTrades.get().size() > 0) {
			ArrayList<MyTrade> fletcherTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.fletcherTrades.get());

			saveOldToNewFile(fletcherTrades, VillagerProfession.FLETCHER.toString());
		}

		if (CustomTradeConfigOld.leatherworkerTrades.get().size() > 0) {
			ArrayList<MyTrade> leatherworkerTrades = TradeUtil
					.convertOldTrades(CustomTradeConfigOld.leatherworkerTrades.get());

			saveOldToNewFile(leatherworkerTrades, VillagerProfession.LEATHERWORKER.toString());
		}

		if (CustomTradeConfigOld.librarianTrades.get().size() > 0) {
			ArrayList<MyTrade> librarianTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.librarianTrades.get());

			saveOldToNewFile(librarianTrades, VillagerProfession.LIBRARIAN.toString());
		}

		if (CustomTradeConfigOld.masonTrades.get().size() > 0) {
			ArrayList<MyTrade> masonTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.masonTrades.get());

			saveOldToNewFile(masonTrades, VillagerProfession.MASON.toString());
		}

		if (CustomTradeConfigOld.nitwitTrades.get().size() > 0) {
			ArrayList<MyTrade> nitwitTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.nitwitTrades.get());

			saveOldToNewFile(nitwitTrades, VillagerProfession.NITWIT.toString());
		}

		if (CustomTradeConfigOld.shepherdTrades.get().size() > 0) {
			ArrayList<MyTrade> shepherdTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.shepherdTrades.get());

			saveOldToNewFile(shepherdTrades, VillagerProfession.SHEPHERD.toString());
		}

		if (CustomTradeConfigOld.toolsmithTrades.get().size() > 0) {
			ArrayList<MyTrade> toolsmithTrades = TradeUtil.convertOldTrades(CustomTradeConfigOld.toolsmithTrades.get());

			saveOldToNewFile(toolsmithTrades, VillagerProfession.TOOLSMITH.toString());
		}

		if (CustomTradeConfigOld.weaponsmithTrades.get().size() > 0) {
			ArrayList<MyTrade> weaponsmithTrades = TradeUtil
					.convertOldTrades(CustomTradeConfigOld.weaponsmithTrades.get());

			saveOldToNewFile(weaponsmithTrades, VillagerProfession.WEAPONSMITH.toString());
		}

		LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Old config has been converted to new configs.");
	}

	private static void saveOldToNewFile(ArrayList<MyTrade> trades, String profession) {
		TradeCollection collection = new TradeCollection();
		MyTrade[] tradeList = Arrays.copyOf(trades.toArray(), trades.size(), MyTrade[].class);

		collection.profession = profession;
		collection.trades = tradeList;

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		File exportPath = new File(folder, collection.profession + ".json");

		try (FileWriter writer = new FileWriter(exportPath)) {
			gson.toJson(collection, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateExampleTrade() {
		TradeCollection exampleTrades = new TradeCollection();

		MyTrade exampleOne = new MyTrade();
		exampleOne.offer = new MyTradeItem("minecraft:dirt", 3);

		exampleOne.offer.enchantments = new MyTradeEnchantment[2];
		exampleOne.offer.enchantments[0] = new MyTradeEnchantment();
		exampleOne.offer.enchantments[0].enchantmentKey = "minecraft:knockback";
		exampleOne.offer.enchantments[0].enchantmentLevel = 2;
		exampleOne.offer.enchantments[1] = new MyTradeEnchantment();
		exampleOne.offer.enchantments[1].enchantmentKey = "minecraft:sharpness";
		exampleOne.offer.enchantments[1].enchantmentLevel = 1;
		exampleOne.request = new MyTradeItem("minecraft:cobblestone", 1);
		exampleOne.tradeExp = 3;
		exampleOne.maxUses = 10;
		exampleOne.priceMultiplier = 0.1f;
		exampleOne.demand = 10;
		exampleOne.tradeLevel = 1;

		MyTrade exampleTwo = new MyTrade();
		exampleTwo.offer = new MyTradeItem("minecraft:emerald", 2);
		exampleTwo.request = new MyTradeItem("minecraft:iron_ingot", 2);
		exampleTwo.additionalRequest = new MyTradeItem("minecraft:gold_ingot", 1);
		exampleTwo.tradeExp = 6;
		exampleTwo.maxUses = 10;
		exampleTwo.priceMultiplier = 0.1f;
		exampleTwo.demand = 10;
		exampleTwo.tradeLevel = 2;

		MyTrade[] exampleTradeList = new MyTrade[2];
		exampleTradeList[0] = exampleOne;
		exampleTradeList[1] = exampleTwo;

		exampleTrades.profession = VillagerProfession.ARMORER.toString();
		exampleTrades.trades = exampleTradeList;

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		File exportPath = new File(folder, exampleTrades.profession + ".json");

		try (FileWriter writer = new FileWriter(exportPath)) {
			gson.toJson(exampleTrades, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
