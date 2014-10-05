package com.john.neihanduanzi.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//large_image 里面的属性  它里面有一个jsonArray  单独提出成一个类
public class ImageUrlList {
	private List<String> largeImageUrls;
	private String uri;
	private int width;
	private int height;

	public List<String> getLargeImageUrls() {
		return largeImageUrls;
	}

	public String getUri() {
		return uri;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void parse(JSONObject json) throws JSONException {
		largeImageUrls = parseImageUrlList(json);
		uri = json.getString("uri");
		width = json.getInt("width");
		height = json.getInt("height");
	}

	/**
	 * 解析图片地址
	 * 
	 * @param imageObject
	 * @return
	 * @throws JSONException
	 */
	public List<String> parseImageUrlList(JSONObject imageObject)
			throws JSONException {
		JSONArray urlList = imageObject.getJSONArray("url_list");

		List<String> imageUrls = new ArrayList<String>();
		for (int j = 0; j < urlList.length(); j++) {
			JSONObject urlObject = urlList.getJSONObject(j);
			String url = urlObject.getString("url");
			imageUrls.add(url);
		}
		return imageUrls;
	}

}

	

