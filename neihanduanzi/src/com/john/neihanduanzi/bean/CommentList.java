package com.john.neihanduanzi.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 10-05 17:06:07.410: D/TestActivity(29989): --->data
10-05 17:06:07.411: D/TestActivity(29989): --->group_id
10-05 17:06:07.411: D/TestActivity(29989): --->message
10-05 17:06:07.411: D/TestActivity(29989): --->total_number
10-05 17:06:07.411: D/TestActivity(29989): --->has_more

 * 评论接口返回的 data ：{} 数据部分的实体定义
 * 包含了 top——comments 和recent_comments 两个数据
 * 
 * <pre>
 * JSON格式如下:<br/>
 * {
 * 		“data” ：{
 * 		“top_comments" :[]
 *      "recent_comments" :[]
 *      }
 * }
 * </pre>
 * 
 */
public class CommentList {
	private List<Comment> topComments;//热门评论
	private List<Comment> recentComments;//最新评论
	
	private long groupId;
	private int totalNumber;
	private boolean hasMore;
	
	public void parseJson(JSONObject json) throws JSONException{
		if(json != null){
			
			groupId =json.optLong("group_id");
			totalNumber = json.optInt("total_number");
			hasMore = json.optBoolean("has_more");
			
			
			JSONObject data = json.getJSONObject("data");
			
			
			JSONArray tArray = data.optJSONArray("top_comments");
			
			
			
			if(tArray != null){
				topComments = new LinkedList<Comment>();
				
				int len = tArray.length();
				if(len > 0){
					for (int index = 0; index < len; index++) {
						JSONObject obj = tArray.getJSONObject(index);
						Comment comment = new Comment();
						comment.parseJson(obj);
						topComments.add(comment);
					}
				}
			}
			JSONArray rArray = data.optJSONArray("recent_comments");
			if(rArray != null){
				recentComments = new LinkedList<Comment>();
				
				int len = rArray.length();
				if(len > 0){
					for (int index = 0; index < len; index++) {
						JSONObject obj = rArray.getJSONObject(index);
						Comment comment = new Comment();
						comment.parseJson(obj);
						recentComments.add(comment);
					}
				}
			}
			
		}
	}

	public List<Comment> getTopComments() {
		return topComments;
	}

	public List<Comment> getRecentComments() {
		return recentComments;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public boolean isHasMore() {
		return hasMore;
	}
	
	
}
