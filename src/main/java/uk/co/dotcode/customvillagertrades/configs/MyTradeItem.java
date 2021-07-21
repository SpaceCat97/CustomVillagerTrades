package uk.co.dotcode.customvillagertrades.configs;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.BaseClass;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeItem {

	public String itemKey;
	public Integer amount;
	public Integer priceModifier;

	public Integer metadata;

	public MyTradeEnchantment[] enchantments;
	public MyTradeEnchantment[] semiRandomEnchantments;
	public String[] blacklistedEnchantments;

	public MyTradeItem(String itemKey, int amount) {
		this.itemKey = itemKey;
		this.amount = amount;
		if (priceModifier == null) {
			priceModifier = 0;
		}
	}

	public MyTradeItem(String itemKey, int amount, int priceModifier) {
		this(itemKey, amount);
		this.priceModifier = priceModifier;
	}

	public ItemStack createItemStack(int modifier) {
		Item item = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(itemKey));

		ItemStack stack = new ItemStack(item, amount + modifier);

		if (metadata != null) {
			stack.setDamageValue(metadata);
		}

		stack = processEnchantments(stack);

		return stack;
	}

	public boolean checkEnchantments() {
		if (enchantments != null) {
			if (enchantments.length > 0) {
				for (int i = 0; i < enchantments.length; i++) {
					boolean check = TradeUtil.isEnchantmentKeyReal(enchantments[i].enchantmentKey);

					if (!check) {
						LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
								"Enchantment invalid - " + enchantments[i].enchantmentKey);
						return true;
					}
				}
			}
		}

		if (semiRandomEnchantments != null && semiRandomEnchantments.length > 0) {
			for (int i = 0; i < semiRandomEnchantments.length; i++) {
				boolean check = TradeUtil.isEnchantmentKeyReal(semiRandomEnchantments[i].enchantmentKey);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Semi-Random Enchantment invalid - " + semiRandomEnchantments[i].enchantmentKey);
					return true;
				}
			}
		}

		return false;
	}

	private ItemStack processEnchantments(ItemStack stack) {
		ItemStack enchantedStack = stack.copy();

		if (enchantments != null) {
			for (int i = 0; i < enchantments.length; i++) {
				if (enchantments[i].enchantmentKey.equalsIgnoreCase("random")) {
					ArrayList<Enchantment> availableEnchantments = new ArrayList<Enchantment>();

					for (Enchantment e : ForgeRegistries.ENCHANTMENTS.getValues()) {
						if (e.canApplyAtEnchantingTable(enchantedStack)) {
							availableEnchantments.add(e);
						}
					}

					addEnchantmentMyWay(enchantedStack,
							availableEnchantments.get(TradeUtil.random.nextInt(availableEnchantments.size())),
							enchantments[i].enchantmentLevel, enchantments[i].maxEnchantmentLevel);
				} else if (enchantments[i].enchantmentKey.contains("#")) {
					String[] enchantmentChoices = enchantments[i].enchantmentKey.split("#");

					String chosenKey = enchantmentChoices[TradeUtil.random.nextInt(enchantmentChoices.length)];

					addEnchantmentMyWay(enchantedStack,
							ForgeRegistries.ENCHANTMENTS.getValue(TradeUtil.getResourceLocation(chosenKey)),
							enchantments[i].enchantmentLevel, enchantments[i].maxEnchantmentLevel);
				} else {
					addEnchantmentMyWay(enchantedStack, enchantments[i].getEnchantment(),
							enchantments[i].enchantmentLevel, enchantments[i].maxEnchantmentLevel);
				}
			}
		}

		if (semiRandomEnchantments != null) {
			int randomInt = TradeUtil.random.nextInt(semiRandomEnchantments.length);

			addEnchantmentMyWay(enchantedStack, semiRandomEnchantments[randomInt].getEnchantment(),
					semiRandomEnchantments[randomInt].enchantmentLevel,
					semiRandomEnchantments[randomInt].maxEnchantmentLevel);
		}

		return enchantedStack;
	}

	private ItemStack addEnchantmentMyWay(ItemStack stack, Enchantment enchantment, int level, int maxLevel) {

		int chosenLevel = level;

		if (level < 0) {
			chosenLevel = TradeUtil.random.nextInt(maxLevel) + 1;
		}

		if (stack.getItem() == Items.ENCHANTED_BOOK) {
			EnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantment, chosenLevel));
		} else {
			stack.enchant(enchantment, chosenLevel);
		}
		return stack;
	}
}
