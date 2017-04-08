package com.dragontechmc.DTChannels;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

import com.dragontechmc.DTChannels.Commands.*;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;


@Plugin(id = "dtch", name = "DTChannels", version = "0.1")

public class DTChannels {
	
	
	@Inject
	private Logger logger;
	
	@Inject 
	private StaffBroadcast staffBroadcast;
	@Inject
	private ModBroadcast modBroadcast;
	@Inject
	private AdminBroadcast adminBroadcast;
	
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	this.logger.info("Starting DTChannels");
    	

    	
        CommandSpec StaffBroadcast = CommandSpec.builder()
        	    .description(Text.of("Staff Broadcast"))
        	    .permission("dtchannels.staffbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(staffBroadcast)
        	    .build();
    	
        CommandSpec ModBroadcast = CommandSpec.builder()
        	    .description(Text.of("Mod Broadcast"))
        	    .permission("dtchannels.modbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(modBroadcast)
        	    .build();
        
        CommandSpec AdminBroadcast = CommandSpec.builder()
        	    .description(Text.of("Admin Broadcast"))
        	    .permission("dtchannels.adminbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(adminBroadcast)
        	    .build();
        
        
        Sponge.getCommandManager().register(this, StaffBroadcast, "staffbroadcast", "sb");
        Sponge.getCommandManager().register(this, ModBroadcast, "modbroadcast", "mb");
        Sponge.getCommandManager().register(this, AdminBroadcast, "adminbroadcast", "ab");
    	
    }
	
	

}