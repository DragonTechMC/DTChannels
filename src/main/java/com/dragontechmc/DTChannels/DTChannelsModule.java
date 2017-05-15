package com.dragontechmc.DTChannels;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.dragontechmc.DTChannels.ChannelConfigs;

public class DTChannelsModule implements Module {

	
	@Override
	public void configure(Binder binder) {
		
		binder.bind(ChannelConfigs.class).in(Scopes.SINGLETON);
		
	}

}
