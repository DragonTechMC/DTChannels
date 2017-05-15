package com.dragontechmc.DTChannels;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

import java.nio.file.Path;
import com.dragontechmc.DTChannels.Commands.*;
import com.google.inject.Inject;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import org.spongepowered.api.config.DefaultConfig;


@Plugin(id = "dtch", name = "DTChannels", version = "0.1")

public class DTChannels {
	
	
	@Inject
	private Logger logger;
	
	@Inject 
	@DefaultConfig(sharedRoot = false)
	private HoconConfigurationLoader defaultConfigLoader;
	
	@Inject
	ChannelConfigs config;
	
	@Inject
	private Injector injector;
	
	@Inject 
	private StaffBroadcast staffBroadcast;
	@Inject
	private ModBroadcast modBroadcast;
	@Inject
	private AdminBroadcast adminBroadcast;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	this.logger.info("Starting DTChannels");
    	
		
    	// Register Commands
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
    
        
        loadConfig();
    }
    
    
    public void loadConfig(){

    	// Config 
    	try {

    		Injector childInjector = injector.createChildInjector(new DTChannelsModule());
    		
	    	CommentedConfigurationNode source = defaultConfigLoader.load();
	    	
	    	defaultConfigLoader.save(source);
	    	
			ObjectMapper<ChannelConfigs> mapper = ObjectMapper.forClass(ChannelConfigs.class);
			ChannelConfigs config = childInjector.getInstance(ChannelConfigs.class);
			mapper.bind(config).populate(source);
			
		
    	} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
    }
}