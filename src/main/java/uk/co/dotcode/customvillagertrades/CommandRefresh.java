package uk.co.dotcode.customvillagertrades;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;

public class CommandRefresh {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> refreshCommand = Commands.literal("reloadTrades")
				.requires((commandSource) -> commandSource.hasPermission(2)).executes(CommandRefresh::refreshTrades);

		dispatcher.register(refreshCommand);
	}

	static int refreshTrades(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {

		BaseClass.tradeEvent.reload();

		commandContext.getSource().getServer().getPlayerList().broadcastMessage(
				new StringTextComponent("Reloaded villager trades - see log for errors."), ChatType.CHAT, null);
		return 1;
	}

}
