package uk.co.dotcode.customvillagertrades;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicates;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;;

public class CustomTradeConfig {

	public static final ConfigValue<List<? extends String>> allVillagerTrades, armorerTrades, butcherTrades,
			cartographerTrades, clericTrades, farmerTrades, fishermanTrades, fletcherTrades, leatherworkerTrades,
			librarianTrades, masonTrades, nitwitTrades, shepherdTrades, toolsmithTrades, weaponsmithTrades;

	public static final ForgeConfigSpec CONFIG_SPEC;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.comment(" This is the trading config for the Villager Trading Mod.",
				" For each villager type, must specify the trade, there are several formats you can use:",
				" \"{trade level} {modid:id} {offer amount} {modid:id} {request amount} {trade experience}\"",
				" \"{trade level} {modid:id} {offer amount} {modid:id} {request amount} {trade experience} {max trades}\"",
				" \"{trade level} {modid:id} {offer amount} {modid:id} {request 1 amount} {modid:id} {request 2 amount} {trade experience}\"",
				" \"{trade level} {modid:id} {offer amount} {modid:id} {request 1 amount} {modid:id} {request 2 amount} {trade experience} {max trades}\"",
				"\n Note that there MUST be a space between each variable.",
				" Example 1: \"1 minecraft:coal 1 dotcoinmod:ironcoin 2 1\"",
				" Example 2: \"1 minecraft:sponge 1 minecraft:emerald 2 4 5\"",
				" Example 3: \"3 minecraft:charcoal 5 minecraft:emerald 1 minecraft:oak_log 5 3\"",
				" Example 4: \"4 minecraft:emerald 1 minecraft:gold_ingot 8 minecraft:diamond 1 10 8\"",
				"\n Example 1 is a level 1 trade, you give the villager 1 iron coin (from another mod) for 1 coal.",
				" Example 2 is a level 1 trade, you give the villager 2 emeralds for 1 sponge. This can be done 5 times.",
				" Example 3 is a level 3 trade, you give the villager 1 emerald and 5 logs for 5 charcoal.",
				" Example 4 is a level 4 trade, you give the villager 8 gold and 1 emerald for 1 diamond. This can be done 8 times.",
				"\n The trade level determines at what level the trade becomes available. The range is 1-5 where 1 is lowest.",
				" I suggest not going below 1 for the trade experience.",
				" The {max trades} variable is optional. If empty it will default to 10.",
				" You can find existing trades to use as a guide here: https:\\\\minecraft.fandom.com\\wiki\\Trading#Java_Edition \n");

		allVillagerTrades = defineEmptyList(builder, "allVillagerTrades", Arrays.asList());

		armorerTrades = defineEmptyList(builder, "armorerTrades", Arrays.asList());

		butcherTrades = defineEmptyList(builder, "butcherTrades", Arrays.asList());

		cartographerTrades = defineEmptyList(builder, "cartographerTrades", Arrays.asList());

		clericTrades = defineEmptyList(builder, "clericTrades", Arrays.asList());

		farmerTrades = defineEmptyList(builder, "farmerTrades", Arrays.asList());

		fishermanTrades = defineEmptyList(builder, "fishermanTrades", Arrays.asList());

		fletcherTrades = defineEmptyList(builder, "fletcherTrades", Arrays.asList());

		leatherworkerTrades = defineEmptyList(builder, "leatherworkerTrades", Arrays.asList());

		librarianTrades = defineEmptyList(builder, "librarianTrades", Arrays.asList());

		masonTrades = defineEmptyList(builder, "masonTrades", Arrays.asList());

		nitwitTrades = defineEmptyList(builder, "nitwitTrades", Arrays.asList());

		shepherdTrades = defineEmptyList(builder, "shepherdTrades", Arrays.asList());

		toolsmithTrades = defineEmptyList(builder, "toolsmithTrades", Arrays.asList());

		weaponsmithTrades = defineEmptyList(builder, "weaponsmithTrades", Arrays.asList());

		CONFIG_SPEC = builder.build();
	}

	public static ConfigValue<List<? extends String>> defineEmptyList(ForgeConfigSpec.Builder builder, String name,
			List<String> array) {
		return builder.defineListAllowEmpty(Arrays.asList(name), () -> array, Predicates.alwaysTrue());
	}
}
