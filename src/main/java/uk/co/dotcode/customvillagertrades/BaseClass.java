package uk.co.dotcode.customvillagertrades;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.co.dotcode.customvillagertrades.configs.TradeHandler;
import uk.co.dotcode.customvillagertrades.events.MyCommandEvent;
import uk.co.dotcode.customvillagertrades.events.ModifyGuiEvent;
import uk.co.dotcode.customvillagertrades.events.MyIMCEvent;
import uk.co.dotcode.customvillagertrades.events.TradeEvent;
import uk.co.dotcode.customvillagertrades.packet.PacketHandler;

@Mod(BaseClass.MODID)
public class BaseClass {

	public static final String MODID = "customvillagertrades";

	public static TradeEvent tradeEvent;
	private ModifyGuiEvent modifyGuiEvent;
	private MyIMCEvent imcEvent;
	private MyCommandEvent commandEvent;

	public BaseClass() {
		tradeEvent = new TradeEvent();
		modifyGuiEvent = new ModifyGuiEvent();
		imcEvent = new MyIMCEvent();
		commandEvent = new MyCommandEvent();

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, CustomTradeConfigOld.CONFIG_SPEC);

		bus.addListener(this::commonSetup);
		bus.addListener(imcEvent::imcProcess);

		MinecraftForge.EVENT_BUS.register(tradeEvent);
		MinecraftForge.EVENT_BUS.register(modifyGuiEvent);
		MinecraftForge.EVENT_BUS.register(commandEvent);

		PacketHandler.register();
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		TradeHandler.init();
	}
}