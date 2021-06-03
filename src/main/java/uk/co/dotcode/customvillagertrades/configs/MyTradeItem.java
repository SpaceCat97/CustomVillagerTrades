package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeItem {

	public String itemKey;
	public int amount;

	public Integer metadata;

	public String enchantmentKey;
	public Integer enchantmentLevel;

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

		if (enchantmentKey != null) {
			Enchantment enchantment = ForgeRegistries.ENCHANTMENTS
					.getValue(TradeUtil.getResourceLocation(enchantmentKey));

			stack.enchant(enchantment, enchantmentLevel);
		}

		return stack;
	}

}
