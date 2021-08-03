package uk.co.dotcode.customvillagertrades.configs;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.BaseClass;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeItem {

	public String itemKey;
	public String customName;
	public Integer amount;
	public Integer priceModifier;

	public Integer metadata;

	public MyTradeEnchantment[] enchantments;
	public MyTradeEnchantment[] semiRandomEnchantments;
	public String[] blacklistedEnchantments;
	public MyTradeEffect[] effects;
	public String[] blacklistedEffects;

	public MyTradeItem(String itemKey, int amount, int priceModifier) {
		this.itemKey = itemKey;
		this.amount = amount;
		this.priceModifier = priceModifier;
	}

	public int getPriceModifier() {
		if (priceModifier == null) {
			return 0;
		}
		return priceModifier;
	}

	public ItemStack createItemStack(int modifier) {
		Item item = ForgeRegistries.ITEMS.getValue(TradeUtil.getResourceLocation(itemKey));

		ItemStack stack = new ItemStack(item, amount + modifier);

		if (metadata != null) {
			stack.setDamageValue(metadata);
		}

		if (customName != null) {
			stack.setHoverName(new StringTextComponent(customName));
		}

		stack = processEnchantments(stack);

		stack = processEffects(stack);

		return stack;
	}

	public boolean checkEffects() {
		if (effects != null && effects.length > 0) {
			for (int i = 0; i < effects.length; i++) {
				boolean check = TradeUtil.isEffectReal(effects[i].effectKey);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN, "Effect invalid - " + effects[i]);
					return true;
				}
			}
		}

		if (blacklistedEffects != null && blacklistedEffects.length > 0) {
			for (int i = 0; i < blacklistedEffects.length; i++) {
				boolean check = TradeUtil.isEffectReal(blacklistedEffects[i]);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Blacklisted Effect invalid - " + blacklistedEffects[i]);
					return true;
				}
			}
		}

		return false;
	}

	public boolean checkEnchantments() {
		if (enchantments != null && enchantments.length > 0) {
			for (int i = 0; i < enchantments.length; i++) {
				boolean check = TradeUtil.isEnchantmentKeyReal(enchantments[i].enchantmentKey);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Enchantment invalid - " + enchantments[i].enchantmentKey);
					return true;
				}
			}
		}

		if (semiRandomEnchantments != null && semiRandomEnchantments.length > 0)

		{
			for (int i = 0; i < semiRandomEnchantments.length; i++) {
				boolean check = TradeUtil.isEnchantmentKeyReal(semiRandomEnchantments[i].enchantmentKey);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Semi-Random Enchantment invalid - " + semiRandomEnchantments[i].enchantmentKey);
					return true;
				}
			}
		}

		if (blacklistedEnchantments != null && blacklistedEnchantments.length > 0) {
			for (int i = 0; i < blacklistedEnchantments.length; i++) {
				boolean check = TradeUtil.isEnchantmentKeyReal(blacklistedEnchantments[i]);

				if (!check) {
					LogManager.getLogger(BaseClass.MODID).log(Level.WARN,
							"Blacklisted Enchantment invalid - " + blacklistedEnchantments[i]);
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
					ArrayList<Enchantment> availableRandomEnchantments = new ArrayList<Enchantment>();

					for (Enchantment e : ForgeRegistries.ENCHANTMENTS.getValues()) {
						boolean isBlacklisted = false;

						if (stack.getItem().getRegistryName().toString().equalsIgnoreCase("minecraft:enchanted_book")) {
							if (blacklistedEnchantments != null) {
								for (String blacklisted : blacklistedEnchantments) {
									if (e.getRegistryName().toString().equalsIgnoreCase(blacklisted)) {
										isBlacklisted = true;
									}
								}
							}
							if (!isBlacklisted) {
								availableRandomEnchantments.add(e);
							}
						} else if (e.canApplyAtEnchantingTable(enchantedStack)) {
							// Shameless copy/paste - promise you won't tell anyone!
							if (blacklistedEnchantments != null) {
								for (String blacklisted : blacklistedEnchantments) {
									if (e.getRegistryName().toString().equalsIgnoreCase(blacklisted)) {
										isBlacklisted = true;
									}
								}
							}
							if (!isBlacklisted) {
								availableRandomEnchantments.add(e);
							}
						}
					}

					addEnchantmentMyWay(enchantedStack,
							availableRandomEnchantments
									.get(TradeUtil.random.nextInt(availableRandomEnchantments.size())),
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

	private ItemStack processEffects(ItemStack stack) {
		ItemStack effectStack = stack.copy();

		if (effects != null) {
			Collection<EffectInstance> effectsToApply = new ArrayList<EffectInstance>();

			for (int i = 0; i < effects.length; i++) {
				if (effects[i].effectKey.equalsIgnoreCase("random")) {
					ArrayList<EffectInstance> availableRandomEffects = new ArrayList<EffectInstance>();

					for (Effect e : ForgeRegistries.POTIONS.getValues()) {
						boolean isBlacklisted = false;

						if (blacklistedEffects != null) {
							for (String blacklisted : blacklistedEffects) {
								if (e.getRegistryName().toString().equalsIgnoreCase(blacklisted)) {
									isBlacklisted = true;
								}
							}
						}
						if (!isBlacklisted) {
							availableRandomEffects.add(effects[i].getInstance(e.getRegistryName().toString()));
						}
					}

					effectsToApply.add(new EffectInstance(
							availableRandomEffects.get(TradeUtil.random.nextInt(availableRandomEffects.size()))));

				} else if (effects[i].effectKey.contains("#")) {
					String[] effectChoices = effects[i].effectKey.split("#");

					String chosenKey = effectChoices[TradeUtil.random.nextInt(effectChoices.length)];

					effectsToApply.add(effects[i].getInstance(chosenKey));
				} else {
					effectsToApply.add(effects[i].getInstance());
				}
			}

			PotionUtils.setCustomEffects(effectStack, effectsToApply);

			if (itemKey.equalsIgnoreCase("minecraft:suspicious_stew")) {
				for (EffectInstance e : effectsToApply) {
					SuspiciousStewItem.saveMobEffect(effectStack, e.getEffect(), e.getDuration());
				}
			}
		}

		return effectStack;
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
