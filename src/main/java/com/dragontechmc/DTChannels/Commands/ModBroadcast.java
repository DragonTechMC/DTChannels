package com.dragontechmc.DTChannels.Commands;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.dragontechmc.DTChannels.ChannelConfigs;
import com.google.inject.Inject;


public class ModBroadcast implements CommandExecutor {
	
	@Inject private Game game;
	
	@Inject
	ChannelConfigs config;
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		
		Text prefix = Text.of(toText(config.mbPrefix));
		
		String message = args.<String>getOne("message").get();
		
		Player player = (Player) src;
		
		Text fullMessage = Text.of(prefix, " ", TextColors.RED, player.getName(), ": ",TextColors.GREEN, message);
		
		
        for (Player players : game.getServer().getOnlinePlayers()) {
            if (players.hasPermission("dtchannels.modbroadcast")) {
                players.sendMessage(fullMessage);
            }
          
        }
        
		return CommandResult.success();
	}

	public static Text toText(String str){
    	return TextSerializers.FORMATTING_CODE.deserialize(str);
	}	
	
}
