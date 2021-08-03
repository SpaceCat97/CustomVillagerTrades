package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.dotcode.customvillagertrades.TradeUtil;

public class MyTradeEffect {

	public String effectKey;
	public Integer duration; // In ticks
	public Integer level;
	public boolean isVisible;

	public Effect getEffect(String key) {
		return ForgeRegistries.POTIONS.getValue(TradeUtil.getResourceLocation(key));
	}

	public EffectInstance getInstance() {
		int actualLevel = level - 1;

		if (actualLevel < 0) {
			actualLevel = 0;
		}

		return new EffectInstance(getEffect(effectKey), duration, actualLevel, false, isVisible, isVisible);
	}

	public EffectInstance getInstance(String chosenKey) {
		int actualLevel = level - 1;

		if (actualLevel < 0) {
			actualLevel = 0;
		}

		return new EffectInstance(getEffect(chosenKey), duration, actualLevel, false, isVisible, isVisible);
	}

}
