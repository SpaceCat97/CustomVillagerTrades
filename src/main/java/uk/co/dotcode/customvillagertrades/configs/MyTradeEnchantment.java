package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeEnchantment {

	public String enchantmentKey;
	public Integer enchantmentLevel;
	public Integer maxEnchantmentLevel;

	public MyTradeEnchantment() {
		if (maxEnchantmentLevel == null) {
			maxEnchantmentLevel = 3;
		}
	}

	public MyTradeEnchantment(String key, int enchantmentLevel) {
		this();
		this.enchantmentKey = key;
		this.enchantmentLevel = enchantmentLevel;
	}

	public MyTradeEnchantment(String key, int enchantmentLevel, int maxEnchantmentLevel) {
		this(key, enchantmentLevel);
		this.maxEnchantmentLevel = maxEnchantmentLevel;
	}

	public Enchantment getEnchantment() {
		return ForgeRegistries.ENCHANTMENTS.getValue(TradeUtil.getResourceLocation(enchantmentKey));
	}
}
