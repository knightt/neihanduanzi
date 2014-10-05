package com.john.neihanduanzi.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 文本段子实体
 * @author John
 *
 */
public class TextEntity{
	
	private int type;

	private long createTime;

	/*
	 * 上线时间
	 */
	private long onlineTime;

	/*
	 * 显示时间
	 */
	private long displayTime;

	/*
	 * 评论的个数
	 */
	private int commentCount;

	/*
	 * digg 的个数
	 */
	private int diggCount;

	// TODO 需要了解digg到底是什么含义
	private int userDigg;

	/*
	 * 段子的ID，访问详情和评论是，用这个作为接口的参数
	 */
	private long groupId;

	/*
	 * 状态，其中的可选值3，需要分析什么类型？
	 */
	private int status;

	/*
	 * 内容分类类型，1，文本，2图片
	 */
	private int categoryId;
	/*
	 * 代表踩的个数
	 */
	private int buryCount;
	/*
	 * 文本段子的内容部分（完整的内容）
	 */
	private String content;

	/*
	 * 
	 */
	private int userRepin;
	/*
	 * 代表当前用户是否踩了 ，0代表没有，1代表踩了
	 */
	private int userBury;
	/*
	 * TODO 这个需要分析一下是什么含义，现在又两处地方出现 1.获取列表接口里面有一个 level = 6 2.文本段子实体中，level = 4
	 */
	private int level;

	// TODO 需要分析
	private int goDetailCount;
	/*
	 * 当前用户是否评论了
	 */
	private int hasComments;

	/*
	 * 用于第三方分享，提交的网址参数
	 */
	private String shareUrl;
	
	/*
	 * 代表赞的个数
	 */
	private int favoriteCount;
	
	
	/*
	 * 代表当前用户是否赞了 ，0代表没有，1代表赞了
	 */
	private int userFavorite;
	
	//TODO 分析这个字段的含义
	private int label;

	
	
	/*
	 * 状态的描述信息<br/>
	 * 可选值:<br>
	 * <ul>
	 * <li>已发表到热门列表</li>
	 * </ul>
	 */
	private String statusDesc;
	
	
	
	
	//可以先解析一级的json  也可以按顺序  这里采用先解析一级，再二级
	
	
	
	
	// TODO 分析它的含义
	private int repinCount;
	
	/*
	 * 是否含有热门评论
	 */
	private int hasHotComments;
	
	private UserEntity userEntity;
	//TODO  需要分析comments这个JSON数组的内容是什么
	
public void parseJson(JSONObject json) throws JSONException {
		
		if(json != null){
			onlineTime = json.getLong("online_time");
			displayTime = json.getLong("display_time");
			JSONObject group = json.getJSONObject("group");
			

			createTime = group.getLong("create_time");
			favoriteCount = group.getInt("favorite_count");
			goDetailCount = group.getInt("go_detail_count");
			userFavorite = group.getInt("user_favorite");
			buryCount = group.getInt("bury_count");
			userBury = group.getInt("user_bury");
			shareUrl = group.getString("share_url");
			label =group.optInt("label");
			content = group.getString("content");
			commentCount = group.getInt("comment_count");
			status = group.getInt("status");
			
			hasComments = group.getInt("has_comments");
			hasHotComments = group.optInt("has_hot_comments");
			
			goDetailCount = group.getInt("go_detail_count");
			statusDesc = group.getString("status_desc");
			
			JSONObject uboj = group.getJSONObject("user");
			userEntity = new UserEntity();
			userEntity.parseJson(uboj);
			
			userDigg = group.getInt("user_digg");
			groupId = group.getLong("group_id");
			level = group.getInt("level");
			repinCount = group.getInt("repin_count");
			diggCount = group.getInt("digg_count");
			hasComments =group.getInt("has_comments");
			userRepin = group.getInt("user_repin");
			categoryId = group.getInt("category_id");
	
			
			
		}
					
	}

public long getOnlineTime() {
	return onlineTime;
}

public long getDisplayTime() {
	return displayTime;
}

public int getType() {
	return type;
}

public long getCreateTime() {
	return createTime;
}

public int getFavoriteCount() {
	return favoriteCount;
}

public int getUserBury() {
	return userBury;
}

public int getUserFavorite() {
	return userFavorite;
}

public int getBuryCount() {
	return buryCount;
}

public String getShareUrl() {
	return shareUrl;
}

public int getLabel() {
	return label;
}

public String getContent() {
	return content;
}

public int getCommentCount() {
	return commentCount;
}

public int getStatus() {
	return status;
}

public String getStatusDesc() {
	return statusDesc;
}

public int getHasComments() {
	return hasComments;
}

public int getGoDetailCount() {
	return goDetailCount;
}

public int getUserDigg() {
	return userDigg;
}

public int getDiggCount() {
	return diggCount;
}

public long getGroupId() {
	return groupId;
}

public int getLevel() {
	return level;
}

public int getRepinCount() {
	return repinCount;
}

public int getUserRepin() {
	return userRepin;
}

public int getHasHotComments() {
	return hasHotComments;
}

public int getCategoryId() {
	return categoryId;
}

public UserEntity getUserEntity() {
	return userEntity;
}


	
	
	
/**
 *  
                "online_time": 1411878957,
                "display_time": 1411878957,
                "group": {
                    "create_time": 1411718218.0,
                    "favorite_count": 1209,
                    "user_bury": 0,
                    "user_favorite": 0,
                    "bury_count": 1516,
                    "share_url": "http://toutiao.com/group/3560859160/?iid=2337593504&app=joke_essay",
                    "label": 1,
                    "content": "甲:昨天碰到抢劫的，被打了两顿。乙:为啥啊？甲:他问我有钱吗我说没有，他从我身上搜出一包软中华然后就被打了一顿。等他走了，不一会儿又回来打了我一顿，因为他发现里面塞的是白红梅，劫匪走时还留下一句‘没钱还装B’",
                    "comment_count": 177,
                    "status": 3,
                    "has_comments": 0,
                    "go_detail_count": 4370,
                    "status_desc": "已发表到热门列表",
                    "user": {
                        "avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
                        "user_id": 3080520868,
                        "name": "请叫我梓安哥",
                        "user_verified": false
                    },
                    "user_digg": 0,
                    "group_id": 3560859160,
                    "level": 4,
                    "repin_count": 1209,
                    "digg_count": 18424,
                    "has_hot_comments": 1,
                    "user_repin": 0,
                    "category_id": 1
                },
                "comments": [],
                "type": 1
            },
 */
}
