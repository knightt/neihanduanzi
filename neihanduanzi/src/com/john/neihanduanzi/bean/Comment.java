package com.john.neihanduanzi.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.provider.MediaStore.Video;

public class Comment {
	/*"recent_comments": [
	            {
	               "uid": 0,
	               "platform": "feifei",
	                 "text": "老公的神回复呀：＂老婆，必须的全力以赴，真干实干！＂ 这是他们单位的工作口号给用我这啦！[擦汗][惊恐][偷笑][害羞][色]。",
	                "digg_count": 0,
	               "user_digg": 0,
	               "user_verified": false,
	                "bury_count": 0,
	             "user_profile_url": "",
	                "id": 1421479394,
	                 "user_name": "petmaggie",
	                "user_bury": 0,
	                 "user_profile_image_url": "http:\/\/mat1.gtimg.com\/www\/mb\/images\/head_50.jpg",
	               "description": "这个用户很懒，神马都木有写",
	                "create_time": 1361772576,
	                "user_id": 1243198063
	           },*/
	
	private long uid;
	private String platform;
	private String text;
	private int diggCount;
	private int userDigg;
	private boolean userVerified;
	private int buryCount;
	private String userPorfileUrl;
	private long id;
	private String userName;
	private int userBury;
	private String userProfileImageUrl;
	private String description;
	private long createTime;
	private long userId;

	public void parseJson(JSONObject json) throws JSONException{
		if(json != null){
			uid = json.getLong("uid");
			platform = json.getString("platform");
			text = json.getString("text");
			diggCount = json.getInt("digg_count");
			userDigg = json.getInt("user_digg");
			userVerified = json.getBoolean("user_verified");
			buryCount = json.getInt("bury_count");
			userPorfileUrl = json.getString("user_profile_url");
			id = json.getLong("id");
			
			userName = json.getString("user_name");
			userBury = json.getInt("user_bury");
			userProfileImageUrl = json.getString("user_profile_image_url");//用户头像列表
			description = json.getString("description");
			createTime = json.getLong("create_time");
			userId = json.getLong("user_id");
			
		}
	}

	public long getUid() {
		return uid;
	}

	public String getPlatform() {
		return platform;
	}

	public String getText() {
		return text;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getUserPorfileUrl() {
		return userPorfileUrl;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserBury() {
		return userBury;
	}

	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public long getCreateTime() {
		return createTime;
	}

	public long getUserId() {
		return userId;
	}
	
}
