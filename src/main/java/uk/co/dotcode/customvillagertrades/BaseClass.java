package uk.co.dotcode.customvillagertrades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;

@Mod(BaseClass.MODID)
public class BaseClass {

	public static final String MODID = "customvillagertrades";

	public static final Logger LOGGER = LogManager.getLogger();

	private TradeEvent tradeEvent;

	public BaseClass() {
		tradeEvent = new TradeEvent();

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, CustomTradeConfigOld.CONFIG_SPEC);

		bus.addListener(this::commonSetup);

		MinecraftForge.EVENT_BUS.register(tradeEvent);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		TradeHandler.init();
	}
}