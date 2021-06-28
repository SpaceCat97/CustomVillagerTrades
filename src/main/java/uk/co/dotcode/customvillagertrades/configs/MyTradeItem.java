package uk.co.dotcode.customvillagertrades.configs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
				enchantedStack.enchant(enchantments[i].getEnchantment(), enchantments[i].enchantmentLevel);
			}
		}

		return enchantedStack;
	}
}
