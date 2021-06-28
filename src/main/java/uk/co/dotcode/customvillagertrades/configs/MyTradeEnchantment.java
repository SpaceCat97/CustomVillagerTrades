package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeEnchantment {

	public String enchantmentKey;
	public Integer enchantmentLevel;

	public Enchantment getEnchantment() {
		return ForgeRegistries.ENCHANTMENTS.getValue(TradeUtil.getResourceLocation(enchantmentKey));
	}

}
