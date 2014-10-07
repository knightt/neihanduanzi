package com.john.neihanduanzi.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageEntity extends TextEntity implements Serializable {

	
	private static final long serialVersionUID = 8758648136554330339L;

	private ImageUrlList largeList;

	private ImageUrlList middleList;



	public ImageUrlList getLargeList() {
		return largeList;
	}

	public ImageUrlList getMiddleList() {
		return middleList;
	}

	public void parseJson(JSONObject item) throws JSONException {
		
		//字段都有了  继承了TextEntity
		super.parseJson(item);
		
		JSONObject group = item.getJSONObject("group");
		
		
		//有可能只有大图 没有小图 或者都没有
		JSONObject largeImage = group.optJSONObject("large_image");
		JSONObject middleImage = group.optJSONObject("middle_image");

		largeList = new ImageUrlList();
		if(largeImage != null){
		largeList.parse(largeImage);
		}

		middleList = new ImageUrlList();
		if(middleImage != null){
		middleList.parse(middleImage);
		}

	}
}
