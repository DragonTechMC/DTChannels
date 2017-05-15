package com.dragontechmc.DTChannels;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import com.google.inject.Inject;
import com.google.inject.Injector;

@Plugin(id = "dtch", name = "DTChannels", version = "0.1")
public class DTChannelsPluginContainer {

	Injector childInjector;
	
	@Inject
	public DTChannelsPluginContainer(Injector injector) {
		// Create the child injector
    	childInjector = injector.createChildInjector(new DTChannelsModule());	
    }

	@Listener
	public void onServerStart(GameInitializationEvent event) {
		DTChannels plugin = childInjector.getInstance(DTChannels.class);     	 
		Sponge.getEventManager().registerListeners(this, plugin);
	}
}
