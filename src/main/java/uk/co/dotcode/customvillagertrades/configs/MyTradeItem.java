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
	public int amount;

	public Integer metadata;

	public MyTradeEnchantment[] enchantments;

	public MyTradeItem(String itemKey, int amount) {
		this.itemKey = itemKey;
		this.amount = amount;
	}

	public ItemStack createItemStack() {
		Item item = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(itemKey));

		ItemStack stack = new ItemStack(item, amount);

		if (metadata != null) {
			stack.setDamageValue(metadata);
		}

		stack = processEnchantments(stack);

		return stack;
	}

	public boolean checkEnchantments() {
		if (enchantments != null && enchantments.length > 0) {
			for (int i = 0; i < enchantments.length; i++) {
				MyTradeEnchantment enchantment = enchantments[i];

				boolean check = TradeUtil.checkEnchantmentKey(enchantment.enchantmentKey);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Enchantment invalid - " + enchantment.enchantmentKey);
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
							TradeUtil.random.nextInt(3) + 1);
				} else if (enchantments[i].enchantmentKey.contains("#")) {
					String[] enchantmentChoices = enchantments[i].enchantmentKey.split("#");

					String chosenKey = enchantmentChoices[TradeUtil.random.nextInt(enchantmentChoices.length)];

					addEnchantmentMyWay(enchantedStack,
							ForgeRegistries.ENCHANTMENTS.getValue(TradeUtil.getResourceLocation(chosenKey)),
							enchantments[i].enchantmentLevel);

				} else {
					addEnchantmentMyWay(enchantedStack, enchantments[i].getEnchantment(),
							enchantments[i].enchantmentLevel);
				}
			}
		}
		return enchantedStack;
	}

	private ItemStack addEnchantmentMyWay(ItemStack stack, Enchantment enchantment, int level) {
		if (stack.getItem() == Items.ENCHANTED_BOOK) {
			EnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantment, level));
		} else {
			stack.enchant(enchantment, level);
		}
		return stack;
	}
}
