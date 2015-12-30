package com.michealharker.saraswati;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MuteManager {
	private HashMap<UUID, Boolean> mutes;
	
	public MuteManager(String json) {
		this.mutes = new HashMap<UUID, Boolean>();
		if (json != null) this.deserialize(json);
	}
	
	public String serialize() {
		JSONObject obj = new JSONObject();
		
		Iterator it = mutes.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			obj.put(pair.getKey(), pair.getValue());
			it.remove();
		}
		
		return obj.toJSONString();
	}
	
	private void deserialize(String json) {
		JSONObject obj = (JSONObject)JSONValue.parse(json);
		
		if (obj != null) {
			Iterator iter = obj.keySet().iterator();
		
			while (iter.hasNext()) {
				String key = (String)iter.next();
				mutes.put(UUID.fromString(key), (boolean)obj.get(key));
			}
		}
	}
	
	public void addMute(UUID uuid) {
		this.mutes.put(uuid, true);
	}
	
	public boolean isMuted(UUID uuid) {;
		if (this.mutes.containsKey(uuid)) {
			return this.mutes.get(uuid);
		} else {
			return false;
		}
	}
	
	public void removeMute(UUID uuid) {
		this.mutes.put(uuid, false);
	}
}
