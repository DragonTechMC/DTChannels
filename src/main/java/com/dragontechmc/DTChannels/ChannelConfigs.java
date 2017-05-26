package com.dragontechmc.DTChannels;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;


@ConfigSerializable
public class ChannelConfigs {
	
	@Setting(value = "SB_Prefix", comment = "Prefix for Staff Broadcast (Supports colour codes)")
	public String sbPrefix = "&6[SB]";
	
	@Setting(value = "MB_Prefix", comment = "Prefix for Moderator Broadcast (Supports colour codes)")
	public String mbPrefix = "&6[MB]";
	
	@Setting(value = "AB_Prefix", comment = "Prefix for Admin Broadcast (Supports colour codes)")
	public String abPrefix = "&6[AB]";
	
	@Setting(value = "Trade_Prefix", comment = "Prefix for Trade Broadcast (Supports colour codes)")
	public String tradePrefix = "&6[Trade]";
	
	

}
