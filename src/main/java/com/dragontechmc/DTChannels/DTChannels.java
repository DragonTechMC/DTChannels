package com.dragontechmc.DTChannels;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

import com.dragontechmc.DTChannels.Commands.*;
import com.google.inject.Inject;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import org.spongepowered.api.config.DefaultConfig;


@Plugin(id = "dtch", name = "DTChannels", version = "0.1")

public class DTChannels {
	
	
	@Inject
	private Logger logger;
	
	@Inject 
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> defaultConfigLoader;
	
	ChannelConfigs config;
	
	@Inject
	private Injector injector;
	
	private Injector childInjector;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	this.logger.info("Starting DTChannels");
    	
    	// Create the child injector
    	childInjector = injector.createChildInjector(new DTChannelsModule());
    	
        loadConfig();
        
    	// Register Commands
        CommandSpec staffBroadcast = CommandSpec.builder()
        	    .description(Text.of("Staff Broadcast"))
        	    .permission("dtchannels.staffbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(childInjector.getInstance(StaffBroadcast.class))
        	    .build();
    	
        CommandSpec modBroadcast = CommandSpec.builder()
        	    .description(Text.of("Mod Broadcast"))
        	    .permission("dtchannels.modbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(childInjector.getInstance(ModBroadcast.class))
        	    .build();
        
		CommandSpec adminBroadcast = CommandSpec.builder()
        	    .description(Text.of("Admin Broadcast")) 
        	    .permission("dtchannels.adminbroadcast") 
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message"))) 
        	    .executor(childInjector.getInstance(AdminBroadcast.class)) 
				.build(); 
        
        
        Sponge.getCommandManager().register(this, staffBroadcast, "staffbroadcast", "sb");
        Sponge.getCommandManager().register(this, modBroadcast, "modbroadcast", "mb");
        Sponge.getCommandManager().register(this, adminBroadcast, "adminbroadcast", "ab");        
    }
    
    
    public void loadConfig(){

    	// Config 
    	try {
    		
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