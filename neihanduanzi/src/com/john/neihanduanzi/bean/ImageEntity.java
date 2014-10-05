package com.john.neihanduanzi.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageEntity {

	
	private int type;

	private int commentCount;

	private long groupId;

	private String content;

	private ImageUrlList largeList;

	private ImageUrlList middleList;

	public int getType() {
		return type;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getContent() {
		return content;
	}

	public ImageUrlList getLargeList() {
		return largeList;
	}

	public ImageUrlList getMiddleList() {
		return middleList;
	}

	public void parseJson(JSONObject item) throws JSONException {
		JSONObject group = item.getJSONObject("group");
		JSONObject largeImage = group.getJSONObject("large_image");
		JSONObject middleImage = group.getJSONObject("middle_image");
		type = item.getInt("type");
		commentCount = group.getInt("comment_count");

		largeList = new ImageUrlList();
		largeList.parse(largeImage);

		middleList = new ImageUrlList();
		middleList.parse(middleImage);

		groupId = group.getLong("group_id");
		content = group.getString("content");
	}
}
