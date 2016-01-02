package com.michealharker.saraswati.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BungeeMessage {
	
	public UUID uuid;
	public String message;
	public BungeeMessageType type;
	public long ts;
	public Object extra;
	
	public BungeeMessage(UUID uuid, String message, BungeeMessageType type, Object extra) {
		this.uuid = uuid;
		this.message = message;
		this.type = type;
		this.extra = extra;
	}
	
	public BungeeMessage(String json) {
		this.deserialize(json);
	}
	
	private void deserialize(String json) {
		JSONObject obj = (JSONObject)JSONValue.parse(json);
		
		this.uuid = UUID.fromString((String) obj.get("u"));
		this.message = (String) obj.get("m");
		this.type = BungeeMessageType.valueOf((String) obj.get("t"));
		this.ts = (Long) obj.get("ts");
		this.extra = (Object) obj.get("e");
	}

	public static byte[] buildMessage(UUID uuid, String message, BungeeMessageType type, Object extra) {
		BungeeMessage bm = new BungeeMessage(uuid, message, type, extra);
		return bm.buildMessage();
	}
	
	public static byte[] buildMessage(UUID uuid, String message, BungeeMessageType type) {
		BungeeMessage bm = new BungeeMessage(uuid, message, type, null);
		return bm.buildMessage();
	}
	
	public byte[] buildMessage() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			// BungeeCord Protocol items
			out.writeUTF("Forward");
			out.writeUTF("ALL"); 
			out.writeUTF("Saraswati");
			
			// Our stuff
			ByteArrayOutputStream bInner = new ByteArrayOutputStream();
			DataOutputStream outInner = new DataOutputStream(bInner);
		
			outInner.writeUTF(this.serialize(uuid, message, type, extra));
			
			byte[] bytes = bInner.toByteArray();
			out.writeShort(bytes.length);
			out.write(bytes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return b.toByteArray();
	}

	@SuppressWarnings("unchecked")
	public String serialize(UUID uuid, String message, BungeeMessageType type, Object extra) {
		JSONObject obj = new JSONObject();
		
		obj.put("u", uuid.toString());
		obj.put("t", type.toString());
		obj.put("m", message);
		obj.put("ts", System.currentTimeMillis());
		obj.put("e", extra);
		
		return obj.toJSONString();
	}
	
}
