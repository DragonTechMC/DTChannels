package com.dragontechmc.DTChannels;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.text.Text;

import com.dragontechmc.DTChannels.Commands.AdminBroadcast;
import com.dragontechmc.DTChannels.Commands.ModBroadcast;
import com.dragontechmc.DTChannels.Commands.StaffBroadcast;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;

public class DTChannels {
	
	@Inject
	private Logger logger;
	
	@Inject 
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> defaultConfigLoader;

	@Inject
	ChannelConfigs config;
	
	@Inject
	private StaffBroadcast staffBroadcast;
	@Inject
	private ModBroadcast modBroadcast;
	@Inject
	private AdminBroadcast adminBroadcast;

	@Inject
	private DTChannelsPluginContainer plugin;
	
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	this.logger.info("Starting DTChannels");
    	
    	
        loadConfig();
        
    	// Register Commands
        CommandSpec staffBroadcastSpec = CommandSpec.builder()
        	    .description(Text.of("Staff Broadcast"))
        	    .permission("dtchannels.staffbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(staffBroadcast)
        	    .build();
    	
        CommandSpec modBroadcastSpec = CommandSpec.builder()
        	    .description(Text.of("Mod Broadcast"))
        	    .permission("dtchannels.modbroadcast")
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
        	    .executor(modBroadcast)
        	    .build();
        
		CommandSpec adminBroadcastSpec = CommandSpec.builder()
        	    .description(Text.of("Admin Broadcast")) 
        	    .permission("dtchannels.adminbroadcast") 
        	    .arguments(GenericArguments.remainingJoinedStrings(Text.of("message"))) 
        	    .executor(adminBroadcast) 
				.build(); 
        
        
        Sponge.getCommandManager().register(plugin, staffBroadcastSpec, "staffbroadcast", "sb");
        Sponge.getCommandManager().register(plugin, modBroadcastSpec, "modbroadcast", "mb");
        Sponge.getCommandManager().register(plugin, adminBroadcastSpec, "adminbroadcast", "ab");        
    }
    
    
    public void loadConfig(){

    	// Config 
    	try {
    		CommentedConfigurationNode source = defaultConfigLoader.load();
	    	
	    	defaultConfigLoader.save(source);
	    	
			ObjectMapper<ChannelConfigs> mapper = ObjectMapper.forClass(ChannelConfigs.class);
			mapper.bind(config).populate(source);		
    	} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
    }
}
