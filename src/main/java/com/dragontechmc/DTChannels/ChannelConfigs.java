package com.dragontechmc.DTChannels;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;


@ConfigSerializable
public class ChannelConfigs {
	
	@Setting(value = "sb_Prefix", comment = "")
	public String sbPrefix = "MB";
	
	

}
