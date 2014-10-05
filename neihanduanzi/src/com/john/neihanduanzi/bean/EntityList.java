package com.john.neihanduanzi.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EntityList {

	private boolean hasMore;
	private long minTime;
	private String tip;
	private long maxTime;
	private List<TextEntity> entities;

	public void parseJson(JSONObject json) throws JSONException {

		if (json != null) {
			// 第一个data的大括号 ，所有的东西,由那边传过来的json  就是拆掉的第一个data之后的

			hasMore = json.getBoolean("has_more");// 是否可以加载更多
			tip = json.getString("tip");
			if(hasMore == true){
				minTime = json.getLong("min_time");
			}
			
			//让其有默认值 为0
			maxTime = json.optLong("max_time");

			// 从data对象中，获取名称为data的数组，它代表的是段子列表
			JSONArray jsonArray = json.getJSONArray("data");
			int len = jsonArray.length();
			if (len > 0) {

				entities = new LinkedList<TextEntity>();

				for (int i = 0; i < len; i++) {
					JSONObject item = jsonArray.getJSONObject(i);

					// 遍历数组中每一条图片段子信息

					int type = item.getInt("type");// 获取类型，1是段子， 5是广告
					if (type == 5) {
						// TODO 处理广告内容

						AdEntity entity = new AdEntity();
						entity.parseJson(item);
						String downloadUrl = entity.getDownloadUrl();
						System.out.println("-------" + "--AD--" + downloadUrl);

					} else if (type == 1) {
						// 分析图片 还是段子在group里
						JSONObject group = item.getJSONObject("group");
						int cid = group.getInt("category_id");
						TextEntity entity = null;
						if (cid == 1) {
							// TODO 解析文本段子
							entity = new TextEntity();
						} else if (cid == 2) {
							// TODO解析图片段子
							// 把imageEntity单独提成了一个类 ，进行解析 ？？？？？不用转换吗
							entity = new ImageEntity();
						} else {
							return;
						}
						entity.parseJson(item);
						entities.add(entity);

						long groupId = entity.getGroupId();
						System.out.println("---------" + "--TEXT--" + groupId);

					}

				}
			}
		}

	}

	public boolean isHasMore() {
		return hasMore;
	}

	public long getMinTime() {
		return minTime;
	}

	public String getTip() {
		return tip;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public List<TextEntity> getEntities() {
		return entities;
	}
	
	
}
