package uk.co.dotcode.customvillagertrades;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BaseClass.MODID)
public class BaseClass {

	public static final String MODID = "customvillagertrades";

	public static final Logger LOGGER = LogManager.getLogger();

	private TradeEvent tradeEvent;

	public BaseClass() {
		tradeEvent = new TradeEvent();

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, CustomTradeConfig.CONFIG_SPEC);

		bus.addListener(this::commonSetup);

		MinecraftForge.EVENT_BUS.register(tradeEvent);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Checking custom villager trades...");

		TradeUtil.checkTrades(CustomTradeConfig.allVillagerTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.armorerTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.butcherTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.cartographerTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.clericTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.farmerTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.fishermanTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.fletcherTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.leatherworkerTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.librarianTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.masonTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.nitwitTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.shepherdTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.toolsmithTrades.get());
		TradeUtil.checkTrades(CustomTradeConfig.weaponsmithTrades.get());

		LogManager.getLogger(BaseClass.MODID).log(Level.INFO, "Check complete!");
	}

}