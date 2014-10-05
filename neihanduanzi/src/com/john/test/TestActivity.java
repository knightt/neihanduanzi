package com.john.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.john.neihanduanzi.R;

import com.john.neihanduanzi.bean.Comment;
import com.john.neihanduanzi.bean.CommentList;
import com.john.neihanduanzi.bean.EntityList;
import com.john.neihanduanzi.bean.ImageEntity;
import com.john.neihanduanzi.bean.TextEntity;
import com.john.neihanduanzi.client.ClientAPI;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Response;

/**
 * 这就是一个测试入口，所有的测试内容都可以在这里
 * 
 * @author John
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String> {
	long lastTime;
	private RequestQueue queue;
	int offset;
	/**
	 * 分类ID, 1 代表文本
	 */

	private static final int CATEGORY_TEXT = 1;
	/**
	 * 分类ID, 2代表图片
	 */

	private static final int CATEGORY_IMAGE = 2;

	/*
	 * 数据
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		queue = Volley.newRequestQueue(this);
		// "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=1&level=6&count=30&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
		final int itemCount = 5;

		// 这个ID是对应文本段子的ID
		final long groupId = 1410053871L;
	
		//ClientAPI.getComments(queue, groupId, offset, this);
		
		
		Button button = (Button) this.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ClientAPI.getList(queue, CATEGORY_IMAGE, 30, lastTime,
						TestActivity.this);
				// ClientAPI.getList(queue, CATEGORY_TEXT, itemCount,lastTime,
				// TestActivity.this);
				 ClientAPI.getComments(queue,groupId, offset,TestActivity.this);
			}
		});

	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);

		
			// Log.d("TestActiviy", "Comment List :"+arg0);
			//解析获取到的评论列表
			CommentList commentList = new CommentList();
			//评论列表包含两组数据，一个是热门评论，一个是新鲜评论
			//评论都有可能为空
			commentList.parseJson(json);

			long groupId = commentList.getGroupId();
			Log.d("TestActivity", "--groupId" + groupId);
			
			//表示评论列表是否还可以继续加载
			boolean hasMore = commentList.isHasMore();
			
			
			List<Comment> topComments = commentList.getTopComments();
			
			List<Comment> recentComments = commentList.getRecentComments();
			
			if(topComments != null){
				for(Comment comment : topComments){
					Log.d("----->", "Top :"+comment.getText());
				}
			}
			
			if(recentComments != null){
				for(Comment comment : recentComments){
					Log.d("----->", "Recent :"+comment.getText());
				}
			}
			
			//TODO  直接把CommentList 提交给ListView 的Adapter  这样可以进行是否还有内容的判断
			//利用adapter更新数据
			
			
			
				//分页标识，要求服务器每次返回20条评论，通过hasMore判断时候还需要分页
				offset = offset + 20;	
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	// 数据列表回调方法 文字段子 图片段子都通过这个回调
	public void listonResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			JSONObject obj = json.getJSONObject("data");

			// 解析段子列表完整数据
			EntityList entityList = new EntityList();
			// 这个方法是解析json的方法，包括的支持图片 文本 广告的解析
			entityList.parseJson(obj);// 相当于获取真实的数据

			// 代表还可以更新一次数据
			if (entityList.isHasMore()) {
				lastTime = entityList.getMinTime(); // 获取更新时间标识
				Log.d("TestActivity", "---" + lastTime);
			} else {
				// 没有更多的数据，休息一会儿
				String tip = entityList.getTip();
				Log.d("----", tip);
			}

			// 获取段子内容列表

			// TODO 把entityList 这个段子的数据集合体，传递给ListView之类的Adapter 即可显示

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
